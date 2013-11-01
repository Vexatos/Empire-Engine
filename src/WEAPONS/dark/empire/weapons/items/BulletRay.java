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
    public Entity attacker;
    public float velocity = 0;
    public Vector3 position;
    public Vector3 motion;
    public boolean dead = false;
    public ProjectileWeapon weapon;
    public Bullet bullet;

    public BulletRay(Entity attacker, ProjectileWeapon weapon, Bullet bullet, float velocity, Vector3 position, Vector3 motion)
    {
        this.attacker = attacker;
        this.velocity = velocity;
        this.position = position;
        this.motion = motion;
        this.weapon = weapon;
        this.bullet = bullet;
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

        if (this.attacker != null)
        {
            Vec3 vec3 = this.attacker.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x, this.position.y, this.position.z);
            Vec3 vec31 = this.attacker.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x + this.motion.x, this.position.y + this.motion.y, this.position.z + this.motion.z);
            MovingObjectPosition movingobjectposition = this.attacker.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
            vec3 = this.attacker.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x, this.position.y, this.position.z);
            vec31 = this.attacker.worldObj.getWorldVec3Pool().getVecFromPool(this.position.x + this.motion.x, this.position.y + this.motion.y, this.position.z + this.motion.z);

            if (movingobjectposition != null)
            {
                vec31 = this.attacker.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.attacker.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(0, 0, 0, 0.1f, 0.1f, 0.1f).addCoord(this.motion.x, this.motion.y, this.motion.z).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float f1;

            for (l = 0; l < list.size(); ++l)
            {
                Entity entity1 = (Entity) list.get(l);

                if (entity1.canBeCollidedWith() && (entity1 != this.attacker || this.ticks >= 5))
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

                if (entityplayer.capabilities.disableDamage || this.attacker instanceof EntityPlayer && !((EntityPlayer) this.attacker).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            float f2;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
                    f2 = MathHelper.sqrt_double(this.motion.x * this.motion.x + this.motion.y * this.motion.y + this.motion.z * this.motion.z);
                    int i1 = MathHelper.ceiling_double_int((double) f2 * this.weapon.getDamage(this.attacker.worldObj.rand));

                    DamageSource damagesource = BulletRay.causeArrowDamage(this.attacker);

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) i1))
                    {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

                            EnchantmentThorns.func_92096_a(this.attacker, entitylivingbase, this.attacker.worldObj.rand);

                            if (movingobjectposition.entityHit != this.attacker && movingobjectposition.entityHit instanceof EntityPlayer && this.attacker instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP) this.attacker).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                            }
                        }
                    }
                }
                else
                {
                    int xTile = movingobjectposition.blockX;
                    int yTile = movingobjectposition.blockY;
                    int zTile = movingobjectposition.blockZ;
                    int inTile = this.attacker.worldObj.getBlockId(xTile, yTile, zTile);

                    if (inTile != 0)
                    {
                        try
                        {
                            Block.blocksList[inTile].onEntityCollidedWithBlock(this.attacker.worldObj, xTile, yTile, zTile, null);
                        }
                        catch (Exception e)
                        {

                        }
                    }

                }
                this.dead = true;
            }
        }
        else
        {
            this.dead = true;
        }
    }
}
