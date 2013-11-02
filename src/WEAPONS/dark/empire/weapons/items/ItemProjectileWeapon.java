package dark.empire.weapons.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.core.client.Effects;
import dark.core.prefab.ModPrefab;
import dark.core.prefab.helpers.ItemWorldHelper;
import dark.core.prefab.items.ItemBasic;
import dark.empire.api.weapons.IBullet;
import dark.empire.weapons.EmpireWeapons;

/** Basic Projectile weapon class that stores all the attributes of the weapon as NBT
 * 
 * @author DarkGaurdsman */
public class ItemProjectileWeapon extends ItemBasic
{
    public ItemProjectileWeapon()
    {
        super(ModPrefab.getNextID(), "EWProjectilWeapon", EmpireWeapons.CONFIGURATION);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setNoRepair();
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4)
    {
        ProjectileWeapon weapon = Guns.get(stack.getItemDamage()).weapon;
        if (stack.getTagCompound() == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        String creator = stack.getTagCompound().getString("Creator");
        if (weapon != null)
        {
            if (creator.equalsIgnoreCase("creative") || creator.equalsIgnoreCase(player.username))
            {
                par3List.add("Range: " + weapon.range + "m");
                par3List.add("FallOff: " + weapon.fallOffPerMeter + "m per m");
                par3List.add("FireDelay: " + (weapon.fireDelayTicks / 20) + "s");
                par3List.add("Reload Time: " + (weapon.reloadTicks / 20) + "s");
                par3List.add("Damage: " + weapon.minDamage + " - " + weapon.maxDamage);
            }
            if (!creator.equalsIgnoreCase("creative") && creator == "")
            {
                par3List.add("Created by: " + creator);
            }
            else if (creator.equalsIgnoreCase("creative"))
            {
                par3List.add("Created by Magic Gun Dwarfs");
            }
        }
        else
        {
            par3List.add("No weapon data");
        }
    }

    public ItemStack addComponentToWeapons(ItemStack stack)
    {
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    public static ItemStack createNewWeapon(String creator, ProjectileWeapon weapon, ItemStack stack)
    {
        if (stack != null && stack.getItem() instanceof ItemProjectileWeapon)
        {
            if (stack.getTagCompound() == null)
            {
                stack.setTagCompound(new NBTTagCompound());
            }
            stack.getTagCompound().setCompoundTag("GunData", weapon.save(new NBTTagCompound()));
            stack.getTagCompound().setString("Creator", creator);
        }
        return stack;
    }

    @Override
    public boolean canItemEditBlocks()
    {
        return false;
    }

    @Override
    public Icon getIconFromDamage(int i)
    {
        ProjectileWeapon weapon = Guns.get(i).weapon;
        if (weapon != null)
        {
            return weapon.icon;
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        for (Guns gun : Guns.values())
        {
            gun.weapon.icon = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "Gun_" + gun.weapon.getName());
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer entityPlayer)
    {
        if (entityPlayer != null)
        {
            PlayerTickHandler.onRightClick(entityPlayer.username);
        }
        return par1ItemStack;
    }

    @Override
    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count)
    {
        //TODO use this for automatic weapons
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        //TODO check for close range swing to do melee damage rather than fire the gun.. Or have alt fire for melee attacks
        if (entityLiving instanceof EntityPlayer)
        {
            boolean dontConsumeAmmo = ((EntityPlayer) entityLiving).capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

            ProjectileWeapon weapon = Guns.get(stack.getItemDamage()).weapon;
            Pair<Integer, ItemStack> bullet_item = this.getBullet(((EntityPlayer) entityLiving), weapon.type);
            Bullet bullet = Bullet.TEN_MM;
            ItemStack shell = null;
            System.out.println("Gun Fired");
            if (bullet_item != null && bullet_item.right() != null && bullet_item.right().getItem() instanceof IBullet)
            {
                System.out.println("Has Bullet");
                bullet = ((IBullet) bullet_item.right().getItem()).getBullet(bullet_item.right());
                shell = ((IBullet) bullet_item.right().getItem()).getShell(bullet_item.right());
            }
            if (weapon != null && (dontConsumeAmmo || bullet_item != null) && !weapon.onFired(entityLiving, bullet))
            {
                if (bullet_item != null && !dontConsumeAmmo)
                {
                    if (shell != null)
                    {
                        ItemWorldHelper.dropItemStack(entityLiving.worldObj, new Vector3(entityLiving), shell, true);
                    }
                    ((EntityPlayer) entityLiving).inventory.decrStackSize(bullet_item.left(), 1);

                }
                for (int i = 0; i < bullet.rounds; i++)
                {
                    float damage = weapon.getDamage(entityLiving.worldObj.rand) + bullet.getDamage(entityLiving.worldObj.rand);
                    float delta = weapon.range * weapon.fallOffPerMeter;
                    float par1 = (entityLiving.worldObj.rand.nextFloat() * (entityLiving.worldObj.rand.nextBoolean() ? -delta : delta));
                    float par3 = (entityLiving.worldObj.rand.nextFloat() * (entityLiving.worldObj.rand.nextBoolean() ? -delta : delta));
                    float par5 = (entityLiving.worldObj.rand.nextFloat() * (entityLiving.worldObj.rand.nextBoolean() ? -delta : delta));
                    Vec3 e = new Vector3(par1, par3, par5).toVec3();
                    MovingObjectPosition hitMOP = ProjectileWeaponManager.ray_trace_do(entityLiving.worldObj, entityLiving, e, weapon.range, true);
                    entityLiving.worldObj.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, EmpireWeapons.instance.PREFIX + "shotgun2", 0.5f, 0.7f, true);
                    Vec3 lookVec = entityLiving.getLookVec();
                    if (hitMOP != null)
                    {
                        if (hitMOP.entityHit != null)
                        {
                            DamageSource damageSource = DamageSource.causePlayerDamage((EntityPlayer) entityLiving);
                            if (hitMOP.entityHit.attackEntityFrom(damageSource, damage))
                            {
                                bullet.onImpact(hitMOP.entityHit);
                                hitMOP.entityHit.addVelocity(lookVec.xCoord * 0.2, 0, lookVec.zCoord * 0.2);
                            }
                        }
                        Effects.drawParticleStreamTo(entityLiving, entityLiving.worldObj, hitMOP.hitVec.xCoord, hitMOP.hitVec.yCoord, hitMOP.hitVec.zCoord);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Pair<Integer, ItemStack> getBullet(EntityPlayer player, AmmoType type)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            ItemStack inventoryStack = player.inventory.getStackInSlot(i);
            System.out.println("Slot[" + i + "] Item: " + (inventoryStack != null ? inventoryStack.toString() : "null"));
            if (inventoryStack != null && inventoryStack.getItem() instanceof IBullet)
            {
                boolean bullet = ((IBullet) inventoryStack.getItem()).getBullet(inventoryStack) != null;
                int t = ((IBullet) inventoryStack.getItem()).getType(inventoryStack) != null ? ((IBullet) inventoryStack.getItem()).getType(inventoryStack).ordinal() : -1;
                System.out.println("Is bullet: " + bullet + "   T:" + t);
                if (((IBullet) inventoryStack.getItem()).getBullet(inventoryStack) != null && ((IBullet) inventoryStack.getItem()).getType(inventoryStack) == type)
                {
                    return new Pair<Integer, ItemStack>(i, inventoryStack);
                }
            }
        }

        return null;
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 0.0F;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (Guns gun : Guns.values())
        {
            par3List.add(ItemProjectileWeapon.createNewWeapon("creative", gun.weapon, new ItemStack(this, 1, gun.ordinal())));
        }
    }

    @Override
    public final String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    /** Stores data about default meta value of the guns */
    public static enum Guns
    {
        HandGun(new ProjectileWeapon("HandGun", AmmoType.SMALL, 5, 40, 5, .01f).setMaxRange(500)),
        DBShotGun(new ProjectileWeapon("DBShotGun", AmmoType.SHOTGUN, 10, 31, 5, .05f).setMaxRange(600)),
        PumpShotGun(new ProjectileWeapon("PumpShotGun", AmmoType.SHOTGUN, 10, 21, 25, .05f).setMaxRange(300));
        ProjectileWeapon weapon;

        private Guns(ProjectileWeapon weapon)
        {
            this.weapon = weapon;
        }

        public static Guns get(int itemDamage)
        {
            if (itemDamage < Guns.values().length)
            {
                return Guns.values()[itemDamage];
            }
            return HandGun;
        }
    }

}
