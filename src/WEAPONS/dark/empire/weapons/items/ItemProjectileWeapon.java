package dark.empire.weapons.items;

import net.minecraft.item.ItemStack;
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

    public ItemStack addComponentToWeapons(ItemStack stack)
    {
        return stack;
    }

    public static ItemStack createNewWeapon()
    {
        return null;
    }

}
