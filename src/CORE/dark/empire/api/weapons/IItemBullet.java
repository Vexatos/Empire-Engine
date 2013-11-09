package dark.empire.api.weapons;

import net.minecraft.item.ItemStack;

public interface IItemBullet
{
    /** Bullet class that is used to do calculation when the gun fires this bullet item */
    public IBullet getBullet(ItemStack stack);

    /** Item that is ejected from the weapon when its fired */
    public ItemStack getShell(ItemStack stack);

    /** Weapon type this bullet is for */
    public AmmoType getType(ItemStack stack);
}
