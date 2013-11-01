package dark.empire.weapons.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
        this.setCreativeTab(CreativeTabs.tabCombat);
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
        if (entityLiving instanceof EntityPlayer)
        {
            MovingObjectPosition hitMOP = ProjectileWeaponManager.ray_trace_do(entityLiving.worldObj, entityLiving, 500, true);
            entityLiving.worldObj.playSoundAtEntity(entityLiving, "random.bow", 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
            Vec3 lookVec = entityLiving.getLookVec();
            if (hitMOP != null)
            {
                if (hitMOP.entityHit != null)
                {
                    drawParticleStreamTo(entityLiving, entityLiving.worldObj, hitMOP.hitVec.xCoord, hitMOP.hitVec.yCoord, hitMOP.hitVec.zCoord);
                    DamageSource damageSource = DamageSource.causePlayerDamage((EntityPlayer) entityLiving);
                    if (hitMOP.entityHit.attackEntityFrom(damageSource, 5))
                    {
                        hitMOP.entityHit.addVelocity(lookVec.xCoord * 1.2, Math.abs(lookVec.yCoord + 0.2f) * 1.2, lookVec.zCoord * 1.2);
                    }
                }
                drawParticleStreamTo(entityLiving, entityLiving.worldObj, hitMOP.hitVec.xCoord, hitMOP.hitVec.yCoord, hitMOP.hitVec.zCoord);
            }
            ((EntityPlayer) entityLiving).setItemInUse(stack, 10);
        }
        return true;
    }

    public void drawParticleStreamTo(Entity shooter, World world, double x, double y, double z)
    {
        Vec3 direction = shooter.getLookVec().normalize();
        double scale = 1.0;
        double xoffset = 1.3f;
        double yoffset = -.2;
        double zoffset = 0.3f;
        Vec3 horzdir = direction.normalize();
        horzdir.yCoord = 0;
        horzdir = horzdir.normalize();
        double cx = shooter.posX + direction.xCoord * xoffset - direction.yCoord * horzdir.xCoord * yoffset - horzdir.zCoord * zoffset;
        double cy = shooter.posY + shooter.getEyeHeight() + direction.yCoord * xoffset + (1 - Math.abs(direction.yCoord)) * yoffset;
        double cz = shooter.posZ + direction.zCoord * xoffset - direction.yCoord * horzdir.zCoord * yoffset + horzdir.xCoord * zoffset;
        double dx = x - cx;
        double dy = y - cy;
        double dz = z - cz;
        double ratio = Math.sqrt(dx * dx + dy * dy + dz * dz);

        while (Math.abs(cx - x) > Math.abs(dx / ratio))
        {
            world.spawnParticle("townaura", cx, cy, cz, 0.0D, 0.0D, 0.0D);
            cx += dx * 0.1 / ratio;
            cy += dy * 0.1 / ratio;
            cz += dz * 0.1 / ratio;
        }
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 0.0F;
    }

}
