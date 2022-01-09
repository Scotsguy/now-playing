package com.github.scotsguy.nowplaying.mixin;

import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Toast.Visibility.class)
public interface ToastVisibilityAccessor {
    @Accessor
    SoundEvent getSoundEvent();
}
