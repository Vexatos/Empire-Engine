package dark.empire.core.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderHB1A1 extends RenderLiving
{
    static ModelDroid model = new ModelDroid();

    public RenderHB1A1()
    {
        super(model, 0);
        this.setRenderPassModel(model);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
        //this.loadTexture("/atextures/Bots/Droid.png");
        return 1;
    }

    protected void renderLivingLabel(EntityLiving par1Entity, double par2, double par4, double par6)
    {
        if (Minecraft.isGuiEnabled())
        {
            float var8 = 1.6F;
            float var9 = 0.016666668F * var8;
            float var10 = par1Entity.getDistanceToEntity(this.renderManager.livingPlayer);
            float var11 = par1Entity.isSneaking() ? 4.0F : 32.0F;

            if (var10 < var11)
            {
                String var12 = "HB1-A1";

                FontRenderer var13 = this.getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) par2 + 0.0F, (float) par4 + 2.3F, (float) par6);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(-var9, -var9, var9);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                Tessellator var14 = Tessellator.instance;
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                var14.startDrawingQuads();
                int var15 = var13.getStringWidth(var12) / 2;
                var14.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                var14.addVertex((-var15 - 1), -1.0D, 0.0D);
                var14.addVertex((-var15 - 1), 8.0D, 0.0D);
                var14.addVertex((var15 + 1), 8.0D, 0.0D);
                var14.addVertex((var15 + 1), -1.0D, 0.0D);
                var14.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDepthMask(true);
                var13.drawString(var12, -var13.getStringWidth(var12) / 2, 0, 553648127);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    }

    public void renderBot(Entity bot)
    {

    }

    protected void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
        this.renderLivingLabel(par1EntityLiving, par2, par4, par6);
        this.renderBot(par1EntityLiving);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        // TODO Auto-generated method stub
        return null;
    }

}