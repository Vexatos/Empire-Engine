package dark.empire.village.village;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Save;
import universalelectricity.core.vector.Vector2;

import com.builtbroken.common.Pair;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

import dark.core.prefab.helpers.NBTFileHelper;
import dark.empire.core.empire.Empire;

/** Manages the functionality of the villages
 *
 * @author Darkguardsman */
public class VillageManager implements IScheduledTickHandler
{
    /** Map of villages to thier save names on file */
    private static HashMap<String, String> villageToSaveName = new HashMap();
    /** Map of village to their locations (dimID, XY coor) used to load and unload villages */
    private static HashMap<String, Pair<Integer, Vector2>> villageToLocation = new HashMap();
    private static Set<Village> villages = new HashSet<Village>();
    /** Save file located in the main world's save folder */
    public static final String VILLAGE_FILE = "EmpireEngine/Villages/";

    private static boolean loadedVillages = false, isLoadingVillages = false;

    private static VillageManager instance;

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
        Village village = new Village();
        if (!villageToSaveName.containsKey(name))
        {
            return village;
        }
        return null;

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
    public static Village loadVillage(String name)
    {
        if (villageToSaveName.containsKey(name))
        {
            Village village = new Village();
            NBTTagCompound tag = NBTFileHelper.loadNBTFile(new File(NBTFileHelper.getWorldSaveDirectory(MinecraftServer.getServer().getFolderName()), VILLAGE_FILE + name), "village", false);
            if (tag != null && !tag.hasNoTags())
            {
                village.load(tag);
            }
        }

        return null;
    }

    /** Saves a village to the save folder */
    public static void saveVillage(Village village)
    {
        if (village != null)
        {
            NBTTagCompound tag = new NBTTagCompound();
            village.save(tag);
            File villageFolder = new File(NBTFileHelper.getWorldSaveDirectory(MinecraftServer.getServer().getFolderName()), VILLAGE_FILE + village.name);
            File buildingFolder = new File(villageFolder, "buildings");
            if (!villageFolder.exists())
            {
                villageFolder.mkdirs();
            }
            if (!buildingFolder.exists())
            {
                buildingFolder.mkdirs();
            }
            //Save village file
            NBTFileHelper.saveNBTFile(new File(villageFolder, "village"), tag);
            //TODO save buildings
        }
    }

    public static void removeVillage(Village village)
    {
        //TODO remove village from all maps and remove its save files from the save dir
    }

    @ForgeSubscribe
    public void worldSave(Save evt)
    {
        for (Village village : villages)
        {
            if (village.isValid() && village.shouldSave)
            {
                saveVillage(village);
            }
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.WORLDLOAD)) && !isLoadingVillages)
        {
            preLoadVillagesFromWorld();
        }
        else if (type.equals(EnumSet.of(TickType.WORLD)) && this.loadedVillages)
        {

        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        if (!loadedVillages && !isLoadingVillages)
        {
            return EnumSet.of(TickType.WORLDLOAD);
        }
        return EnumSet.of(TickType.WORLD);
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
