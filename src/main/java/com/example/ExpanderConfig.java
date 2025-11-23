package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("mapexpander")
public interface ExpanderConfig extends Config
{
    @Range(
            min = 1,
            max = 200
    )
    @ConfigItem(
            keyName = "zoomLevel",
            name = "Zoom Level",
            description = "Target zoom value. Try values like 50, 100, or 800 to find the scale.",
            position = 1
    )
    default int zoomLevel()
    {
        return 64;
    }

    @ConfigItem(
            keyName = "debugZoom",
            name = "Debug Zoom",
            description = "Prints the current World Map zoom value to chat.",
            position = 2
    )
    default boolean debugZoom()
    {
        return true;
    }
}