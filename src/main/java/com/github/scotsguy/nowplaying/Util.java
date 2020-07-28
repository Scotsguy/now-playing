package com.github.scotsguy.nowplaying;

import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Util {
    public static Text getSoundName(WeightedSoundSet soundSet) {
        if (soundSet.getSound().getIdentifier().toString().startsWith("minecraft:music/")) {
            String id = "now_playing.sound." + soundSet.getSound().getIdentifier().toString();

            return new TranslatableText(id);
        }
        return null;
    }
}
