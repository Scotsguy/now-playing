package com.github.scotsguy.nowplaying.sound;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.jetbrains.annotations.NotNull;

public class NowPlayingListener implements SoundEventListener {
    @Override
    public void onPlaySound(SoundInstance sound, @NotNull WeighedSoundEvents soundSet, float f) {
        if (sound.getSource() == SoundSource.MUSIC) {
            Component name = Sound.getSoundName(sound);

            if (Config.get().options.musicStyle == Config.Options.Style.Toast) {
                Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(name));
            }
            else if (Config.get().options.musicStyle == Config.Options.Style.Hotbar) {
                Minecraft.getInstance().gui.setOverlayMessage(name, true);
            }
        }
        else if (sound.getSource() == SoundSource.RECORDS) {
            if (Config.get().options.jukeboxStyle != Config.Options.Style.Toast) return;

            RecordItem disc = Sound.getDiscFromSound(sound);
            if (disc == null) return;

            Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(disc.getDisplayName(), new ItemStack(disc)));
        }
    }
}
