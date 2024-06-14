package com.github.scotsguy.nowplaying.sound;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class Sound {
    public static Component getSoundName(SoundInstance instance) {
        String soundLocation = instance.getSound().getLocation().toString();
        String[] splitSoundLocation = soundLocation.split("/");
        String soundName = splitSoundLocation[splitSoundLocation.length - 1];
        return localized("music", soundName);
    }
}
