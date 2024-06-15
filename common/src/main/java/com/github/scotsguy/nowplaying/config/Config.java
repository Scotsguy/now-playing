package com.github.scotsguy.nowplaying.config;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class Config {
    private static final Path DIR_PATH = Path.of("config");
    private static final String FILE_NAME = NowPlaying.MOD_ID + ".json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    // Options

    public final Options options = new Options();

    public static class Options {
        public static final Style defaultMusicStyle = Style.Toast;
        public Style musicStyle = defaultMusicStyle;

        public static final boolean defaultOnlyKeybind = false;
        public boolean onlyKeybind = defaultOnlyKeybind;

        public static final Style defaultJukeboxStyle = Style.Hotbar;
        public Style jukeboxStyle = defaultJukeboxStyle;

        public static final boolean defaultSilenceWoosh = true;
        public boolean silenceWoosh = defaultSilenceWoosh;

        public static final boolean defaultSimpleToast = false;
        public boolean simpleToast = defaultSimpleToast;

        public static final int defaultToastTime = 5;
        public int toastTime = defaultToastTime;

        public static final int defaultHotbarTime = 3;
        public int hotbarTime = defaultHotbarTime;

        public static final boolean defaultNarrate = true;
        public boolean narrate = defaultNarrate;

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
        }
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private static @Nullable Config load(Path file, Gson gson) {
        try (FileReader reader = new FileReader(file.toFile())) {
            return gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            // Catch Exception as errors in deserialization may not fall under
            // IOException or JsonParseException, but should not crash the game.
            NowPlaying.LOG.error("Unable to load config.", e);
            return null;
        }
    }

    public static void save() {
        try {
            if (!Files.isDirectory(DIR_PATH)) Files.createDirectories(DIR_PATH);
            Path file = DIR_PATH.resolve(FILE_NAME);
            Path tempFile = file.resolveSibling(file.getFileName() + ".tmp");

            try (FileWriter writer = new FileWriter(tempFile.toFile())) {
                writer.write(GSON.toJson(instance));
            } catch (IOException e) {
                throw new IOException(e);
            }
            Files.move(tempFile, file, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            NowPlaying.LOG.error("Unable to save config.", e);
        }
    }
}
