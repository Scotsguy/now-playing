package com.github.scotsguy.nowplaying.sound;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import com.github.scotsguy.nowplaying.mixin.GuiAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

public class NowPlayingListener implements SoundEventListener {
    @Override
    public void onPlaySound(@NotNull SoundInstance sound, @NotNull WeighedSoundEvents soundSet, float f) {
        Config config = Config.get();
        Minecraft minecraft = Minecraft.getInstance();
        Component name = Sound.getSoundName(sound);

        if (sound.getSource() == SoundSource.MUSIC) {
            Component message = Component.translatable("record.nowPlaying", name);

            if (config.options.musicStyle == Config.Options.Style.Toast) {
                minecraft.getToasts().addToast(new NowPlayingToast(name));
            }
            else if (config.options.musicStyle == Config.Options.Style.Hotbar) {
                minecraft.gui.setOverlayMessage(message, true);
                ((GuiAccessor)minecraft.gui).setOverlayMessageTime(config.options.hotbarTime * 20);
            }

            if (config.options.narrate) {
                minecraft.getNarrator().sayNow(message);
            }
        }
    }
}
