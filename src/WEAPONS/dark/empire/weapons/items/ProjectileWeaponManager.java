package dark.empire.weapons.items;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/** Registers and manages weapons plus upgrades
 *
 * @author DarkGuardsman */
public class ProjectileWeaponManager
{
    private static HashMap<String, WeaponUpgrade> weaponUpgrades = new HashMap();
    private static HashMap<String, ProjectileWeapon> weapons = new HashMap();

    /** @param name - not the weapon name but the name used to id it
     * @param weapon - instance of the weapon */
    public static void registerWeapon(String name, ProjectileWeapon weapon)
    {
        if (name != null && !weapons.containsKey(name) && weapon != null && getWeaponID(weapon) == null)
        {
            weapons.put(name, weapon);
        }
    }

    /** @param name - not the weapon name but the name used to id it
     * @param weapon - instance of the weapon upgrade */
    public static void registerUpgrade(String name, WeaponUpgrade upgrade)
    {
        if (name != null && !weapons.containsKey(name) && upgrade != null && getUpgradeID(upgrade) == null)
        {
            weaponUpgrades.put(name, upgrade);
        }
    }

    public static WeaponUpgrade getUpgrade(String name)
    {
        return weaponUpgrades.get(name);
    }

    public static ProjectileWeapon getWeapon(String name)
    {
        return weapons.get(name);
    }

    public static String getUpgradeID(WeaponUpgrade upgrade)
    {
        String id = null;
        for (Entry<String, WeaponUpgrade> entry : weaponUpgrades.entrySet())
        {
            if (entry.getValue() != null && entry.getValue().getName().equalsIgnoreCase(upgrade.getName()))
            {
                return entry.getKey();
            }
        }
        return id;
    }

    public static String getWeaponID(ProjectileWeapon upgrade)
    {
        String id = null;
        for (Entry<String, ProjectileWeapon> entry : weapons.entrySet())
        {
            if (entry.getValue() != null && entry.getValue().getName().equalsIgnoreCase(upgrade.getName()))
            {
                return entry.getKey();
            }
        }
        return id;
    }

    public static MovingObjectPosition raytraceEntities(World world, Entity entity, double reachDistance, boolean collisionFlag)
    {

        MovingObjectPosition pickedEntity = null;
        Vec3 playerPosition = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3 playerLook = entity.getLookVec();

        Vec3 playerViewOffset = Vec3.createVectorHelper(playerPosition.xCoord + playerLook.xCoord * reachDistance, playerPosition.yCoord + playerLook.yCoord * reachDistance, playerPosition.zCoord + playerLook.zCoord * reachDistance);

        double playerBorder = 1.1 * reachDistance;
        AxisAlignedBB boxToScan = entity.boundingBox.expand(playerBorder, playerBorder, playerBorder);

        List entitiesHit = world.getEntitiesWithinAABBExcludingEntity(entity, boxToScan);
        double closestEntity = reachDistance;

        if (entitiesHit == null || entitiesHit.isEmpty())
        {
            return null;
        }
        for (Entity entityHit : (Iterable<Entity>) entitiesHit)
        {
            if (entityHit != null && entityHit.canBeCollidedWith() && entityHit.boundingBox != null)
            {
                float border = entityHit.getCollisionBorderSize();
                AxisAlignedBB aabb = entityHit.boundingBox.expand((double) border, (double) border, (double) border);
                MovingObjectPosition hitMOP = aabb.calculateIntercept(playerPosition, playerViewOffset);

                if (hitMOP != null)
                {
                    if (aabb.isVecInside(playerPosition))
                    {
                        if (0.0D < closestEntity || closestEntity == 0.0D)
                        {
                            pickedEntity = new MovingObjectPosition(entityHit);
                            if (pickedEntity != null)
                            {
                                pickedEntity.hitVec = hitMOP.hitVec;
                                closestEntity = 0.0D;
                            }
                        }
                    }
                    else
                    {
                        double distance = playerPosition.distanceTo(hitMOP.hitVec);

                        if (distance < closestEntity || closestEntity == 0.0D)
                        {
                            pickedEntity = new MovingObjectPosition(entityHit);
                            pickedEntity.hitVec = hitMOP.hitVec;
                            closestEntity = distance;
                        }
                    }
                }
            }
        }
        return pickedEntity;
    }

    public static MovingObjectPosition raytraceBlocks(World world, Entity entity, double reachDistance, boolean collisionFlag)
    {
        Vec3 playerPosition = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3 playerLook = entity.getLookVec();

        Vec3 playerViewOffset = Vec3.createVectorHelper(playerPosition.xCoord + playerLook.xCoord * reachDistance, playerPosition.yCoord + playerLook.yCoord * reachDistance, playerPosition.zCoord + playerLook.zCoord * reachDistance);
        return world.rayTraceBlocks_do_do(playerPosition, playerViewOffset, collisionFlag, !collisionFlag);
    }

    public static MovingObjectPosition ray_trace_do(World world, Entity entity, double reachDistance, boolean collisionFlag)
    {
        MovingObjectPosition hitBlock = raytraceBlocks(world, entity, reachDistance, collisionFlag);
        MovingObjectPosition hitEntity = raytraceEntities(world, entity, reachDistance, collisionFlag);
        if (hitEntity == null)
        {
            return hitBlock;
        }
        else if (hitBlock == null)
        {
            return hitEntity;
        }
        else
        {
            Vec3 playerPosition = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
            if (hitEntity.hitVec.distanceTo(playerPosition) <  hitBlock.hitVec.distanceTo(playerPosition))
            {
                return hitEntity;
            }
            else
            {
                return hitBlock;
            }
        }
    }
}
