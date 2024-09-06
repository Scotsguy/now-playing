package com.github.scotsguy.nowplaying.sound;

import com.github.scotsguy.nowplaying.config.Config;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class Sound {
    public static Component getSoundName(SoundInstance instance) {
        String soundLocation = instance.getSound().getLocation().toString();
        String[] splitSoundLocation = soundLocation.split("/");
        String soundName = splitSoundLocation[splitSoundLocation.length - 1];
        return localized("music", soundName);
    }

    public static Item getMusicDisc(String music) {
        if (Config.get().options.onlyCat) return Items.MUSIC_DISC_CAT;
        if (music.startsWith("C418")) {
            return Items.MUSIC_DISC_BLOCKS;
        } else if (music.startsWith("Lena Raine")) {
            return Items.MUSIC_DISC_OTHERSIDE;
        } else if (music.startsWith("Aaron Cherof")) {
            return Items.MUSIC_DISC_RELIC;
        } else if (music.startsWith("Kumi Tanioka")) {
            return Items.MUSIC_DISC_MALL;
        } else {
            return Items.MUSIC_DISC_CAT;
        }
    }
}
