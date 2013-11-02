package dark.empire.weapons.items;

import java.util.Random;

import universalelectricity.core.vector.Vector3;

import net.minecraft.entity.Entity;

public class Bullet
{
    public float radius = 5;
    public float velocity;
    public float mass;
    public String name;
    public float bulletDamage = 1;

    public static final Bullet NINE_MM = new Bullet("9mm", 1100f, 4.5f, 8f);

    public Bullet(String name, float velocity, float radius, float mass)
    {
        this.name = name;
        this.velocity = velocity;
        this.radius = radius;
        this.mass = mass;
    }

    public void onImpact(Entity entity)
    {

    }

    public float getDamage(Random rand)
    {
        return rand.nextFloat() * bulletDamage;
    }

}
