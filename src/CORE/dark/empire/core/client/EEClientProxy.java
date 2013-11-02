package dark.empire.core.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import dark.empire.core.EECommonProxy;

public class EEClientProxy extends EECommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
    }

    @Override
    public void init()
    {
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
