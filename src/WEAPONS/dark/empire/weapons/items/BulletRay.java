package dark.empire.weapons.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;
import dark.core.prefab.helpers.MathHelper;

/** Used by bullet or projectiles that move so fast that an entity can no be used to track them
 * correctly.
 *
 * @author DarkGuardsman */
public class BulletRay
{
    public static float mass = 4.2f;
    public static float friction = 1;
    public static float gravity = 9.8f;
    public int ticks = 0;
    public Entity shooter;
    public float velocity = 0;
    public Vector3 position;
    public Vector3 motion;
    public boolean dead = false;
    public ProjectileWeapon weapon;
    public Bullet bullet;
    public World worldObj;

    public BulletRay(Entity attacker, ProjectileWeapon weapon, Bullet bullet)
    {
        this(attacker.worldObj, attacker.rotationYaw, attacker.rotationPitch, new Vector3(attacker), weapon, bullet);
        this.shooter = attacker;
        this.position.y += (double) attacker.getEyeHeight();
    }

    public BulletRay(World world, float yaw, float pitch, Vector3 pos, ProjectileWeapon weapon, Bullet bullet)
    {
        this.worldObj = world;
        this.weapon = weapon;
        this.bullet = bullet;
        this.position = pos;
        this.motion = new Vector3();
        this.position.x -= (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.position.y -= 0.10000000149011612D;
        this.position.z -= (double) (MathHelper.sin(yaw / 180.0F * (float) Math.PI) * 0.16F);
        motion.x = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)) * (bullet.velocity/ 20);
        motion.z = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)) * (bullet.velocity/ 20);
        motion.y = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI)) * (bullet.velocity/ 20);
    }

    public void setShooter(Entity entity)
    {
        this.shooter = entity;
    }

    /** returns EntityDamageSourceIndirect of an arrow */
    public static DamageSource causeArrowDamage(Entity par1Entity)
    {
        return (new BulletDamageSource("bullet", par1Entity)).setProjectile();
    }

    public void update()
    {
        ++this.ticks;
        this.collisionCheck();
        this.updateMotion();
        if (this.ticks >= 100)
        {
            this.dead = true;
        }

    }

    public void updateMotion()
    {
        this.position.translate(this.motion);
        this.motion.y -= BulletRay.gravity;
        this.motion.scale(0.85f);
    }

    public void collisionCheck()
    {
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x, this.position.y, this.position.z);
        Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x + this.motion.x, this.position.y + this.motion.y, this.position.z + this.motion.z);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
        vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x, this.position.y, this.position.z);
        vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x + this.motion.x, this.position.y + this.motion.y, this.position.z + this.motion.z);

        if (movingobjectposition != null)
        {
            vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(0, 0, 0, 0.1f, 0.1f, 0.1f).addCoord(this.motion.x, this.motion.y, this.motion.z).expand(1.0D, 1.0D, 1.0D));
        double d0 = 0.0D;
        int l;
        float f1;

        for (l = 0; l < list.size(); ++l)
        {
            Entity entity1 = (Entity) list.get(l);

            if (entity1.canBeCollidedWith() && (entity1 != this.shooter || this.ticks >= 5))
            {
                f1 = 0.3F;
                AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
                MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

                if (movingobjectposition1 != null)
                {
                    double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

            if (entityplayer.capabilities.disableDamage || this.shooter instanceof EntityPlayer && !((EntityPlayer) this.shooter).canAttackPlayer(entityplayer))
            {
                movingobjectposition = null;
            }
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit != null)
            {
                int i1 = MathHelper.ceiling_double_int(this.weapon.getDamage(this.worldObj.rand) + this.bullet.getDamage(this.worldObj.rand));

                DamageSource damagesource;
                if (this.shooter == null)
                {
                    damagesource = DamageSource.fallingBlock;
                }
                else
                {
                    damagesource = BulletRay.causeArrowDamage(this.shooter);
                }

                if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) i1))
                {
                    this.bullet.onImpact(movingobjectposition.entityHit, this.motion);
                    if (movingobjectposition.entityHit instanceof EntityLivingBase)
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

                        EnchantmentThorns.func_92096_a(this.shooter, entitylivingbase, this.worldObj.rand);

                        if (movingobjectposition.entityHit != this.shooter && movingobjectposition.entityHit instanceof EntityPlayer && this.shooter instanceof EntityPlayerMP)
                        {
                            ((EntityPlayerMP) this.shooter).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                        }
                    }
                }
            }
            else
            {
                int xTile = movingobjectposition.blockX;
                int yTile = movingobjectposition.blockY;
                int zTile = movingobjectposition.blockZ;
                int inTile = this.worldObj.getBlockId(xTile, yTile, zTile);

                if (inTile != 0)
                {
                    try
                    {
                        Block.blocksList[inTile].onEntityCollidedWithBlock(this.worldObj, xTile, yTile, zTile, null);
                    }
                    catch (Exception e)
                    {

                    }
                }

            }
            this.dead = true;
        }
    }
}
