package dark.empire.weapons.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import dark.empire.weapons.EWCommonProxy;
import dark.empire.weapons.EWRecipeLoader;
import dark.empire.weapons.guns.PlayerTickHandler;

public class EWClientProxy extends EWCommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
        MinecraftForge.EVENT_BUS.register(SoundHandler.INSTANCE);
        TickRegistry.registerTickHandler(PlayerTickHandler.instance, Side.CLIENT);
    }

    @Override
    public void init()
    {

        if (EWRecipeLoader.itemGun != null)
        {
            MinecraftForgeClient.registerItemRenderer(EWRecipeLoader.itemGun.itemID, new RenderItemGun());
        }
        super.init();
    }

    @Override
    public void postInit()
    {
        super.postInit();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}
