package de.codecrafter47.data.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public class VelocitySessionDurationProvider implements Function<Player, Duration> {
    
    private static VelocitySessionDurationProvider instance = null;
    
    public static VelocitySessionDurationProvider getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new VelocitySessionDurationProvider(plugin);
        }
        return instance;
    }
    
    private final Map<Player, LocalDateTime> timeJoined = Collections.synchronizedMap(new IdentityHashMap<>());
    
    private VelocitySessionDurationProvider(Plugin plugin) {
        // TODO get this stuff ready!
    }
    
    public Duration apply(Player player) {
        return null;
    }
}
