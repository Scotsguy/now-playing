package com.github.scotsguy.nowplaying;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

@Environment(EnvType.CLIENT)
public class NowPlayingListener implements SoundEventListener {
    @Override
    public void onPlaySound(SoundInstance sound, WeighedSoundEvents soundSet) {
        if (sound.getSource() == SoundSource.MUSIC) {
            Component name = Util.getSoundName(sound);
            if (name == null) return;

            NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();

            if (config.musicStyle == NowPlayingConfig.Style.Toast) {
                Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(name));
            } else if (config.musicStyle == NowPlayingConfig.Style.Hotbar) {
                Minecraft.getInstance().gui.setOverlayMessage(new TranslatableComponent("record.nowPlaying", name), true);
            }
        } else if (sound.getSource() == SoundSource.RECORDS) {
            NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
            if (config.jukeboxStyle != NowPlayingConfig.Style.Toast) return;

            RecordItem disc = Util.getDiscFromSound(sound);
            if (disc == null) return;

            Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(disc.getDisplayName(), new ItemStack(disc)));

        }

    }
}
