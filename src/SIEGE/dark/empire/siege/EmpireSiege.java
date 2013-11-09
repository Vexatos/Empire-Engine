package dark.empire.siege;

import java.io.File;
import java.util.Arrays;

import net.minecraftforge.common.Configuration;
import universalelectricity.prefab.TranslationHelper;
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

@Mod(modid = EmpireSiege.MOD_ID, name = EmpireSiege.MOD_NAME, version = EmpireSiege.VERSION, dependencies = "after:EmpireEngine", useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class EmpireSiege extends ModPrefab
{

    // @Mod Prerequisites
    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVIS_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVIS_VERSION + "." + BUILD_VERSION;

    // @Mod
    public static final String MOD_ID = "EmpireEngine:Siege";
    public static final String MOD_NAME = "Empire Engine : Siege Package";

    @SidedProxy(clientSide = "dark.empire.siege.client.ESClientProxy", serverSide = "dark.empire.siege.ESCommonProxy")
    public static ESCommonProxy proxy;

    @Metadata(MOD_ID)
    public static ModMetadata meta;

    @Instance(MOD_ID)
    public static EmpireSiege instance;

    /** Main config file */
    public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "EmpireEngine/Siege.cfg"));
    private static final String[] LANGUAGES_SUPPORTED = new String[] { "en_US" };

    public static EmpireSiege getInstance()
    {
        if (instance == null)
        {
            instance = new EmpireSiege();
        }
        return instance;
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        EmpireSiege.getInstance();
        super.preInit(event);
        proxy.preInit();
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
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
        return "empiresiege";
    }

    @Override
    public void loadModMeta()
    {
        meta.modId = MOD_ID;
        meta.name = MOD_NAME;
        meta.description = "Siege package for Empire Engine provides a wide range of ways to destroy thy enemy within their safe walls.";
        meta.url = "http://www.builtbroken.com/empire-engine/";

        meta.logoFile = TEXTURE_DIRECTORY + "GP_Banner.png";
        meta.version = VERSION;
        meta.authorList = Arrays.asList(new String[] { "DarkGuardsman aka DarkCow" });
        meta.credits = "Please see the website.";
        meta.autogenerated = false;

    }

    @Override
    public void registerObjects()
    {
        EmpireSiege.CONFIGURATION.load();
        EmpireSiege.CONFIGURATION.save();

    }

    @Override
    public void loadRecipes()
    {
        ESRecipeLoader.instance().loadRecipes();
    }

}
