package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.command.Commands;
import com.github.scotsguy.nowplaying.gui.screen.ConfigScreenProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(NowPlaying.MOD_ID_NEOFORGE)
@EventBusSubscriber(modid = NowPlaying.MOD_ID_NEOFORGE, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NowPlayingNeoForge {
    public NowPlayingNeoForge() {
        // Config screen
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (mc, parent) -> ConfigScreenProvider.getConfigScreen(parent));

        // Main initialization
        NowPlaying.init();
    }

    // Keybindings
    @SubscribeEvent
    static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(NowPlaying.DISPLAY_KEY);
    }

    @EventBusSubscriber(modid = NowPlaying.MOD_ID_NEOFORGE, value = Dist.CLIENT)
    static class ClientEventHandler {
        // Commands
        @SubscribeEvent
        static void registerClientCommands(RegisterClientCommandsEvent event) {
            new Commands<CommandSourceStack>().register(Minecraft.getInstance(), event.getDispatcher(), event.getBuildContext());
        }

        // Tick events
        @SubscribeEvent
        public static void clientTickEvent(ClientTickEvent.Post event) {
            NowPlaying.onEndTick(Minecraft.getInstance());
        }
    }
}
