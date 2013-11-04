package dark.empire.siege.entities.arty;

import net.minecraft.world.World;

public class EntityMortar extends EntityArty
{

    public EntityMortar(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        this.displayName = "Mortar";
    }

    @Override
    public void fireRound()
    {
        EntityMortarRound round = new EntityMortarRound(this.worldObj);

    }

}
