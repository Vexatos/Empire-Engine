package dark.empire.core.empire;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import dark.api.access.AccessGroup;
import dark.api.access.AccessUser;
import dark.api.access.ISpecialAccess;
import dark.api.access.GroupRegistry;
import dark.api.save.IVirtualObject;
import dark.empire.api.IEmpireNode;

public class Empire implements ISpecialAccess, Cloneable, IVirtualObject
{
    /** Name the player sees */
    private String displayName = "Empire";
    /** ID used by the game to track this empire. Normaly is a short version of the display name */
    private String id = "Empire";
    /** Set containing all parts the empire */
    private Set<IEmpireNode> empireParts = new HashSet<IEmpireNode>();

    private List<AccessGroup> groups = new ArrayList<AccessGroup>();

    public Empire(String name)
    {
        this.displayName = name;
        GroupRegistry.loadNewGroupSet(this);
    }

    public String getID()
    {
        return this.id;
    }

    /** Should be called when an object is created that is linked to this empire */
    public void registerMember(IEmpireNode object)
    {
        //TODO add to a list so the empire has a static list of its memebers
    }

    /** Should be called when an object is destroyed and no longer exits */
    public void unregisterMember(IEmpireNode object)
    {

    }

    /** Called when the object unloads from the map */
    public void onMemberUnloaded(IEmpireNode village)
    {
        //TODO unload the memeber for the empires active list but maintain its record the the object exists

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
            if (group.getName().equalsIgnoreCase(name))
            {
                return group;
            }
        }
        return null;
    }

    @Override
    public boolean addGroup(AccessGroup group)
    {
        if (!this.groups.contains(group))
        {
            return this.groups.add(group);
        }
        return false;
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

    }

    public void writeToNBT(NBTTagCompound nbt)
    {

    }

    @Override
    public Empire clone()
    {
        return new Empire(this.displayName);
    }

    @Override
    public File getSaveFile()
    {
        return null;
    }

    @Override
    public void setSaveFile(File file)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(NBTTagCompound nbt)
    {
        nbt.setString("empireID", this.id);
        nbt.setString("displayName", this.displayName);
        // Write user list to save
        NBTTagList usersTag = new NBTTagList();
        for (AccessGroup group : this.groups)
        {
            usersTag.appendTag(group.save(new NBTTagCompound()));
        }
        nbt.setTag("groups", usersTag);
    }

    @Override
    public void load(NBTTagCompound nbt)
    {
        this.id = nbt.getString("empireID");
        this.displayName = nbt.getString("displayName");
        //Read users from save
        NBTTagList userList = nbt.getTagList("groups");
        for (int i = 0; i < userList.tagCount(); i++)
        {
            AccessGroup group = new AccessGroup("");
            group.load((NBTTagCompound) userList.tagAt(i));
            this.groups.add(group);
        }

    }

}
