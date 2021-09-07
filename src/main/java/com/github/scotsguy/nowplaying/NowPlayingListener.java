package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.config.NowPlayingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NowPlayingListener implements SoundEventListener {

    @Override
    public void onPlaySound(SoundInstance sound, WeighedSoundEvents accessor) {
        if (sound.getSource() == SoundSource.MUSIC) {
            TranslatableComponent name = Util.getSoundName(sound);
            if (name == null) return;
            if (NowPlayingConfig.musicStyle.get() == Config.Style.Toast) {
                Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(name));
            } else if (NowPlayingConfig.musicStyle.get() == Config.Style.Hotbar) {
                Minecraft.getInstance().gui.setOverlayMessage(new TranslatableComponent("record.nowPlaying", name), true);
            }
        } else if (sound.getSource() == SoundSource.RECORDS) {
            RecordItem disc = Util.getDiscFromSound(sound);
            if (disc == null) return;
            if (NowPlayingConfig.jukeboxStyle.get() != Config.Style.Toast) return;
            Minecraft.getInstance().getToasts().addToast(new NowPlayingToast(disc.getDescription(), new ItemStack(disc)));
        }
    }
}
