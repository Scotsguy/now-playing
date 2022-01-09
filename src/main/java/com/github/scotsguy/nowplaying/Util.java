package com.github.scotsguy.nowplaying;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;
import com.github.scotsguy.nowplaying.mixin.RecordItemAccessor;

public class Util {
    public static Component getSoundName(SoundInstance instance) {
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
