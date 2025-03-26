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

package com.github.scotsguy.nowplaying.util;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class SpriteProvider {
    private static final ResourceLocation SPRITES_FILE = 
            ResourceLocation.fromNamespaceAndPath(NowPlaying.MOD_ID, "sprites.json");
    public static final ResourceLocation DISC_SPRITE_DEFAULT =
            ResourceLocation.parse("minecraft:textures/item/music_disc_cat.png");
    
    private static final HashMap<String, ResourceLocation> CACHE = new HashMap<>();
    private static boolean hasAttemptedLoad;
    
    public static void onResourceReload() {
        CACHE.clear();
        hasAttemptedLoad = false;
    }
    
    private static @Nullable ResourceLocation getCustomSprite(String song) {
        if (CACHE.isEmpty() && !hasAttemptedLoad) {
            loadCache();
        }
        return CACHE.get(song);
    }
    
    private static void loadCache() {
        hasAttemptedLoad = true;
//        NowPlaying.LOG.warn("Loading sprite cache");
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        try {
            List<Resource> resourceStack = manager.getResourceStack(SPRITES_FILE);
            for (Resource resource : resourceStack) {
                try (var reader = new InputStreamReader(resource.open())) {
                    JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
                    for (var entry : obj.entrySet()) {
                        String spriteStr = entry.getValue().getAsString();
//                        NowPlaying.LOG.info("Added {} -> {}", entry.getKey(), spriteStr);
                        ResourceLocation sprite = ResourceLocation.tryParse(spriteStr + ".png");
                        if (sprite == null) {
                            NowPlaying.LOG.error("Unable to parse sprite location '{}'", spriteStr);
                        } else {
                            CACHE.put(entry.getKey(), sprite);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            // Catch exception as loading/parsing errors may not fall under
            // IOException or JsonParseException, but should not crash the game.
            NowPlaying.LOG.error("Unable to load sprite cache", e);
        }
    }

    public static ResourceLocation getMusicSprite(ResourceLocation location, String title) {
        String locStr = location.toString();
        ResourceLocation sprite = getCustomSprite(locStr);
        
        if (sprite == null) {
//            NowPlaying.LOG.warn("getCustomSprite failed for '{}'", locStr);
            String namespace = location.getNamespace();
            String path = location.getPath();
            String[] splitPath = path.split("/");
            
            for (int i = splitPath.length -1; i > 0; i--) {
                path = path.substring(0, path.length() - (splitPath[i].length() + 1));
//                NowPlaying.LOG.warn("Trying reduced path '{}'", namespace + ":" + path);
                sprite = getCustomSprite(namespace + ":" + path);
                if (sprite != null) break;
            }
        }
        
        if (sprite == null) {
//            NowPlaying.LOG.warn("Unable to find any sprite for '{}'", locStr);
            return getDefaultSprite(title);
        } else {
            return sprite;
        }
    }

    public static ResourceLocation getDefaultSprite(String title) {
        if (title.contains("C418")) {
            return ResourceLocation.withDefaultNamespace("textures/item/music_disc_blocks.png");
        } else if (title.contains("Lena Raine")) {
            return ResourceLocation.withDefaultNamespace("textures/item/music_disc_otherside.png");
        } else if (title.contains("Aaron Cherof")) {
            return ResourceLocation.withDefaultNamespace("textures/item/music_disc_relic.png");
        } else if (title.contains("Kumi Tanioka")) {
            return ResourceLocation.withDefaultNamespace("textures/item/music_disc_mall.png");
        } else {
            return DISC_SPRITE_DEFAULT;
        }
    }
    
    public static ResourceLocation getDiscSprite(ResourceLocation location) {
        String discId = location.getPath().replaceAll("\\.", "_");
        return ResourceLocation.parse("textures/item/" + discId + ".png");
    }
}
