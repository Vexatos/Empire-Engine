package dark.empire.village.village;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;
import com.builtbroken.common.Triple;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import dark.core.save.NBTFileHelper;
import dark.core.save.SaveManager;
import dark.empire.api.village.IVillage;
import dark.empire.core.empire.Empire;

/** Manages the functionality of the villages
 * 
 * @author Darkguardsman */
public class VillageManager implements IScheduledTickHandler
{
    /** Map of village to their locations (dimID, XY coor) used to load and unload villages */
    private static HashMap<String, Triple<Integer, Vector3, Integer>> villageToLocation = new HashMap();
    private static Set<Village> villages = new HashSet<Village>();
    /** Villages waiting to be unloaded from the map */
    private static HashMap<Village, Integer> unloadList = new HashMap();
    /** Save file located in the main world's save folder */
    public static final String VILLAGE_FILE = "EmpireEngine/Villages/";

    private static HashMap<Integer, Boolean> loadedVillages = new HashMap<Integer, Boolean>();

    private static VillageManager instance;

    static
    {
        SaveManager.registerClass("BaseVillage", Village.class);
    }

    public static VillageManager instance()
    {
        if (instance == null)
        {
            instance = new VillageManager();
        }
        return instance;
    }

    /** Creates a new village, use loadVillage to get an exiting village
     * 
     * @param name - name the village will be created with and must be unique
     * @param empire - optional, empire the village will be attached to
     * @param creator - what created the village, can be a player, the world, or an entity
     * @return new Village */
    public static Village createNewVillage(World world, Vector3 location, String name, Empire empire, Object creator)
    {
        if (!villageToLocation.containsKey(name))
        {
            Village village = new Village(name, world, location);

            village.init();
            registerVillage(village);
            if (empire != null)
            {
                village.setEmpire(empire);
                empire.registerMember(village);
            }
            return village;
        }
        return null;

    }

    public static void registerVillage(Village village)
    {
        if (!villages.contains(village))
        {
            villages.add(village);
            Pair<World, Vector3> location = village.getLocation();
            if (location != null && location.left() != null && location.right() != null)
            {
                villageToLocation.put(village.name, new Triple<Integer, Vector3, Integer>(location.left().provider.dimensionId, location.right(), village.sizeInChunks));
            }
        }
    }

