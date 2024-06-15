package com.github.scotsguy.nowplaying;

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
@EventBusSubscriber(modid = NowPlaying.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NowPlayingNeoForge {
    public NowPlayingNeoForge() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (mc, parent) -> ConfigScreenProvider.getConfigScreen(parent));

        NowPlaying.init();
    }

    @SubscribeEvent
    static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(NowPlaying.DISPLAY_KEY);
    }

    @EventBusSubscriber(modid = NowPlaying.MOD_ID, value = Dist.CLIENT)
    static class ClientEventHandler {
        @SubscribeEvent
        public static void clientTickEvent(ClientTickEvent.Post event) {
            NowPlaying.onEndTick(Minecraft.getInstance());
        }
    }
}
