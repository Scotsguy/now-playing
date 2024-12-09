/*
 * Copyright (c) 2022-2025 AppleTheGolden
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    
    @Shadow
    private @Final Level level;

    @WrapOperation(
            method = "playJukeboxSong",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"
            )
    )
    private void display(Gui instance, Component text, Operation<Void> original,
                         @Local JukeboxSong song, @Local SoundEvent sound) {
        NowPlaying.display(song.description(), now_playing$getDisc(sound), Config.options().jukeboxStyle);
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
