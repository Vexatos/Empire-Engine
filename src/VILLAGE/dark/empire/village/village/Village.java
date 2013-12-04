package dark.empire.village.village;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

import dark.api.save.NBTFileHelper;
import dark.empire.api.village.IVillage;
import dark.empire.api.village.IVillageObject;
import dark.empire.core.empire.Empire;

/** Virtual entity that control all aspect of the physical village in the world. Mainly its only used
 * to store information and process NPC events
 * 
 * @author DarkGuardsman */
public class Village implements IVillage
{
    /** Empire this village is part of */
    private Empire empire;
    /** Unique name of the village */
    public String name = "village";
    /** What created the village */
    public String creator = "world";
    /** Size of village in chunks radius from center chunk */
    public int sizeInChunks = 20;
    /** Location of the village */
    private Pair<World, Vector3> villageCenter;
    /** Should the village unload if the map area unloads */
    private boolean canUnload = true, isChunkLoader = false;
    /** Used by the villageManager to check if this has anything new to save */
    public boolean shouldSave = false;

    private File saveFile;

    public Village()
    {

    }

    public Village(String name, World world, Vector3 center)
    {
        this.name = name;
        this.villageCenter = new Pair<World, Vector3>(world, center);
    }

    /** Called when the village is created */
    @Override
    public void init()
    {
        VillageManager.registerVillage(this);
        if (empire != null)
        {
            empire.registerMember(this);
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

    @Override
    public void load(NBTTagCompound nbt)
    {
        this.name = nbt.getString("name");
        this.sizeInChunks = nbt.getInteger("size");
        this.villageCenter = new Pair<World, Vector3>(WorldProvider.getProviderForDimension(nbt.getInteger("dim")).worldObj, new Vector3(nbt.getInteger("xCoord"), nbt.getInteger("yCoord"), nbt.getInteger("zCoord")));

    }

    @Override
    public void save(NBTTagCompound nbt)
    {
        nbt.setString("name", this.name);
        nbt.setInteger("size", this.sizeInChunks);
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

    /** Called when the village is deleted */
    public void inValidate()
    {
        //TODO loop threw all object that reference this and tell them that the village is unloading
        if (empire != null)
        {
            empire.unregisterMember(this);
            this.empire = null;
        }

    }

    /** Called when the village is unloaded from the map */
    public void onUnload()
    {
        if (this.empire != null)
        {
            empire.onMemberUnloaded(this);
        }
    }

    @Override
    public File getSaveFile()
    {
        if (this.saveFile == null && this.getLocation() != null && this.getLocation().left() != null)
        {
            this.saveFile = new File(NBTFileHelper.getWorldSaveDirectory(MinecraftServer.getServer().getFolderName()), VillageManager.VILLAGE_FILE + "/" + this.getLocation().left().provider.dimensionId + "/" + "village_" + name + "/village.dat");
        }
        return this.saveFile;
    }

    @Override
    public void setSaveFile(File file)
    {
        this.saveFile = file;
    }

    @Override
    public void registerVillageObject(IVillageObject object)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterVillageObject(IVillageObject object)
    {
        // TODO Auto-generated method stub

    }

}
