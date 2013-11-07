package dark.empire.core.client;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;
import dark.empire.api.IVehicle;

public class PlayerRenderHandler
{
    @ForgeSubscribe
    public void onPlayerRender(RenderPlayerEvent.Pre event)
    {
        //TODO turn off player render if the player is in a vehicle then have the vehicle render the player
        if (event.entityPlayer.isRiding() && event.entityPlayer.ridingEntity instanceof IVehicle)
        {
            event.setCanceled(true);
        }
        else if (event.entityPlayer.username.equalsIgnoreCase("darkguardsman"))
        {
            //TODO render a model for my own character rather than the default one
            event.setCanceled(true);
        }
        else if (event.entityPlayer.username.equalsIgnoreCase("doppelgangerous"))
        {
            //TODO allow the doppel to render as something else
        }
        else if (event.entityPlayer.username.equalsIgnoreCase("hangcow"))
        {
            //TODO render horns on the cow
        }
    }
}
