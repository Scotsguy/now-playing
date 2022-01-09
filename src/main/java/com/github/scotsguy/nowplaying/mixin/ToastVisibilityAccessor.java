package com.github.scotsguy.nowplaying.mixin;

import net.minecraft.client.toast.Toast;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Toast.Visibility.class)
public interface ToastVisibilityAccessor {
    @Accessor
    SoundEvent getSound();
}
