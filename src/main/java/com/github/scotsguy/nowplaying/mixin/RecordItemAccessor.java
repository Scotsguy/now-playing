package com.github.scotsguy.nowplaying.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RecordItem.class)
public interface RecordItemAccessor {

    @Accessor("BY_NAME")
    static Map<SoundEvent, RecordItem> getDiscs() {
        throw new UnsupportedOperationException();
    }
}
