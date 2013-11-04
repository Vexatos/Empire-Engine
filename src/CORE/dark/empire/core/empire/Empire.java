package dark.empire.core.empire;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import dark.api.access.AccessGroup;
import dark.api.access.AccessUser;
import dark.api.access.ISpecialAccess;
import dark.core.prefab.terminal.TerminalCommandRegistry;

public class Empire implements ISpecialAccess, Cloneable
{
    /** Name the player sees */
    private String displayName = "Empire";
    /** ID used by the game to track this empire. Normaly is a short version of the display name */
    private String id = "Empire";
    /** Set containing all parts the empire */
    private Set<IEmpireMember> empireParts = new HashSet();

    private List<AccessGroup> groups = new ArrayList();

    public Empire(String name)
    {
        this.displayName = name;
        TerminalCommandRegistry.loadNewGroupSet(this);
    }

    @Override
    public AccessUser getUserAccess(String username)
    {
        for (AccessGroup group : this.groups)
        {
            AccessUser user = group.getMember(username);
            if (user != null)
            {
                return user;
            }
        }
        return null;
    }

    public boolean canUserAccess(String username)
    {
        return this.getUserAccess(username) != null || this.getOwnerGroup().getMembers().size() <= 0;
    }

    @Override
    public List<AccessUser> getUsers()
    {
        List<AccessUser> users = new ArrayList<AccessUser>();
        for (AccessGroup group : this.groups)
        {
            users.addAll(group.getMembers());
        }
        return users;
    }

    @Override
    public boolean setUserAccess(String player, AccessGroup g, boolean save)
    {
        return setUserAccess(new AccessUser(player).setTempary(save), g);
    }

    @Override
    public boolean setUserAccess(AccessUser user, AccessGroup group)
    {
        boolean bool = false;
        if (user != null && user.getName() != null)
        {
            bool = this.removeUserAccess(user.getName()) && group == null;
            if (group != null)
            {
                bool = group.addMemeber(user);
            }
        }
        return bool;
    }

    public boolean removeUserAccess(String player)
    {
        boolean re = false;
        for (AccessGroup group : this.groups)
        {
            AccessUser user = group.getMember(player);
            if (user != null && group.removeMemeber(user))
            {
                re = true;
            }
        }
        return re;
    }

    @Override
    public AccessGroup getGroup(String name)
    {
        for (AccessGroup group : this.groups)
        {
            if (group.name().equalsIgnoreCase(name))
            {
                return group;
            }
        }
        return null;
    }

    @Override
    public void addGroup(AccessGroup group)
    {
        if (!this.groups.contains(group))
        {
            this.groups.add(group);
        }
    }

    @Override
    public AccessGroup getOwnerGroup()
    {
        if (this.getGroup("owner") == null)
        {
            this.groups.add(new AccessGroup("owner"));
        }
        return this.getGroup("owner");
    }

    @Override
    public List<AccessGroup> getGroups()
    {
        return this.groups;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList userList = nbt.getTagList("groups");
        for (int i = 0; i < userList.tagCount(); i++)
        {
            AccessGroup group = new AccessGroup("");
            group.load((NBTTagCompound) userList.tagAt(i));
            this.groups.add(group);
        }
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        // Write user list
        NBTTagList usersTag = new NBTTagList();
        for (AccessGroup group : this.groups)
        {
            usersTag.appendTag(group.save(new NBTTagCompound()));
        }
        nbt.setTag("groups", usersTag);
    }

    @Override
    public Empire clone()
    {
        return new Empire(this.displayName);
    }
}
