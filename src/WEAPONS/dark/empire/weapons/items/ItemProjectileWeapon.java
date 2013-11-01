package dark.empire.weapons.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import dark.core.prefab.items.ItemBasic;
import dark.empire.weapons.EmpireWeapons;

/** Basic Projectile weapon class that stores all the attributes of the weapon as NBT
 *
 * @author DarkGaurdsman */
public class ItemProjectileWeapon extends ItemBasic
{
    public ItemProjectileWeapon()
    {
        super(EmpireWeapons.getNextID(), "EWProjectilWeapon", EmpireWeapons.CONFIGURATION);
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    }

    public ItemStack addComponentToWeapons(ItemStack stack)
    {
        return stack;
    }

    public static ItemStack createNewWeapon()
    {
        return null;
    }

    @Override
    public boolean canItemEditBlocks()
    {
        return false;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconIndex(ItemStack par1ItemStack)
    {
        return this.getIconFromDamage(par1ItemStack.getItemDamage());
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {

        return false;
    }

    @Override
    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count)
    {
        //TODO use this for automatic weapons
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if (entityLiving != null && !entityLiving.worldObj.isRemote)
        {
            ProjectileWeaponManager.fireWeapon(new ProjectileWeapon("Gun", ProjectileWeaponTypes.HANDGUN, 3),Bullet.NINE_MM, entityLiving);
        }
        return true;
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 0.0F;
    }

}
