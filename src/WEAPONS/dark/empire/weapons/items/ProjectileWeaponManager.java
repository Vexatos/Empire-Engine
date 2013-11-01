package dark.empire.weapons.items;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map.Entry;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import universalelectricity.core.vector.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/** Registers and manages weapons plus upgrades
 *
 * @author DarkGuardsman */
public class ProjectileWeaponManager implements ITickHandler
{
    private static HashMap<String, WeaponUpgrade> weaponUpgrades = new HashMap();
    private static HashMap<String, ProjectileWeapon> weapons = new HashMap();

    /** @param name - not the weapon name but the name used to id it
     * @param weapon - instance of the weapon */
    public static void registerWeapon(String name, ProjectileWeapon weapon)
    {
        if (name != null && !weapons.containsKey(name) && weapon != null && getWeaponID(weapon) == null)
        {
            weapons.put(name, weapon);
        }
    }

    /** @param name - not the weapon name but the name used to id it
     * @param weapon - instance of the weapon upgrade */
    public static void registerUpgrade(String name, WeaponUpgrade upgrade)
    {
        if (name != null && !weapons.containsKey(name) && upgrade != null && getUpgradeID(upgrade) == null)
        {
            weaponUpgrades.put(name, upgrade);
        }
    }

    public static WeaponUpgrade getUpgrade(String name)
    {
        return weaponUpgrades.get(name);
    }

    public static ProjectileWeapon getWeapon(String name)
    {
        return weapons.get(name);
    }

    public static String getUpgradeID(WeaponUpgrade upgrade)
    {
        String id = null;
        for (Entry<String, WeaponUpgrade> entry : weaponUpgrades.entrySet())
        {
            if (entry.getValue() != null && entry.getValue().getName().equalsIgnoreCase(upgrade.getName()))
            {
                return entry.getKey();
            }
        }
        return id;
    }

    public static String getWeaponID(ProjectileWeapon upgrade)
    {
        String id = null;
        for (Entry<String, ProjectileWeapon> entry : weapons.entrySet())
        {
            if (entry.getValue() != null && entry.getValue().getName().equalsIgnoreCase(upgrade.getName()))
            {
                return entry.getKey();
            }
        }
        return id;
    }

    public static boolean fireWeapon(ProjectileWeapon weapon, Entity entity)
    {
        if (entity instanceof EntityPlayer && weapon != null)
        {
            World world = entity.worldObj;
            Vector3 start = new Vector3(entity);
            Vector3 motion = new Vector3();
            float yaw = entity.rotationYaw;
            float pitch = entity.rotationPitch;
            start.x -= (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * 0.16F);
            start.y -= 0.10000000149011612D + (double)entity.getEyeHeight();
            start.z -= (double)(MathHelper.sin(yaw / 180.0F * (float)Math.PI) * 0.16F);
            motion.x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI));
            motion.z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI));
            motion.y = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI));
        }
        return false;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel()
    {
        return "WeaponUpdateTick";
    }

    public static class BulletRay
    {

    }
}
