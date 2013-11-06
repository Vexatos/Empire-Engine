package dark.empire.core.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import dark.empire.api.IEmpireEntity;
import dark.empire.core.ai.FOFSystem;
import dark.empire.core.empire.Empire;

public class EntityNPC extends EntityCreature implements IEmpireEntity
{
    protected FOFSystem targetingSystem;
    protected Empire empire;
    protected String name;

    public EntityNPC(World world)
    {
        super(world);
    }

    public FOFSystem getTargeting()
    {
        if (targetingSystem == null)
        {
            targetingSystem = new FOFSystem(this);
        }
        return targetingSystem;
    }

    @Override
    public Empire getEmpire()
    {
        return empire;
    }

    @Override
    public void setEmpire(Empire empire)
    {
        this.empire = empire;
    }

    @Override
    protected void dropEquipment(boolean par1, int par2)
    {
        for (int j = 0; j < this.getLastActiveItems().length; ++j)
        {

            this.entityDropItem(this.getCurrentItemOrArmor(j), 0.0F);
        }
    }

    @Override
    protected boolean interact(EntityPlayer par1EntityPlayer)
    {
        //TODO open up a chat window with the NPC
        return false;
    }
}
