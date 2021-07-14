package com.github.scotsguy.nowplaying;

import com.mojang.datafixers.kinds.IdF;
import com.github.scotsguy.nowplaying.mixin.MusicDiscItemAccessor;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class Util {
    public static Text getSoundName(SoundInstance instance) {
        if (instance.getSound().getIdentifier().toString().startsWith("minecraft:music/")) {
            String id = "now_playing.sound." + instance.getSound().getIdentifier().toString();

            return new TranslatableText(id);
        }
        return null;
    }
    public static MusicDiscItem getDiscFromSound(SoundInstance instance) {
        //SoundEvent event = new SoundEvent(new Identifier(instance.getId().toString().replace('.', '_')));
        for (SoundEvent event : MusicDiscItemAccessor.getDiscs().keySet()) {
            if (event.getId().equals(instance.getId())) {
                return MusicDiscItem.bySound(event);
            }
        }
        return null;
    }
}
