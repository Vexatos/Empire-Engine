package dark.empire.weapons.guns;

import java.awt.Color;
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
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.core.helpers.ItemWorldHelper;
import dark.core.helpers.RayTraceHelper;
import dark.core.prefab.ItemBasic;
import dark.core.prefab.ModPrefab;
import dark.empire.api.weapons.AmmoType;
import dark.empire.api.weapons.IBullet;
import dark.empire.api.weapons.IItemBullet;
import dark.empire.weapons.EmpireWeapons;
import dark.machines.DarkMain;
import dark.machines.client.Effects;

/** Basic Projectile weapon class that stores all the attributes of the weapon as NBT
 * 
 * @author DarkGaurdsman */
public class ItemProjectileWeapon extends ItemBasic implements IItemElectric
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
                Pair<Integer, ItemStack> bullet_item = this.getBullet(player, weapon.type);
                float dm = 0, dM = 0, f = 0;
                if (bullet_item != null && bullet_item.right() != null && bullet_item.right().getItem() instanceof IItemBullet)
                {
                    IBullet bullet = ((IItemBullet) bullet_item.right().getItem()).getBullet(bullet_item.right());
                    dm = bullet.getMinDamage();
                    dM = bullet.getMaxDamage();
                    f = bullet.getFallOffBonus();
                }
                par3List.add("Range: " + weapon.range + "m");
                par3List.add("FallOff: " + (weapon.fallOffPerMeter + f) + "m per m");
                par3List.add("FireDelay: " + (weapon.fireDelayTicks / 20) + "s");
                par3List.add("Reload Time: " + (weapon.reloadTicks / 20) + "s");
                par3List.add("Damage: " + (weapon.minDamage + dm) + " - " + (weapon.maxDamage + dM));
            }
            if (!creator.equalsIgnoreCase("creative") && creator != "")
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

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        //TODO use for launcher based weapons
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

    @Override
    public void onCreated(ItemStack stack, World par2World, EntityPlayer entityPlayer)
    {
        this.setElectricity(stack, 0);
        if (stack.getTagCompound() == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (entityPlayer != null)
        {
            stack.getTagCompound().setString("Creator", entityPlayer.username);
        }
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
            IBullet bullet = Bullet.TEN_MM;
            ItemStack shell = null;
            if (bullet_item != null && bullet_item.right() != null && bullet_item.right().getItem() instanceof IItemBullet)
            {
                bullet = ((IItemBullet) bullet_item.right().getItem()).getBullet(bullet_item.right());
                shell = ((IItemBullet) bullet_item.right().getItem()).getShell(bullet_item.right());
            }
            if (weapon != null && (dontConsumeAmmo || bullet_item != null || weapon.type == AmmoType.BATTERY && this.getElectricityStored(stack) > 10) && !weapon.onFired(entityLiving, bullet))
            {
                //Consume ammo
                if (bullet_item != null && !dontConsumeAmmo)
                {
                    if (shell != null)
                    {
                        ItemWorldHelper.dropItemStack(entityLiving.worldObj, new Vector3(entityLiving), shell, true);
                    }
                    ((EntityPlayer) entityLiving).inventory.decrStackSize(bullet_item.left(), 1);

                }
                //Consume energy if energy is consumed per shot
                if (weapon.type == AmmoType.BATTERY && !dontConsumeAmmo)
                {
                    this.discharge(stack, Guns.get(stack.getItemDamage()).costAShot, true);
                }
                //Fire bullets
                for (int i = 0; i < bullet.getShotsFired(); i++)
                {
                    float damage = weapon.getDamage(entityLiving.worldObj.rand) + bullet.getDamage(entityLiving.worldObj.rand);
                    float delta = weapon.range * (weapon.fallOffPerMeter + bullet.getFallOffBonus());
                    float par1 = (entityLiving.worldObj.rand.nextFloat() * (entityLiving.worldObj.rand.nextBoolean() ? -delta : delta));
                    float par3 = (entityLiving.worldObj.rand.nextFloat() * (entityLiving.worldObj.rand.nextBoolean() ? -delta : delta));
                    float par5 = (entityLiving.worldObj.rand.nextFloat() * (entityLiving.worldObj.rand.nextBoolean() ? -delta : delta));
                    Vec3 e = new Vector3(par1, par3, par5).toVec3();
                    Vec3 playerPosition = Vec3.createVectorHelper(entityLiving.posX, entityLiving.posY + entityLiving.getEyeHeight(), entityLiving.posZ);
                    Vec3 playerLook = RayTraceHelper.getLook(entityLiving, 1.0f);
                    Vec3 p = Vec3.createVectorHelper(playerPosition.xCoord + playerLook.xCoord * 2, playerPosition.yCoord + playerLook.yCoord * 2, playerPosition.zCoord + playerLook.zCoord * 2);

                    Vec3 playerViewOffset = Vec3.createVectorHelper(playerPosition.xCoord + playerLook.xCoord * weapon.range, playerPosition.yCoord + playerLook.yCoord * weapon.range + e.yCoord, playerPosition.zCoord + playerLook.zCoord * weapon.range + e.zCoord);

                    MovingObjectPosition hit = RayTraceHelper.do_rayTraceFromEntity(entityLiving, e, weapon.range, true);
                    entityLiving.worldObj.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, EmpireWeapons.instance.PREFIX + "shotgun2", 0.5f, 0.7f, true);
                    Vec3 lookVec = entityLiving.getLookVec();
                    if (hit != null)
                    {
                        if (hit.entityHit != null)
                        {
                            DamageSource damageSource = DamageSource.causePlayerDamage((EntityPlayer) entityLiving);
                            if (hit.entityHit.attackEntityFrom(damageSource, damage))
                            {
                                bullet.onImpact(hit.entityHit);
                                hit.entityHit.addVelocity(lookVec.xCoord * 0.2, 0, lookVec.zCoord * 0.2);
                            }
                        }
                        playerViewOffset = hit.hitVec;
                    }
                    if (Guns.get(stack.getItemDamage()) != Guns.LaserRifle)
                    {
                        Effects.drawParticleStreamTo(entityLiving, entityLiving.worldObj, playerViewOffset.xCoord, playerViewOffset.yCoord, playerViewOffset.zCoord);
                    }
                    else
                    {
                        DarkMain.getInstance();
                        DarkMain.proxy.renderBeam(entityLiving.worldObj, new Vector3(p).translate(new Vector3(0, -.4, 0)), new Vector3(playerViewOffset), Color.RED, 2);
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
            if (inventoryStack != null && inventoryStack.getItem() instanceof IItemBullet)
            {
                boolean bullet = ((IItemBullet) inventoryStack.getItem()).getBullet(inventoryStack) != null;
                int t = ((IItemBullet) inventoryStack.getItem()).getType(inventoryStack) != null ? ((IItemBullet) inventoryStack.getItem()).getType(inventoryStack).ordinal() : -1;
                if (((IItemBullet) inventoryStack.getItem()).getBullet(inventoryStack) != null && ((IItemBullet) inventoryStack.getItem()).getType(inventoryStack) == type)
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
            if (gun.weapon.type == AmmoType.BATTERY)
            {
                ItemStack stack = new ItemStack(this, 1, gun.ordinal());
                this.setElectricity(stack, Guns.get(stack.getItemDamage()).battery);
                par3List.add(ItemProjectileWeapon.createNewWeapon("creative", gun.weapon, stack));

            }
        }
    }

    @Override
    public final String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    /* 888888888888888888888888888888888888888888888888
     * Energy Methods
     * 888888888888888888888888888888888888888888888888 */

    @Override
    public float recharge(ItemStack itemStack, float energy, boolean doReceive)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            float rejectedElectricity = Math.max((this.getElectricityStored(itemStack) + energy) - this.getMaxElectricityStored(itemStack), 0);
            float energyToReceive = energy - rejectedElectricity;

            if (doReceive)
            {
                this.setElectricity(itemStack, this.getElectricityStored(itemStack) + energyToReceive);
            }

            return energyToReceive;
        }
        return 0;
    }

    @Override
    public float discharge(ItemStack itemStack, float energy, boolean doDischarge)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            float energyToTransfer = Math.min(this.getElectricityStored(itemStack), energy);

            if (doDischarge)
            {
                this.setElectricity(itemStack, this.getElectricityStored(itemStack) - energyToTransfer);
            }

            return energyToTransfer;
        }
        return 0;
    }

    @Override
    public float getElectricityStored(ItemStack itemStack)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            if (itemStack.getTagCompound() == null)
            {
                itemStack.setTagCompound(new NBTTagCompound());
            }

            float electricityStored = itemStack.getTagCompound().getFloat("electricity");
            return electricityStored;
        }
        return 0;
    }

    @Override
    public float getMaxElectricityStored(ItemStack itemStack)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            return Guns.get(itemStack.getItemDamage()).battery;
        }
        return 0;
    }

    @Override
    public void setElectricity(ItemStack itemStack, float joules)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            // Saves the frequency in the ItemStack
            if (itemStack.getTagCompound() == null)
            {
                itemStack.setTagCompound(new NBTTagCompound());
            }

            float electricityStored = Math.max(Math.min(joules, this.getMaxElectricityStored(itemStack)), 0);
            itemStack.getTagCompound().setFloat("electricity", electricityStored);
        }

    }

    @Override
    public float getTransfer(ItemStack itemStack)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            return this.getMaxElectricityStored(itemStack) - this.getElectricityStored(itemStack);
        }
        return 0;
    }

    @Override
    public float getVoltage(ItemStack itemStack)
    {
        ProjectileWeapon weapon = Guns.get(itemStack.getItemDamage()).weapon;
        if (weapon != null && weapon.type == AmmoType.BATTERY)
        {
            return .120f;
        }
        return 0;
    }

    /** Stores data about default meta value of the guns */
    public static enum Guns
    {
        HandGun(new ProjectileWeapon("HandGun", AmmoType.SMALL, 1, 40, 5, .01f).setMaxRange(500)),
        DBShotGun(new ProjectileWeapon("DBShotGun", AmmoType.SHOTGUN, 3, 31, 5, .01f).setMaxRange(600)),
        PumpShotGun(new ProjectileWeapon("PumpShotGun", AmmoType.SHOTGUN, 2, 21, 25, .01f).setMaxRange(300)),
        LaserRifle(new ProjectileWeapon("LaserRifle", AmmoType.BATTERY, 10, 21, 25, 0f).setMaxRange(700), 150, 1);
        ProjectileWeapon weapon;
        int battery = 1000, costAShot = 10;

        private Guns(ProjectileWeapon weapon)
        {
            this.weapon = weapon;
        }

        private Guns(ProjectileWeapon weapon, int battery, int costAShot)
        {
            this(weapon);
            this.battery = battery;
            this.costAShot = costAShot;
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
