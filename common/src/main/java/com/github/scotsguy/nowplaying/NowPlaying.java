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

package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import com.github.scotsguy.nowplaying.mixin.accessor.GuiAccessor;
import com.github.scotsguy.nowplaying.mixin.accessor.MinecraftAccessor;
import com.github.scotsguy.nowplaying.util.Localization;
import com.github.scotsguy.nowplaying.util.ModLogger;
import com.github.scotsguy.nowplaying.util.SpriteProvider;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

import static com.github.scotsguy.nowplaying.config.Config.options;
import static com.github.scotsguy.nowplaying.util.Localization.localized;
import static com.github.scotsguy.nowplaying.util.Localization.translationKey;

public class NowPlaying {
    public static final String MOD_ID = "now-playing";
    public static final String MOD_ID_NEOFORGE = "now_playing";
    public static final String MOD_NAME = "Now Playing";
    public static final ModLogger LOG = new ModLogger(MOD_NAME);
    public static final KeyMapping DISPLAY_KEY = new KeyMapping(
            translationKey("key", "group.display"), InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(), translationKey("key", "group"));
    public static final KeyMapping NEXT_KEY = new KeyMapping(
            translationKey("key", "group.next"), InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(), translationKey("key", "group"));

    public static ResourceLocation lastMusic;

    public static void init() {
        Config.getAndSave();
    }

    public static void onEndTick(Minecraft mc) {
        while (DISPLAY_KEY.consumeClick()) {
            displayLastMusic();
        }
        while (NEXT_KEY.consumeClick()) {
            ((MinecraftAccessor)mc).getMusicManager().stopPlaying();
            ((MinecraftAccessor)mc).getMusicManager().startPlaying(mc.getSituationalMusic());
        }
    }
    
    public static void onResourceReload() {
        SpriteProvider.onResourceReload();
    }

    public static void displayLastMusic() {
        if (lastMusic != null) {
            displayMusic(lastMusic);
        } else {
            Minecraft.getInstance().gui.setOverlayMessage(
                    localized("message", "notFound").withStyle(ChatFormatting.RED), true);
        }
    }
    
    public static void displayMusic(ResourceLocation location) {
        Component title = getTranslatedTitle(location.toString());
        display(title, () -> SpriteProvider.getMusicSprite(location, title.getString()),
                options().musicStyle);
    }

    public static void displayDisc(Component text, ResourceLocation location) {
        display(text, () -> SpriteProvider.getDiscSprite(location), options().jukeboxStyle);
    }

    private static void display(Component name, Supplier<ResourceLocation> spriteSupplier, 
                               Config.Options.Style style) {
        Minecraft mc = Minecraft.getInstance();
        Component message = Component.translatable("record.nowPlaying", name);

        switch(style) {
            case Toast -> {
                mc.getToasts().addToast(new NowPlayingToast(name, spriteSupplier.get(),
                        options().toastTime * 1000L, options().toastScale, options().darkToast));
                if (options().narrate) mc.getNarrator().sayNow(message);
            }
            case Hotbar -> {
                if (isHotbarVisible(mc.screen)) {
                    mc.gui.setOverlayMessage(message, true);
                    ((GuiAccessor)mc.gui).setOverlayMessageTime(options().hotbarTime * 20);
                } else if (options().fallbackToast) {
                    mc.getToasts().addToast(new NowPlayingToast(name, spriteSupplier.get(),
                            options().toastTime * 1000L, options().toastScale, options().darkToast));
                }
                if (options().narrate) mc.getNarrator().sayNow(message);
            }
        }
    }

    public static boolean isHotbarVisible(Screen screen) {
        return (screen == null || screen instanceof ChatScreen);
    }
    
    private static Component getTranslatedTitle(String location) {
        String key = Localization.translationKey(location);
        if (!I18n.exists(key)) {
            String[] splitLocation = location.split("/");
            if (splitLocation.length > 0) {
                String name = splitLocation[splitLocation.length -1];
                if (name != null && !name.isBlank()) {
                    String oldKey = Localization.translationKey("music", name);
                    if (I18n.exists(oldKey)) {
                        return Component.translatable(oldKey);
                    }
                }
            }
        }
        return Component.translatable(key);
    }
}
