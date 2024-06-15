package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import com.github.scotsguy.nowplaying.mixin.GuiAccessor;
import com.github.scotsguy.nowplaying.util.ModLogger;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.github.scotsguy.nowplaying.util.Localization.translationKey;

public class NowPlaying {
    public static final String MOD_ID = "now-playing";
    public static final String MOD_ID_NEOFORGE = "now_playing";
    public static final String MOD_NAME = "Now Playing";
    public static final ModLogger LOG = new ModLogger(MOD_NAME);
    public static final KeyMapping DISPLAY_KEY = new KeyMapping(
            translationKey("key", "display"), InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(), translationKey("key_group"));

    public static Component lastMusic;

    public static void init() {
        Config.getAndSave();
    }

    public static void onEndTick(Minecraft mc) {
        while (DISPLAY_KEY.consumeClick()) {
            display();
        }
    }

    public static void display() {
        if (lastMusic != null) display(lastMusic);
    }

    public static void display(Component name) {
        display(name, Items.MUSIC_DISC_CAT);
    }

    public static void display(Component name, Item disc) {
        Component message = Component.translatable("record.nowPlaying", name);

        Minecraft mc = Minecraft.getInstance();
        Config config = Config.get();

        switch(config.options.musicStyle) {
            case Toast -> mc.getToasts().addToast(new NowPlayingToast(name, new ItemStack(disc)));
            case Hotbar -> {
                // Use toast if hotbar display would not be visible, and fallback is enabled
                if (mc.screen != null && !(mc.screen instanceof ChatScreen) && config.options.fallbackToast) {
                    mc.getToasts().addToast(new NowPlayingToast(name, new ItemStack(disc)));
                } else {
                    mc.gui.setOverlayMessage(message, true);
                    ((GuiAccessor)mc.gui).setOverlayMessageTime(config.options.hotbarTime * 20);
                }
            }
        }

        if (config.options.narrate) {
            mc.getNarrator().sayNow(message);
        }
    }
}
