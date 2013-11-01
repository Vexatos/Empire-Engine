package dark.empire.core.client;

import dark.empire.core.EECommonProxy;
import dark.empire.weapons.EWCommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EEClientProxy extends EECommonProxy
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
