package dark.empire.weapons.items;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/** Registers and manages weapons plus upgrades
 *
 * @author DarkGuardsman */
public class ProjectileWeaponManager implements ITickHandler
{
    private static HashMap<String, WeaponUpgrade> weaponUpgrades = new HashMap();
    private static HashMap<String, ProjectileWeapon> weapons = new HashMap();
    private static List<BulletRay> firedBullets = new ArrayList();

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

    public static boolean fireWeapon(ProjectileWeapon weapon, Bullet bullet, Entity entity)
    {
        if (entity instanceof EntityPlayer && weapon != null)
        {
            firedBullets.add(new BulletRay(entity, weapon, bullet));
        }
        return false;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        Iterator<BulletRay> it = firedBullets.iterator();
        while (it.hasNext())
        {
            BulletRay ray = it.next();
            ray.update();
            if (ray.dead)
            {
                it.remove();
            }
            else if (ray.ticks >= 1000)
            {
                it.remove();
            }
        }
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
}
