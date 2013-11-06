package dark.empire.village.village;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.WorldEvent.Save;
import universalelectricity.core.vector.Vector2;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

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
    private static HashMap<String, Pair<Integer, Vector2>> villageToLocation = new HashMap();
    private static Set<Village> villages = new HashSet<Village>();
    /** Villages waiting to be unloaded from the map */
    private static HashMap<Village, Integer> unloadList = new HashMap();
    /** Save file located in the main world's save folder */
    public static final String VILLAGE_FILE = "EmpireEngine/Villages/";

    private static boolean loadedVillages = false, isLoadingVillages = false;

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
    public static Village createNewVillage(String name, Empire empire, Object creator)
    {
        if (!villageToLocation.containsKey(name))
        {
            Village village = new Village(name);
            village.setEmpire(empire);
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
                villageToLocation.put(village.name, new Pair<Integer, Vector2>(location.left().provider.dimensionId, location.right().toVector2()));
            }
        }
    }

    /** Temp loads all the villages from file so the manager can record what villages exist */
    public static void preLoadVillagesFromWorld()
    {
        File villageFolder = new File(NBTFileHelper.getWorldSaveDirectory(MinecraftServer.getServer().getFolderName()), VILLAGE_FILE);
        //TODO get a list of all folders in the village folder, check for village.dat files then add those file locations to the maps
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
            File file = village.getSaveFile();
            village.inValidate();
            return !file.exists() || file.delete();
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
            if (delete && !deleteVillage(village))
            {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.WORLDLOAD)) && !isLoadingVillages)
        {
            if(VillageManager.loadedVillages)
            {
                //Everything should have been saved before the game goes to main screen and the next map is loaded.
                for(Village village : villages)
                {
                    village.onUnload();
                    village.inValidate();
                }
                villages.clear();
                villageToLocation.clear();
                unloadList.clear();
                System.gc();
            }
            preLoadVillagesFromWorld();
        }
        else if (type.equals(EnumSet.of(TickType.SERVER)) && VillageManager.loadedVillages)
        {
            Iterator<Village> it = villages.iterator();
            while (it.hasNext())
            {
                Village village = it.next();
                Pair<World, Vector3> location = village.getLocation();
                if (village.isValid())
                {
                    VillageManager.villageToLocation.put(village.name, new Pair<Integer, Vector2>(location.left().provider.dimensionId, location.right().toVector2()));
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
        }
    }

    /** Checks if part of the village is loaded
     *
     * @return true even if its only the very edge to preven issues of the center not getting loaded */
    public boolean isPartOfVillageLoaded(Village village)
    {
        if (village != null)
        {
            Pair<World, Vector3> location = village.getLocation();
            if (location != null && location.left() != null && location.right() != null)
            {
                //TODO check if chunks in the area of the village are loaded
                World world = location.left();
                int sx = (location.right().intX() / 16) + village.sizeInChunks;
                int sz = (location.right().intZ() / 16) + village.sizeInChunks;

                for (int x = sx; x <= sx && x > sx - village.sizeInChunks; x--)
                {
                    for (int z = sz; z <= sz && z > sz - village.sizeInChunks; z--)
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
        if (!loadedVillages && !isLoadingVillages)
        {
            return EnumSet.of(TickType.WORLDLOAD);
        }
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
