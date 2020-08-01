package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.mixin.MusicDiscItemAccessor;

import net.minecraft.client.audio.ISound;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class Util {
    public static ITextComponent getSoundName(ISound instance) {
        if (instance.getSound().getIdentifier().toString().startsWith("minecraft:music/")) {
            String id = "now_playing.sound." + instance.getSound().getIdentifier().toString();
            return new TranslationTextComponent(id);
        }
        return null;
    }

    public static MusicDiscItem getDiscFromSound(ISound instance) {
        //SoundEvent event = new SoundEvent(new Identifier(instance.getId().toString().replace('.', '_')));
        for (SoundEvent event : MusicDiscItemAccessor.getDiscs().keySet()) {
            if (event.getId().equals(instance.getId())) {
                return MusicDiscItem.bySound(event);
            }
        }
        return null;
    }
}
