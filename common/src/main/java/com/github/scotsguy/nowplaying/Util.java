package com.github.scotsguy.nowplaying;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;
import com.github.scotsguy.nowplaying.mixin.RecordItemAccessor;

public class Util {
    public static Component getSoundName(SoundInstance instance) {
        String soundLocation = instance.getSound().getLocation().toString();
        if (soundLocation.startsWith("minecraft:music/") || I18n.exists("now_playing.sound." + soundLocation)) {
            return Component.translatable("now_playing.sound." + soundLocation);
        }
        return null;
    }
    public static RecordItem getDiscFromSound(SoundInstance instance) {
        for (SoundEvent event : RecordItemAccessor.getDiscs().keySet()) {
            if (event.getLocation().equals(instance.getLocation())) {
                return RecordItem.getBySound(event);
            }
        }
        return null;
    }
}
