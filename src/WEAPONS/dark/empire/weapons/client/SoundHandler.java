package dark.empire.weapons.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.empire.weapons.EmpireWeapons;

@SideOnly(Side.CLIENT)
public class SoundHandler
{
    public static final SoundHandler INSTANCE = new SoundHandler();

    public static final String[] SOUND_FILES = { "shotgun2.ogg" };

    @ForgeSubscribe
    public void loadSoundEvents(SoundLoadEvent event)
    {
        for (int i = 0; i < SOUND_FILES.length; i++)
        {
            event.manager.soundPoolSounds.addSound(EmpireWeapons.instance.PREFIX + SOUND_FILES[i]);
        }
    }
}
