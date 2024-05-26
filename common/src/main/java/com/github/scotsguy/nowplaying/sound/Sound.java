package com.github.scotsguy.nowplaying.sound;

import com.github.scotsguy.nowplaying.mixin.RecordItemAccessor;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class Sound {
    public static Component getSoundName(SoundInstance instance) {
        String soundLocation = instance.getSound().getLocation().toString();
        String[] splitSoundLocation = soundLocation.split("/");
        String soundName = splitSoundLocation[splitSoundLocation.length - 1];
        return localized("music", soundName);
    }
    public static RecordItem getDiscFromSound(SoundInstance instance) {
        for (SoundEvent event : RecordItemAccessor.getByName().keySet()) {
            if (event.getLocation().equals(instance.getLocation())) {
                return RecordItem.getBySound(event);
            }
        }
        return null;
    }
}
