package dark.empire.weapons.items;

import net.minecraftforge.common.Configuration;
import dark.core.prefab.items.ItemBasic;
import dark.empire.weapons.EmpireWeapons;

public class ItemClip extends ItemBasic
{

    public ItemClip(int itemID, String name, Configuration config)
    {
        super(EmpireWeapons.getNextItemId(), "EWAmmoClip", EmpireWeapons.CONFIGURATION);
    }

}
