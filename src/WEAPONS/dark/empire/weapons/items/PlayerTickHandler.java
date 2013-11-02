package dark.empire.weapons.items;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import dark.empire.weapons.EWRecipeLoader;

public class PlayerTickHandler implements ITickHandler
{

    List<String> players = new ArrayList();

    public static PlayerTickHandler instance = new PlayerTickHandler();

    public static void onRightClick(String name)
    {
        if (instance.players.contains(name))
        {
            instance.players.remove(name);
        }
        else if (name != null)
        {
            instance.players.add(name);
        }
    }

    public static boolean isAiming(String name)
    {
        return instance.players.contains(name);
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.PLAYER)))
        {
            try
            {
                EntityPlayer player = (EntityPlayer) tickData[0];

                ItemStack currentItem = player.getCurrentEquippedItem();
                if (currentItem != null && currentItem.itemID == EWRecipeLoader.itemGun.itemID)
                {
                    if (currentItem != null && (player != Minecraft.getMinecraft().renderViewEntity || Minecraft.getMinecraft().gameSettings.thirdPersonView != 0))
                    {

                        if (player.getItemInUseCount() <= 10)
                        {
                            player.setItemInUse(currentItem, Integer.MAX_VALUE);
                        }

                    }
                    else
                    {
                        player.setEating(false);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(this.getLabel() + " failed to tick properly.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel()
    {
        return "Empire-Engine:Weapons:Player";
    }

}
