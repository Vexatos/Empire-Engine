package dark.empire.core.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import dark.empire.core.CommonProxy;

public class ClientProxy extends CommonProxy
{
    public void preInit()
    {
        super.preInit();
    }

    public void init()
    {
        super.init();
    }

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
