/*
 * Copyright (C) 2016 Florian Stober
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.codecrafter47.data.bungee.bungeeperms3;

import net.alpenblock.bungeeperms.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;

public class BungeePermsGroupPrefixProvider implements Function<ProxiedPlayer, String> {
    @Override
    public String apply(ProxiedPlayer player) {
        BungeePerms bp = BungeePerms.getInstance();
        PermissionsManager pm = bp.getPermissionsManager();
        if (pm != null) {
            User user = pm.getUser(player.getName());
            if (user != null) {
                Group mainGroup = pm.getMainGroup(user);
                if (mainGroup != null) {
                    net.md_5.bungee.api.connection.Server serverConnection = player.getServer();
                    if (serverConnection != null) {
                        String serverName = serverConnection.getInfo().getName();
                        Server server = mainGroup.getServer(serverName);
                        if (server != null && server.getPrefix() != null) {
                            return server.getPrefix();
                        }
                    }
                    return mainGroup.getPrefix();
                }
            }
        }
        return null;
    }
}
