/*
 * Copyright (c) 2022-2026 AppleTheGolden
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

package com.github.scotsguy.nowplaying.config;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class Config {
    private static final Path DIR_PATH = Path.of("config");
    private static final String FILE_NAME = NowPlaying.MOD_ID + ".json";
    private static final String BACKUP_FILE_NAME = NowPlaying.MOD_ID + ".unreadable.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Options

    public final Options options = new Options();

    public static Options options() {
        return Config.get().options;
    }

    public static class Options {
        public static final boolean onlyKeybindDefault = false;
        public boolean onlyKeybind = onlyKeybindDefault;

        public static final Style musicStyleDefault = Style.Toast;
        public Style musicStyle = musicStyleDefault;

        public static final Style jukeboxStyleDefault = Style.Hotbar;
        public Style jukeboxStyle = jukeboxStyleDefault;

        public static final boolean fallbackToastDefault = true;
        public boolean fallbackToast = fallbackToastDefault;

        public static final boolean silenceWooshDefault = true;
        public boolean silenceWoosh = silenceWooshDefault;

        public static final float toastScaleDefault = 1.0F;
        public float toastScale = toastScaleDefault;

        public static final boolean simpleToastDefault = false;
        public boolean simpleToast = simpleToastDefault;

        public static final boolean darkToastDefault = false;
        public boolean darkToast = darkToastDefault;

        public static final int toastTimeDefault = 5;
        public int toastTime = toastTimeDefault;

        public static final int hotbarTimeDefault = 3;
        public int hotbarTime = hotbarTimeDefault;

        public static final boolean narrateDefault = true;
        public boolean narrate = narrateDefault;

        public enum Style {
            Toast,
            Hotbar,
            Disabled;

            public static Component name(Enum<Style> style) {
                return switch(style.name()) {
                    case "Toast" -> localized("option", "style.toast")
                            .withStyle(ChatFormatting.GREEN);
                    case "Hotbar" -> localized("option", "style.hotbar")
                            .withStyle(ChatFormatting.AQUA);
                    default -> localized("option", "style.disabled")
                            .withStyle(ChatFormatting.RED);
                };
            }
        }
    }

    // Instance management

    private static Config instance = null;

    public static Config get() {
        if (instance == null) {
            instance = Config.load();
        }
        return instance;
    }

    public static Config getAndSave() {
        get();
        save();
        return instance;
    }

    public static Config resetAndSave() {
        instance = new Config();
        save();
        return instance;
    }

    // Load and save

    public static @NotNull Config load() {
        Path file = DIR_PATH.resolve(FILE_NAME);
        Config config = null;
        if (Files.exists(file)) {
            config = load(file, GSON);
            if (config == null) {
                backup();
                NowPlaying.LOG.warn("Resetting config");
            }
        }
        return config != null ? config : new Config();
    }

    private static @Nullable Config load(Path file, Gson gson) {
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file.toFile()), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            // Catch Exception as errors in deserialization may not fall under
            // IOException or JsonParseException, but should not crash the game.
            NowPlaying.LOG.error("Unable to load config", e);
            return null;
        }
    }

    private static void backup() {
        try {
            NowPlaying.LOG.warn("Copying {} to {}", FILE_NAME, BACKUP_FILE_NAME);
            if (!Files.isDirectory(DIR_PATH)) Files.createDirectories(DIR_PATH);
            Path file = DIR_PATH.resolve(FILE_NAME);
            Path backupFile = file.resolveSibling(BACKUP_FILE_NAME);
            Files.move(file, backupFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            NowPlaying.LOG.error("Unable to copy config file", e);
        }
    }

    public static void save() {
        if (instance == null) return;
        try {
            if (!Files.isDirectory(DIR_PATH)) Files.createDirectories(DIR_PATH);
            Path file = DIR_PATH.resolve(FILE_NAME);
            Path tempFile = file.resolveSibling(file.getFileName() + ".tmp");
            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(tempFile.toFile()), StandardCharsets.UTF_8)) {
                writer.write(GSON.toJson(instance));
            } catch (IOException e) {
                throw new IOException(e);
            }
            Files.move(tempFile, file, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            NowPlaying.LOG.error("Unable to save config", e);
        }
    }
}
