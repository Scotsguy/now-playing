package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.github.scotsguy.nowplaying.config.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Shadow
    @Nullable
    private ClientLevel level;

    @WrapOperation(
            method = "playJukeboxSong",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"
            )
    )
    private void display(Gui instance, Component text, Operation<Void> original,
                         @Local JukeboxSong song, @Local SoundEvent sound) {
        NowPlaying.display(song.description(), now_playing$getDisc(sound), Config.get().options.jukeboxStyle);
    }

    @Unique
    private Item now_playing$getDisc(SoundEvent sound) {
        Item defaultDisc = Items.MUSIC_DISC_CAT;
        if (level == null) return defaultDisc;

        Item disc = null;
        Optional<Registry<Item>> itemRegistry = level.registryAccess().registry(Registries.ITEM);
        if (itemRegistry.isPresent()) disc = itemRegistry.get().get(ResourceLocation.parse(
                sound.getLocation().toString().replaceAll("\\.", "_")));

        if (disc == null || disc.equals(Items.AIR)) disc = defaultDisc;
        return disc;
    }
}
