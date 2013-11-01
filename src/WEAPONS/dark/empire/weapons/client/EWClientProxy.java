package dark.empire.weapons.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import dark.empire.weapons.EWCommonProxy;

public class EWClientProxy extends EWCommonProxy
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
