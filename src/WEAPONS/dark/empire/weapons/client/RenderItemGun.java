package dark.empire.weapons.client;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.empire.weapons.EWRecipeLoader;
import dark.empire.weapons.EmpireWeapons;
import dark.empire.weapons.client.models.ModelHandGun;
import dark.empire.weapons.items.ItemProjectileWeapon;

@SideOnly(Side.CLIENT)
public class RenderItemGun implements IItemRenderer
{

    private static final ModelHandGun HANDGUN = new ModelHandGun();
    private static final ResourceLocation TEXTURE_HANDGUN = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/" + "HandGun.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        switch (ItemProjectileWeapon.Guns.get(item.getItemDamage()))
        {
            case HandGun:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_HANDGUN);
                break;
            case PumpShotGun:
            case DBShotGun:
            case Sniper:
            default:
        }

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glTranslatef(0, 2f, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glRotatef(20, 0, 1, 0);
        }
        else if(type == ItemRenderType.EQUIPPED)
        {
            float scale = 2f;
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(-105, 0, 0, 1);
            GL11.glRotatef(-70, 0, 1, 0);
            GL11.glTranslatef(0.3f, -0.9f, 0.42f);
        }
        switch (ItemProjectileWeapon.Guns.get(item.getItemDamage()))
        {
            case HandGun:
                HANDGUN.render(0.0625F);
                break;
            case PumpShotGun:
            case DBShotGun:
            case Sniper:
            default:
        }

        GL11.glPopMatrix();
    }
}
