package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.github.scotsguy.nowplaying.config.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(LevelEventHandler.class)
public class MixinLevelEventHandler {

    @Final
    @Shadow
    private Level level;

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
        Optional<Holder.Reference<Registry<Item>>> itemRegistry =
                level.registryAccess().get(Registries.ITEM);
        if (itemRegistry.isPresent()) {
            Optional<Holder.Reference<Item>> discRef = itemRegistry.get().value().get(
                    ResourceLocation.parse(sound.location().toString().replaceAll("\\.", "_")));
            if (discRef.isPresent()) disc = discRef.get().value();
        }

        if (disc == null || disc.equals(Items.AIR)) disc = defaultDisc;
        return disc;
    }
}
