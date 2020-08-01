package com.github.scotsguy.nowplaying.mixin;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MusicDiscItem.class)
public interface MusicDiscItemAccessor {

    @Accessor("MUSIC_DISCS")
    static Map<SoundEvent, MusicDiscItem> getDiscs() {
        throw new UnsupportedOperationException();
    }
}
