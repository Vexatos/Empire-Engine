package dark.empire.weapons.block;

import dark.empire.weapons.EmpireWeapons;
import net.minecraft.block.material.Material;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.block.BlockTile;

/** Block designed to store weapons on such as guns, and swords
 *
 * @author DarkGuardsman */
public class BlockWeaponRack extends BlockTile
{

    public BlockWeaponRack(int id, Material material)
    {
        super(EmpireWeapons.CONFIGURATION.getBlock("EWWeaponRack", EmpireWeapons.getNextID()).getInt(), UniversalElectricity.machine);
        this.setUnlocalizedName("EWWeaponRack");
    }

    public static enum RackData
    {
        /** Item frame for a gun but more 3D */
        SINGLE(),
        /** 2 Item frame for a gun but more 3D. Guns stack on top of each other */
        DOUBLE(),
        /** 4 gun single side wall mounted rack */
        QUAD();
    }

}
