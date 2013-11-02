package dark.empire.weapons.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import dark.core.prefab.ModPrefab;
import dark.core.prefab.items.ItemBasic;
import dark.empire.weapons.EmpireWeapons;

public class ItemClip extends ItemBasic
{

    public ItemClip()
    {
        super(ModPrefab.getNextItemId(), "EWAmmoClip", EmpireWeapons.CONFIGURATION);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public boolean hasBullets(ItemStack stack)
    {
        return false;
    }

    public Bullet getBullet(ItemStack stack)
    {
        return null;
    }

    public boolean addBullet(ItemStack stack)
    {
        return false;
    }

    public boolean removeBullet(ItemStack stack)
    {
        return false;
    }

    public static enum Clip
    {
        REV_LOADER("Revolver Loader", 6, 1),
        SMALL_CLIP("Small Cal Clip", 15, 1),
        SMALL_CLIP_EXTENDED("Small Cal Clip", 30, 1),
        MEDIUM_CLIP("Clip", 20, 1),
        MEDIUM_CLIP_EXTENDED("Clip", 30, 1),
        MEDIUM_CLIP_DRUM("Drum Clip", 60, 1),
        LARGE_CLIP("Large Cal Clip", 10, 1),
        LARGE_CLIP_EXTENDED("Large Cal Clip", 20, 1),
        LARGE_CLIP_DRUM("Large Cal Clip", 40, 1),
        BELT("Belt", 120, 1),
        BOX("Ammo Box", 240, 1);
        int space;
        String name;
        Icon[] icons;

        private Clip(String name, int space, int icons)
        {
            this.name = name;
            this.space = space;
            this.icons = new Icon[icons];
        }
    }

}