    @ForgeSubscribe
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (!event.world.isRemote)
        {
            int dim = event.world.provider.dimensionId;

            System.out.println("[VillageManager] loading villages from dim " + dim);
            //Refresh the list if the world decides to reload when it was already loaded
            if (loadedVillages.containsKey(dim) && loadedVillages.get(dim))
            {
                for (Village village : villages)
                {
                    village.onUnload();
                    village.inValidate();
                }
                villages.clear();
                villageToLocation.clear();
                unloadList.clear();
                System.gc();
            }
            preLoadVillagesFromWorld(dim);
        }
    }

    @ForgeSubscribe
    public void onWorldunLoad(WorldEvent.Unload event)
    {
        if (!event.world.isRemote)
        {
            int dim = event.world.provider.dimensionId;
            System.out.println("[VillageManager] unloading villages from dim " + dim);
            Iterator<Village> it = villages.iterator();

            while (it.hasNext())
            {
                Village village = it.next();
                Pair<World, Vector3> location = village.getLocation();
                if (location != null && location.left() != null)
                {
                    if (location.left().provider.dimensionId == dim)
                    {
                        SaveManager.saveObject(village);
                        unloadList.put(village, 0);
                        it.remove();
                    }
                }
                else
                {
                    unloadList.put(village, 0);
                    it.remove();
                }
            }
            loadedVillages.put(dim, false);
        }
    }

    /** Temp loads all the villages from file so the manager can record what villages exist */
    public static void preLoadVillagesFromWorld(int dim)
    {
        if (!loadedVillages.containsKey(dim) || !loadedVillages.get(dim))
        {
            File villageFolder = new File(NBTFileHelper.getWorldSaveDirectory(MinecraftServer.getServer().getFolderName()), VILLAGE_FILE + "/" + dim);
            if (villageFolder.exists())
            {
                for (File fileEntry : villageFolder.listFiles())
                {
                    if (fileEntry.isDirectory() && fileEntry.getName().startsWith("village"))
                    {
                        for (File subFile : fileEntry.listFiles())
                        {
                            if (subFile.getName().equalsIgnoreCase("village.dat"))
                            {
                                NBTTagCompound tag = NBTFileHelper.loadNBTFile(subFile, false);
                                if (tag.hasKey("name"))
                                {
                                    String name = tag.getString("name");
                                    Triple<Integer, Vector3, Integer> l = null;
                                    if (tag.hasKey("dim") && tag.hasKey("xCoord"))
                                    {
                                        l = new Triple<Integer, Vector3, Integer>(tag.getInteger("dim"), new Vector3(tag.getInteger("xCoord"), tag.getInteger("yCoord"), tag.getInteger("zCoord")), tag.getInteger("size"));
                                    }
                                    villageToLocation.put(name, l);
                                }
                                break;
                            }
                        }
                    }
                }
                loadedVillages.put(dim, true);
            }
            else
            {
                villageFolder.mkdirs();
            }
        }
    }

    /** Loads a village from the save folder
     * 
     * @param name - unique name of the village */
    public static IVillage loadVillage(String name)
    {
        if (villageToLocation.containsKey(name))
        {
            File file = new File(NBTFileHelper.getWorldSaveDirectory(MinecraftServer.getServer().getFolderName()), VILLAGE_FILE + name);
            if (file.exists())
            {
                Object object = SaveManager.createAndLoad(new File(file, "village.dat"));
                if (object instanceof IVillage)
                {
                    ((IVillage) object).init();
                    return (IVillage) object;
                }
            }
        }
        return null;
    }

    /** Deletes the villages save folder, */
    private static boolean deleteVillage(Village village)
    {
        if (village != null)
        {
            System.out.println("[VillageManager] Delted village " + village.name);
            File file = village.getSaveFile();

            village.inValidate();
            villageToLocation.remove(village);
            if (file != null)
            {
                return !file.exists() || file.delete();
            }
        }
        return false;
    }

    /** Removes the village from the map and unloads it from memory
     * 
     * @param village - village
     * @param delete - should we delte it save file
     * @return true if nothing went wrong */
    public static boolean removeVillage(Village village, boolean delete)
    {
        if (village != null)
        {
            //TODO check how tileEntities are removed
            SaveManager.saveObject(village);
            villages.remove(village);
            village.onUnload();
            System.out.println("[VillageManager] Removed village " + village.name + " from manager");
            if (delete && !deleteVillage(village))
            {
                return false;
            }
            return true;
        }
        return false;
    }

    Village vill;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        System.out.println("[VillageManager] 'test' tick");

        if (type.equals(EnumSet.of(TickType.SERVER)))
        {
            Iterator<Village> it = villages.iterator();
            List<String> l = new ArrayList<String>();
            while (it.hasNext())
            {
                Village village = it.next();
                l.add(village.name);
                Pair<World, Vector3> location = village.getLocation();
                if (village.isValid())
                {
                    VillageManager.villageToLocation.put(village.name, new Triple<Integer, Vector3, Integer>(location.left().provider.dimensionId, location.right(), village.sizeInChunks));
                    village.update();
                    if (village.shouldUnload() && !isPartOfVillageLoaded(village))
                    {
                        VillageManager.unloadList.put(village, 0);
                        it.remove();
                    }
                    else if (village.shouldSave)
                    {
                        SaveManager.markNeedsSaved(village);
                    }
                }
                else
                {
                    System.out.println("[VillageManager]Error: Invalid village had been removed. Name: " + village.name);
                    removeVillage(village, true);
                }
            }
            for (Entry<String, Triple<Integer, Vector3, Integer>> entry : villageToLocation.entrySet())
            {
                try
                {
                    if (!l.contains(entry.getKey()) && entry.getValue() != null)
                    {
                        World world = WorldProvider.getProviderForDimension(entry.getValue().getA()).worldObj;
                        if (isPartOfVillageLoaded(world, entry.getValue().getB(), entry.getValue().getC()))
                        {
                            System.out.println("[VillageManager] Loading village " + entry.getKey());
                            loadVillage(entry.getKey());
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isPartOfVillageLoaded(Village village)
    {
        if (village.getLocation() != null && village.getLocation().left() != null && village.getLocation().right() != null)
        {
            return isPartOfVillageLoaded(village.getLocation().left(), village.getLocation().right(), village.sizeInChunks);
        }
        return false;
    }

    /** Checks if part of the village is loaded
     * 
     * @return true even if its only the very edge to preven issues of the center not getting loaded */
    public boolean isPartOfVillageLoaded(World world, Vector3 location, int size)
    {
        if (world != null && location != null)
        {
            if (size == 0)
            {
                size = 1;
            }
            int sx = (location.intX() / 16) + size;
            int sz = (location.intZ() / 16) + size;

            for (int x = sx; x <= sx && x > sx - size; x--)
            {
                for (int z = sz; z <= sz && z > sz - size; z--)
                {
                    Chunk chunk = world.getChunkFromChunkCoords(x, z);
                    if (chunk != null && chunk.isChunkLoaded)
                    {
                        /* TODO check if the village has anything loaded in the chunk, to decrease the village
                         * having to be loaded if too far from the player. Should allow for chunkloading to happen
                         * without the village existing */
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        //Update villages on the unload list
        for (Entry<Village, Integer> entry : unloadList.entrySet())
        {
            if (isPartOfVillageLoaded(entry.getKey()))
            {
                System.out.println("[VillageManager] Unloading village " + entry.getKey().name);
                unloadList.remove(entry.getKey());
                villages.add(entry.getKey());
            }
            else if (entry.getValue() >= 5)
            {
                VillageManager.removeVillage(entry.getKey(), false);
            }
            else
            {
                unloadList.put(entry.getKey(), entry.getValue() + 1);
            }
        }

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);

    }

    @Override
    public String getLabel()
    {
        return "EmpireEngine:VillageManager";
    }

    @Override
    public int nextTickSpacing()
    {
        return 1200;
    }
}
