/*
 *     Copyright (C) 2020 Florian Stober
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.codecrafter47.data.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public class VelocitySessionDurationProvider implements Function<Player, Duration> {

    private static VelocitySessionDurationProvider instance = null;

    public static VelocitySessionDurationProvider getInstance(ProxyServer server, Object plugin) {
        if (instance == null) {
            instance = new VelocitySessionDurationProvider(server, plugin);
        }
        return instance;
    }

    private final Map<Player, LocalDateTime> timeJoined = Collections.synchronizedMap(new IdentityHashMap<>());

    private VelocitySessionDurationProvider(ProxyServer server, Object plugin) {
        server.getEventManager().register(plugin, this);
    }

    @Override
    public Duration apply(Player player) {
        LocalDateTime joined = timeJoined.get(player);
        if (joined != null) {
            return Duration.between(joined, LocalDateTime.now());
        }
        return null;
    }

    @Subscribe
    public void onJoin(PostLoginEvent event) {
        timeJoined.put(event.getPlayer(), LocalDateTime.now());
    }

    @Subscribe
    public void onLeave(DisconnectEvent event) {
        timeJoined.remove(event.getPlayer());
    }
}