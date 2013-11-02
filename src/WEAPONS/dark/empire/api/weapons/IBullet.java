package dark.empire.api.weapons;

import net.minecraft.item.ItemStack;
import dark.empire.weapons.items.AmmoType;
import dark.empire.weapons.items.Bullet;

public interface IBullet
{
    /** Bullet class that is used to do calculation when the gun fires this bullet item */
    public Bullet getBullet(ItemStack stack);

    /** Item that is ejected from the weapon when its fired */
    public ItemStack getShell(ItemStack stack);

    /** Weapon type this bullet is for */
    public AmmoType getType(ItemStack stack);
}
