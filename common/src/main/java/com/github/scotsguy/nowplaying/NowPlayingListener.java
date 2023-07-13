package com.github.scotsguy.nowplaying;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

@Environment(EnvType.CLIENT)
public class NowPlayingListener implements SoundEventListener {
    @Override
    public void onPlaySound(SoundInstance soundInstance, WeighedSoundEvents soundEvents) {
        Options options = Minecraft.getInstance().options;
        SoundSource soundSource = soundInstance.getSource();
        if (options.getSoundSourceVolume(SoundSource.MASTER) <= 0.0 || options.getSoundSourceVolume(soundSource) <= 0.0) {
            return; // Minecraft occasionally likes to play silent sounds
        }
        if (soundSource == SoundSource.MUSIC) {
            Component name = Util.getSoundName(soundInstance);
            if (name == null) return;

            NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();

            if (config.musicStyle == NowPlayingConfig.Style.Toast) {
                Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(name));
            } else if (config.musicStyle == NowPlayingConfig.Style.Hotbar) {
                Minecraft.getInstance().gui.setNowPlaying(name);
            }
        } else if (soundSource == SoundSource.RECORDS) {
            NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
            if (config.jukeboxStyle != NowPlayingConfig.Style.Toast) return;

            RecordItem disc = Util.getDiscFromSound(soundInstance);
            if (disc == null) return;

            Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(disc.getDisplayName(), new ItemStack(disc)));

        }

    }
}
