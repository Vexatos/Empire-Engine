package dark.empire.weapons.guns;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.dark.DarkCore;
import com.dark.prefab.ItemBasic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.empire.weapons.EmpireWeapons;

public class ItemWeaponUpgrade extends ItemBasic
{
    public ItemWeaponUpgrade()
    {
        super(DarkCore.getNextItemId(), "EWWeaponUpgrade", EmpireWeapons.CONFIGURATION);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public ItemStack addComponentToWeapons(ItemStack stack)
    {
        return stack;
    }

    public static ItemStack createNewWeapon()
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconIndex(ItemStack par1ItemStack)
    {
        return this.getIconFromDamage(par1ItemStack.getItemDamage());
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 0.0F;
    }

}