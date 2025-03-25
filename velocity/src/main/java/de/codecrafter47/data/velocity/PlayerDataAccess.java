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

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import de.codecrafter47.data.velocity.api.VelocityData;
import de.codecrafter47.data.velocity.luckperms.LuckPermsPrefixProvider;
import de.codecrafter47.data.velocity.luckperms.LuckPermsPrimaryGroupProvider;
import de.codecrafter47.data.velocity.luckperms.LuckPermsSuffixProvider;
import de.codecrafter47.data.velocity.luckperms.LuckPermsWeightProvider;
import de.codecrafter47.data.velocity.luckperms5.LuckPerms5PrefixProvider;
import de.codecrafter47.data.velocity.luckperms5.LuckPerms5PrimaryGroupProvider;
import de.codecrafter47.data.velocity.luckperms5.LuckPerms5SuffixProvider;
import de.codecrafter47.data.velocity.luckperms5.LuckPerms5WeightProvider;

import java.util.logging.Logger;

public class PlayerDataAccess extends AbstractVelocityDataAccess<Player> {
    public PlayerDataAccess(ProxyServer server, Object plugin, Logger logger) {
        super(server, plugin, logger);

        addProvider(VelocityData.Velocity_DisplayName, Player::getUsername);
        addProvider(VelocityData.Velocity_Ping, player -> (int) (player.getPing() < 0 ? 0 : player.getPing()));
        addProvider(VelocityData.Velocity_SessionDuration, VelocitySessionDurationProvider.getInstance(server, plugin));
        addProvider(VelocityData.Velocity_Server, player -> {
            ServerConnection serverConnection = player.getCurrentServer().orElse(null);
            return serverConnection != null ? serverConnection.getServerInfo().getName() : null;
        });
        addProvider(VelocityData.Velocity_Permission, (player, key) -> {
            try {
                return player.hasPermission(key.getParameter());
            } catch (Throwable ignored) {
                return null;
            }
        });
        addProvider(VelocityData.Velocity_Locale, (player, key) -> player.getEffectiveLocale() != null ? player.getEffectiveLocale().toString() : null);

        if (server.getPluginManager().getPlugin("luckperms").isPresent()) {
            if (isClassPresent("net.luckperms.api.LuckPerms")) {
                // API v5.0
                addProvider(VelocityData.LuckPerms_PrimaryGroup, new LuckPerms5PrimaryGroupProvider());
                addProvider(VelocityData.LuckPerms_Prefix, new LuckPerms5PrefixProvider());
                addProvider(VelocityData.LuckPerms_Suffix, new LuckPerms5SuffixProvider());
                addProvider(VelocityData.LuckPerms_Weight, new LuckPerms5WeightProvider());
            } else {
                // API v4.0
                addProvider(VelocityData.LuckPerms_PrimaryGroup, new LuckPermsPrimaryGroupProvider());
                addProvider(VelocityData.LuckPerms_Prefix, new LuckPermsPrefixProvider());
                addProvider(VelocityData.LuckPerms_Suffix, new LuckPermsSuffixProvider());
                addProvider(VelocityData.LuckPerms_Weight, new LuckPermsWeightProvider());
            }
        }
    }

    private static boolean isClassPresent(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    private static boolean isMethodPresent(String className, String methodName, Class<?>... parameters) {
        try {
            Class.forName(className).getMethod(methodName, parameters);
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {
            return false;
        }
    }
}
