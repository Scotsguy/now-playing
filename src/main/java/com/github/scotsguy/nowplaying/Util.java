package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.mixin.RecordItemAccessor;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;

public class Util {
    public static TranslatableComponent getSoundName(SoundInstance instance) {
        if (instance.getSound().getLocation().toString().startsWith("minecraft:music/")) {
            String id = "now_playing.sound." + instance.getSound().getLocation().toString();
            return new TranslatableComponent(id);
        }
        return null;
    }

    public static RecordItem getDiscFromSound(SoundInstance instance) {
        //SoundEvent event = new SoundEvent(new Identifier(instance.getId().toString().replace('.', '_')));
        for (SoundEvent event : RecordItemAccessor.getDiscs().keySet()) {
            if (event.getLocation().equals(instance.getLocation())) {
                return RecordItem.getBySound(event);
            }
        }
        return null;
    }
}
