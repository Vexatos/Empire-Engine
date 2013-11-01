package dark.empire.weapons.items;

import net.minecraftforge.common.Configuration;
import dark.core.prefab.items.ItemBasic;
import dark.empire.weapons.EmpireWeapons;

/** Class for bullets, clips, and ammo in general
 *
 * @author DarkGuardsman */
public class ItemBullet extends ItemBasic
{

    public ItemBullet()
    {
        super(EmpireWeapons.getNextItemId(), "EWItemBullet", EmpireWeapons.CONFIGURATION);
        this.setHasSubtypes(true);
    }

    public static enum BulletData
    {
        MUSKET_BALL(),
        SMALL_BULLET(),
        MEDIUM_BULLET(),
        HEAVY_BULLET(),
        SHELL(),
        SHOTGUN_SHELL();
    }

}
