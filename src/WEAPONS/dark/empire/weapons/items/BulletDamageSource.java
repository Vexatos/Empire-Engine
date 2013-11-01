package dark.empire.weapons.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.StatCollector;

public class BulletDamageSource extends EntityDamageSource
{
    public BulletDamageSource(String par1Str, Entity par2Entity)
    {
        super(par1Str, par2Entity);
    }

    /** Returns the message to be displayed on player death. */
    public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase)
    {
        String s = this.damageSourceEntity == null ? this.damageSourceEntity.getTranslatedEntityName() : this.damageSourceEntity.getTranslatedEntityName();
        ItemStack itemstack = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase) this.damageSourceEntity).getHeldItem() : null;
        String s1 = "death.attack." + this.damageType;
        String s2 = s1 + ".item";
        return itemstack != null && itemstack.hasDisplayName() && StatCollector.func_94522_b(s2) ? ChatMessageComponent.createFromTranslationWithSubstitutions(s2, new Object[] { par1EntityLivingBase.getTranslatedEntityName(), s, itemstack.getDisplayName() }) : ChatMessageComponent.createFromTranslationWithSubstitutions(s1, new Object[] { par1EntityLivingBase.getTranslatedEntityName(), s });
    }
}
