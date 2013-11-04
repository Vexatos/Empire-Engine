package dark.empire.siege.entities.arty;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import dark.core.interfaces.IExternalInv;
import dark.core.interfaces.IInvBox;
import dark.core.prefab.invgui.InvChest;
import dark.empire.api.weapons.IBullet;

public abstract class EntityArty extends Entity implements IExternalInv
{

    protected IInvBox inventory;
    protected String displayName = "Arty";

    public EntityArty(World world)
    {
        super(world);
    }

    public EntityArty(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    protected void entityInit()
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        this.getInventory().loadInv(nbt.getCompoundTag("items"));

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setCompoundTag("items", this.getInventory().saveInv(new NBTTagCompound()));
    }

    @Override
    public IInvBox getInventory()
    {
        if (inventory == null)
        {
            inventory = new InvChest(this, 1);
        }
        return inventory;
    }

    @Override
    public boolean canStore(ItemStack stack, int slot, ForgeDirection side)
    {
        if (stack != null && stack.getItem() instanceof IBullet)
        {
            return ((IBullet) stack.getItem()).getBullet(stack) != null;
        }
        return false;
    }

    @Override
    public boolean canRemove(ItemStack stack, int slot, ForgeDirection side)
    {
        return true;
    }

    /** Called when the arty is activated to fire */
    public void onActivated()
    {
        if (!this.worldObj.isRemote && hasAmmo())
        {
            this.fireRound();
        }
    }

    public boolean hasAmmo()
    {
        return true;
    }

    public abstract void fireRound();

}
