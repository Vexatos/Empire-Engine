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
    /** Empire this village is part of */
    private Empire empire;
    /** Unique name of the village */
    public String name = "village";
    /** Size of village in chunks radius from center chunk */
    public int sizeInChunks = 20;
    /** Location of the village */
    private Pair<World, Vector3> villageCenter;
    /** NPCs that call this village home */
    protected List<EntityNPC> village_members = new ArrayList();
    /** Should the village unload if the map area unloads */
    private boolean canUnload = true, isChunkLoader = false;
    /** Used by the villageManager to check if this has anything new to save */
    public boolean shouldSave = false;

    public Village()
    {

    }

    public Village(String name)
    {
        this.name = name;
    }

    /** Called when the village is created */
    public void init()
    {
        if (villageCenter != null && villageCenter.left() == null)
        {
            //TODO set world using the dim ID
        }
        VillageManager.registerVillage(this);
        if (empire != null)
        {
            //TODO register to empire and tell it this object is loaded
        }
    }

    /** Called every few mins so the village has a chance to check on all of its content */
    public void update()
    {
        //TODO tick all buildings for updates
        //TODO chunk buildings if #isChunkLoader is true
        //TODO check if data has changed then set shouldSave to true
    }

    /** Checks if the village wants to upload, is also checked in combination from the manager if the
     * area of the map is loaded */
    public boolean shouldUnload()
    {
        return this.canUnload && !this.isChunkLoader;
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

    public void inValidate()
    {
        //TODO loop threw all object that refrence this and tell them that the village is unloading
    }

}
