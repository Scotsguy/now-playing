package com.github.scotsguy.nowplaying;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class NowPlayingListener implements SoundInstanceListener {
    @Override
    public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
        if (sound.getCategory() == SoundCategory.MUSIC) {
            Text name = Util.getSoundName(sound);
            if (name == null) return;

            NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();

            if (config.musicStyle == NowPlayingConfig.Style.Toast) {
                MinecraftClient.getInstance().getToastManager().add(new NowPlayingToast(name));
            } else if (config.musicStyle == NowPlayingConfig.Style.Hotbar) {
                MinecraftClient.getInstance().inGameHud.setOverlayMessage(new TranslatableText("record.nowPlaying", name), true);
            }
        } else if (sound.getCategory() == SoundCategory.RECORDS) {
            NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
            if (config.jukeboxStyle != NowPlayingConfig.Style.Toast) return;

            MusicDiscItem disc = Util.getDiscFromSound(sound);
            if (disc == null) return;

            MinecraftClient.getInstance().getToastManager().add(new NowPlayingToast(disc.getDescription(), new ItemStack(disc)));

        }

    }
}
