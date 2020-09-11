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

package de.codecrafter47.data.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public class BungeeCordSessionDurationProvider implements Function<ProxiedPlayer, Duration>, Listener {

    private static BungeeCordSessionDurationProvider instance = null;

    public static BungeeCordSessionDurationProvider getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new BungeeCordSessionDurationProvider(plugin);
        }
        return instance;
    }

    private final Map<ProxiedPlayer, LocalDateTime> timeJoined = Collections.synchronizedMap(new IdentityHashMap<>());

    private BungeeCordSessionDurationProvider(Plugin plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @Override
    public Duration apply(ProxiedPlayer player) {
        LocalDateTime joined = timeJoined.get(player);
        if (joined != null) {
            return Duration.between(joined, LocalDateTime.now());
        }
        return null;
    }

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        timeJoined.put(event.getPlayer(), LocalDateTime.now());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        timeJoined.remove(event.getPlayer());
    }
}
