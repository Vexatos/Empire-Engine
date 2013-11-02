package dark.empire.weapons.items;

import java.util.Random;

import net.minecraft.entity.Entity;

public class Bullet
{
    public String name;
    public float bulletDamage = 1;
    public int rounds = 1;
    public boolean fire = false, highEx = false, poison = false;

    public static final Bullet NINE_MM = new Bullet("9mm", 1);
    public static final Bullet TEN_MM = new Bullet("10mm", 1);
    public static final Bullet TWELVE_GUAGE = new Bullet("12G", 8);

    public Bullet(String name, int rounds)
    {
        this.name = name;
        this.rounds = rounds;
    }

    public void onImpact(Entity entity)
    {
        if (entity != null)
        {
            if (fire)
            {
                entity.setFire(5);
            }
            if (highEx)
            {
                entity.worldObj.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 1, true);
            }
        }
    }

    public float getDamage(Random rand)
    {
        return rand.nextFloat() * bulletDamage;
    }

}
