package dark.empire.drone.test;

import net.minecraft.block.material.Material;
import dark.core.prefab.machine.BlockMachine;
import dark.empire.drone.EmpireDrone;

public class BlockRenderTest extends BlockMachine
{
    public BlockRenderTest()
    {
        super(EmpireDrone.CONFIGURATION, "RenderTest", Material.circuits);
    }

}
