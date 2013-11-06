package dark.empire.village.village;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

import dark.empire.core.empire.Empire;
import dark.empire.core.empire.IEmpireMember;
import dark.empire.core.entities.EntityNPC;

public class Village implements IEmpireMember
{
    private Empire empire;
    private String name = "village";
    private Pair<World, Vector3> villageCenter;
    protected List<EntityNPC> village_members = new ArrayList();
    private boolean canUnload = true;

    public void init()
    {
        if (villageCenter != null && villageCenter.left() == null)
        {
            //TODO set world using the dim ID
        }
    }

    public void update()
    {
        //slow tick update that only checks for a few things that have changed then goes back to sleep
        //Mainly is called when the village is unloaded from the map but still want to do a few things
    }

    /** Checks if the village wants to upload, is also checked in combination from the manager if the
     * area of the map is loaded */
    public boolean shouldUpload()
    {
        return this.canUnload;
    }

    @Override
    public Empire getEmpire()
    {
        if (this.empire == null)
        {
            //TODO load in the neutral empire
        }
        return this.empire;
    }

    @Override
    public void setEmpire(Empire empire)
    {
        this.empire = empire;

    }

    public void setCenter(World world, Vector3 location)
    {
        if (world != null && location != null)
        {
            this.villageCenter = new Pair<World, Vector3>(world, location);
        }
    }

    @Override
    public Pair<World, Vector3> getLocation()
    {
        if (this.villageCenter == null)
        {
            //TODO find a way to correct this so it is never null
        }
        return this.villageCenter;
    }

    public void load(NBTTagCompound nbt)
    {
        this.name = nbt.getString("name");
        this.villageCenter = new Pair<World, Vector3>(WorldProvider.getProviderForDimension(nbt.getInteger("dim")).worldObj, new Vector3(nbt.getInteger("xCoord"), nbt.getInteger("yCoord"), nbt.getInteger("zCoord")));

    }

    public void save(NBTTagCompound nbt)
    {
        nbt.setString("name", this.name);
        if (this.villageCenter != null)
        {
            if (this.villageCenter.right() != null)
            {
                nbt.setInteger("xCoord", this.villageCenter.right().intX());
                nbt.setInteger("yCoord", this.villageCenter.right().intY());
                nbt.setInteger("zCoord", this.villageCenter.right().intZ());
            }
            if (this.villageCenter.left() != null)
            {
                nbt.setInteger("dim", this.villageCenter.left().provider.dimensionId);
            }
        }
    }

    /** Is the village valid. This is used by the manager to check if the village is able to keep
     * function.
     *
     * @return true for good, false if this village should be cleared out of the list */
    public boolean isValid()
    {
        return this.villageCenter != null && this.villageCenter.left() != null && this.villageCenter.right() != null;
    }

}
