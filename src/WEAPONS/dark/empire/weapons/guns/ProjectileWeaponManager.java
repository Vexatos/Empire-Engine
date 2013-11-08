package dark.empire.weapons.guns;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/** Registers and manages weapons plus upgrades
 * 
 * @author DarkGuardsman */
public class ProjectileWeaponManager
{
    public static HashMap<String, WeaponUpgrade> weaponUpgrades = new HashMap();
    public static HashMap<String, ProjectileWeapon> weapons = new HashMap();

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
        String id = "Unkown";
        for (Entry<String, ProjectileWeapon> entry : weapons.entrySet())
        {
            if (entry.getValue() != null && entry.getValue().getName().equalsIgnoreCase(upgrade.getName()))
            {
                return entry.getKey();
            }
        }
        return id;
    }

    public static ProjectileWeapon getWeapon(ItemStack stack)
    {
        ProjectileWeapon weapon = null;
        if (stack != null)
        {
            if (stack.getTagCompound() == null)
            {
                stack.setTagCompound(new NBTTagCompound());
            }
            return ProjectileWeapon.createAndLoadWeapon(stack.getTagCompound().getCompoundTag("GunData"));
        }

        return weapon;
    }

}
