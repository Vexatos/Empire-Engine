package dark.empire.weapons.guns;

import java.util.Random;

import net.minecraft.entity.Entity;
import dark.empire.api.weapons.IBullet;

public class Bullet implements IBullet
{
    public String name;
    public float bulletDamage = 5, bulletDamageMax = 6, fallOff = 0.001f;
    public int rounds = 1;
    public boolean fire = false, highEx = false, poison = false;

    public static final Bullet NINE_MM = new Bullet("9mm", 1, 5f, 10f, .007f);
    public static final Bullet TEN_MM = new Bullet("10mm", 1, 7f, 11f, .006f);
    public static final Bullet Thirteen_MM = new Bullet("13mm", 1, 9f, 16f, .005f);
    public static final Bullet Thirteen_MM_Ball = new Bullet("13mmBall", 1, 7f, 14f, .02f);
    public static final Bullet TWELVE_GUAGE = new Bullet("12G", 8, 5f, 8f, .04f);

    public Bullet(String name, int rounds, float minD, float maxD, float fallOff)
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

    @Override
    public float getMinDamage()
    {
        return this.bulletDamage;
    }

    @Override
    public float getMaxDamage()
    {
        return this.bulletDamageMax;
    }

    @Override
    public float getFallOffBonus()
    {
        return this.fallOff;
    }

    @Override
    public int getShotsFired()
    {
        return this.rounds;
    }

}
