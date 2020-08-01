package com.github.scotsguy.nowplaying.config;

import com.github.scotsguy.nowplaying.NowPlaying;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

import static net.minecraftforge.fml.Logging.CORE;

@Mod.EventBusSubscriber
public class Config {
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        NowPlayingConfig.init(CLIENT_BUILDER);
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        NowPlaying.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        NowPlaying.LOGGER.debug("Built TOML config for {}", path.toString());
        configData.load();
        NowPlaying.LOGGER.debug("Loaded TOML config file {}", path.toString());
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(ModConfig.Loading configEvent) {
        NowPlaying.LOGGER.debug("Loaded {} config file {}", NowPlaying.MODID, configEvent.getConfig().getFileName());

    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.Reloading configEvent) {
        NowPlaying.LOGGER.fatal(CORE, "{} config just got changed on the file system!", NowPlaying.MODID);
    }

    public enum Style {
        Hotbar,
        Toast,
        Disabled
    }
}
