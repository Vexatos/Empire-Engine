package dark.empire.core.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;

/** Friend or foe system that can be used by an entity to create a targeting system for logicily
 * targeting entities
 *
 * @author DarkGuardsman */
public class FOFSystem
{
    protected Entity hostEntity;
    protected boolean targetEmpire = true, targetPlayer = false, targetMonsters = true, targetNeuts = false, targetAnimals = false;

    public FOFSystem(Entity entity)
    {
        this.hostEntity = entity;
    }

    public boolean isMonster(Entity entity)
    {
        return entity instanceof IMob;
    }

    public boolean canTarget(Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            return true;
        }
        return false;
    }
}
