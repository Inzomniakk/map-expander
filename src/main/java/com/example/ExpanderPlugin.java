package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "Map Expander",
        description = "Enables adjustable zoom functionality for the World Map.",
        tags = {"world", "map", "zoom", "expander"}
)
public class ExpanderPlugin extends Plugin
{
    private static final int WORLD_MAP_ZOOM_SCRIPT = 1759;

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ExpanderConfig config;

    private float lastReportedZoom = -1;
    private int tickCounter = 0;

    @Provides
    ExpanderConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ExpanderConfig.class);
    }

    @Subscribe
    public void onClientTick(ClientTick event)
    {
        if (client == null || client.getWorldMap() == null)
        {
            return;
        }

        if (client.getWidget(WidgetInfo.WORLD_MAP_VIEW) == null)
        {
            return;
        }

        if (config.debugZoom())
        {
            float currentZoom = client.getWorldMap().getWorldMapZoom();
            if (Math.abs(currentZoom - lastReportedZoom) > 0.01f && tickCounter++ % 10 == 0)
            {
                lastReportedZoom = currentZoom;
                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
                        "Map Zoom: " + currentZoom + " | Target Config: " + config.zoomLevel(), null);
            }
        }
        
        try
        {
            client.runScript(WORLD_MAP_ZOOM_SCRIPT, config.zoomLevel());
        }
        catch (Exception e)
        {
            
        }
    }
}