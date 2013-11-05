package dark.empire.drone.client.animation;

import universalelectricity.core.vector.Vector3;
import net.minecraft.client.model.ModelRenderer;

public class Part
{
    float yaw = 0f, pitch = 0f;
    final Vector3 defaultPos, defaultRotation;
    String name;
    Part child, parent;
    ModelRenderer peace;
    /** Can the pos be changed */
    boolean canChangePos = true;
    /** Locks the yaw to that of its parent */
    boolean lockedYaw = false;
    /** Locks the pitch to taht of its parent */
    boolean lockedPitch = false;

    public Part(String name, ModelRenderer peace, float yaw, float pitch, Part child, boolean canChangePos)
    {
        this.name = name;
        this.yaw = yaw;
        this.pitch = pitch;
        this.child = child;
        this.peace = peace;
        this.canChangePos = canChangePos;
        child.parent = this;
        defaultPos = new Vector3(peace.rotationPointX, peace.rotationPointY, peace.rotationPointZ);
        defaultRotation = new Vector3(peace.rotateAngleX, peace.rotateAngleY, peace.rotateAngleZ);
    }

    public ModelRenderer reset()
    {
        this.peace.setRotationPoint((float) defaultPos.x, (float) defaultPos.y, (float) defaultPos.z);
        this.peace.rotateAngleX = (float) defaultRotation.x;
        this.peace.rotateAngleY = (float) defaultRotation.y;
        this.peace.rotateAngleZ = (float) defaultRotation.z;
        return peace;
    }
}
