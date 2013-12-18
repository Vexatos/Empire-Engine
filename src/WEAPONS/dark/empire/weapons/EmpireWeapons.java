package dark.empire.weapons;

import java.io.File;
import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.prefab.TranslationHelper;

import com.dark.DarkCore;
import com.dark.ModObjectRegistry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dark.core.network.PacketHandler;
import dark.core.prefab.ModPrefab;
import dark.empire.weapons.guns.ItemBullet;
import dark.empire.weapons.guns.ItemBullet.BulletData;
import dark.empire.weapons.guns.ItemBullet.BulletTypes;
import dark.empire.weapons.guns.ItemProjectileWeapon;

@Mod(modid = EmpireWeapons.MOD_ID, name = EmpireWeapons.MOD_NAME, version = EmpireWeapons.VERSION, dependencies = "after:EmpireEngine", useMetadata = true)
@NetworkMod(channels = { EmpireWeapons.CHANNEL }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class EmpireWeapons extends ModPrefab
{

    // @Mod Prerequisites
    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVIS_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVIS_VERSION + "." + BUILD_VERSION;

    // @Mod
    public static final String MOD_ID = "EmpireEngine:Weapons";
    public static final String MOD_NAME = "Empire Engine Weapons";

    @SidedProxy(clientSide = "dark.empire.weapons.client.EWClientProxy", serverSide = "dark.empire.weapons.EWCommonProxy")
    public static EWCommonProxy proxy;

    public static final String CHANNEL = "EmpireEngine";

    @Metadata(MOD_ID)
    public static ModMetadata meta;

    @Instance(MOD_ID)
    public static EmpireWeapons instance;

    /** Main config file */
    public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "EmpireEngine/Weapons.cfg"));
    private static final String[] LANGUAGES_SUPPORTED = new String[] { "en_US" };

    public static EmpireWeapons getInstance()
    {
        if (instance == null)
        {
            instance = new EmpireWeapons();
        }
        return instance;
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        EmpireWeapons.getInstance();
        super.preInit(event);
        proxy.preInit();
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

        if (EWRecipeLoader.itemBullet instanceof ItemBullet)
        {
            for (BulletData data : BulletData.values())
            {
                for (BulletTypes type : BulletTypes.values())
                {
                    OreDictionary.registerOre("bullet." + data.name + "." + type.name, new ItemStack(EWRecipeLoader.itemBullet, 1, data.ordinal() * ItemBullet.spacing + type.ordinal()));
                }
            }

        }
        FMLLog.info(" Loaded: " + TranslationHelper.loadLanguages(LANGUAGE_PATH, LANGUAGES_SUPPORTED) + " Languages.");
        proxy.init();
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        proxy.postInit();
    }

    @Override
    public String getDomain()
    {
        return "empireweapons";
    }

    @Override
    public void loadModMeta()
    {
        meta.modId = MOD_ID;
        meta.name = MOD_NAME;
        meta.description = "Empire Engine is the begining of a new world in the age of NPC empire management";
        meta.url = "http://www.builtbroken.com/empire-engine/";

        meta.logoFile = DarkCore.TEXTURE_DIRECTORY + "GP_Banner.png";
        meta.version = VERSION;
        meta.authorList = Arrays.asList(new String[] { "DarkGuardsman aka DarkCow" });
        meta.credits = "Please see the website.";
        meta.autogenerated = false;

    }

    @Override
    public void registerObjects()
    {
        EmpireWeapons.CONFIGURATION.load();
        EWRecipeLoader.instance();
        EWRecipeLoader.itemGun = ModObjectRegistry.createNewItem("EWItemGun", MOD_ID, ItemProjectileWeapon.class, true);
        EWRecipeLoader.itemBullet = ModObjectRegistry.createNewItem("EWItemBullet", MOD_ID, ItemBullet.class, true);
        EmpireWeapons.CONFIGURATION.save();

    }

    @Override
    public void loadRecipes()
    {
        EWRecipeLoader.instance().loadRecipes();
    }

}
