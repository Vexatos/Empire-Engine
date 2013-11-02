// Date: 11/2/2013 1:23:41 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package net.minecraft.src;

public class ModelHandGun extends ModelBase
{
  //fields
    ModelRenderer Grip;
    ModelRenderer body2;
    ModelRenderer Grip2;
    ModelRenderer guard;
    ModelRenderer barrel;
    ModelRenderer body4;
    ModelRenderer body5;
    ModelRenderer body6;
    ModelRenderer body6;
    ModelRenderer chamber;
    ModelRenderer barrelRid;
    ModelRenderer pump1;
    ModelRenderer pump2;
  
  public ModelHandGun()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Grip = new ModelRenderer(this, 10, 24);
      Grip.addBox(0F, 0F, 0F, 2, 5, 2);
      Grip.setRotationPoint(0F, 19F, 0F);
      Grip.setTextureSize(64, 32);
      Grip.mirror = true;
      setRotation(Grip, 1.134464F, 0F, 0F);
      body2 = new ModelRenderer(this, 0, 0);
      body2.addBox(0F, 0F, 0F, 2, 1, 5);
      body2.setRotationPoint(0F, 18F, -3F);
      body2.setTextureSize(64, 32);
      body2.mirror = true;
      setRotation(body2, 0F, 0F, 0F);
      Grip2 = new ModelRenderer(this, 0, 0);
      Grip2.addBox(0F, 0F, 0F, 1, 5, 1);
      Grip2.setRotationPoint(0.5F, 19F, 0F);
      Grip2.setTextureSize(64, 32);
      Grip2.mirror = true;
      setRotation(Grip2, 1.047198F, 0F, 0F);
      guard = new ModelRenderer(this, 0, 0);
      guard.addBox(0F, 0F, 0F, 1, 2, 1);
      guard.setRotationPoint(0.5F, 19F, -2F);
      guard.setTextureSize(64, 32);
      guard.mirror = true;
      setRotation(guard, 0F, 0F, 0F);
      barrel = new ModelRenderer(this, 25, 0);
      barrel.addBox(-1F, 0F, 0F, 2, 2, 13);
      barrel.setRotationPoint(1F, 16F, -18.01F);
      barrel.setTextureSize(64, 32);
      barrel.mirror = true;
      setRotation(barrel, 0F, 0F, 0F);
      body4 = new ModelRenderer(this, 0, 0);
      body4.addBox(0F, 0F, 0F, 2, 3, 3);
      body4.setRotationPoint(0F, 17F, 2F);
      body4.setTextureSize(64, 32);
      body4.mirror = true;
      setRotation(body4, 0F, 0F, 0F);
      body5 = new ModelRenderer(this, 0, 0);
      body5.addBox(1.5F, 0F, 0F, 3, 4, 5);
      body5.setRotationPoint(-2F, 16.5F, 5F);
      body5.setTextureSize(64, 32);
      body5.mirror = true;
      setRotation(body5, -0.0698132F, 0F, 0F);
      body6 = new ModelRenderer(this, 0, 0);
      body6.addBox(-1.5F, 0F, 0F, 3, 2, 1);
      body6.setRotationPoint(1F, 16.1F, 1F);
      body6.setTextureSize(64, 32);
      body6.mirror = true;
      setRotation(body6, 0F, 0F, 0F);
      body6 = new ModelRenderer(this, 0, 0);
      body6.addBox(1.5F, 0F, 0F, 3, 4, 5);
      body6.setRotationPoint(-2F, 16.5F, 5F);
      body6.setTextureSize(64, 32);
      body6.mirror = true;
      setRotation(body6, 0.122173F, 0F, 0F);
      chamber = new ModelRenderer(this, 25, 0);
      chamber.addBox(-1.5F, 0F, 0F, 3, 3, 6);
      chamber.setRotationPoint(1F, 15.8F, -5.01F);
      chamber.setTextureSize(64, 32);
      chamber.mirror = true;
      setRotation(chamber, 0F, 0F, 0F);
      barrelRid = new ModelRenderer(this, 25, 0);
      barrelRid.addBox(-0.5F, 0F, 0F, 1, 1, 13);
      barrelRid.setRotationPoint(1F, 18F, -17.01F);
      barrelRid.setTextureSize(64, 32);
      barrelRid.mirror = true;
      setRotation(barrelRid, 0F, 0F, 0F);
      pump1 = new ModelRenderer(this, 25, 0);
      pump1.addBox(-0.7F, 0F, 0F, 1, 1, 5);
      pump1.setRotationPoint(1F, 18.2F, -14.01F);
      pump1.setTextureSize(64, 32);
      pump1.mirror = true;
      setRotation(pump1, 0F, 0F, 0F);
      pump2 = new ModelRenderer(this, 25, 0);
      pump2.addBox(0.7F, 0F, 0F, 1, 1, 5);
      pump2.setRotationPoint(0F, 18.2F, -14.01F);
      pump2.setTextureSize(64, 32);
      pump2.mirror = true;
      setRotation(pump2, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Grip.render(f5);
    body2.render(f5);
    Grip2.render(f5);
    guard.render(f5);
    barrel.render(f5);
    body4.render(f5);
    body5.render(f5);
    body6.render(f5);
    body6.render(f5);
    chamber.render(f5);
    barrelRid.render(f5);
    pump1.render(f5);
    pump2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}
