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

package de.codecrafter47.data.bungee;

import de.codecrafter47.data.bungee.api.BungeeData;
import de.codecrafter47.data.bungee.bungeeonlinetime.BungeeOnlineTimeOnlineTimeProvider30;
import de.codecrafter47.data.bungee.bungeeonlinetime.BungeeOnlineTimeOnlineTimeProvider31;
import de.codecrafter47.data.bungee.bungeeonlinetime.BungeeOnlineTimeOnlineTimeProvider43;
import de.codecrafter47.data.bungee.bungeeperms2.*;
import de.codecrafter47.data.bungee.bungeeperms3.*;
import de.codecrafter47.data.bungee.luckperms.LuckPermsPrefixProvider;
import de.codecrafter47.data.bungee.luckperms.LuckPermsPrimaryGroupProvider;
import de.codecrafter47.data.bungee.luckperms.LuckPermsSuffixProvider;
import de.codecrafter47.data.bungee.luckperms.LuckPermsWeightProvider;
import de.codecrafter47.data.bungee.protocolsupportbungee.ProtocolSupportBungeeClientVersionProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.logging.Logger;

public class PlayerDataAccess extends AbstractBungeeDataAccess<ProxiedPlayer> {
    public PlayerDataAccess(Plugin plugin, Logger logger) {
        super(plugin, logger);

        addProvider(BungeeData.BungeeCord_DisplayName, ProxiedPlayer::getDisplayName);
        addProvider(BungeeData.BungeeCord_Ping, ProxiedPlayer::getPing);
        addProvider(BungeeData.BungeeCord_PrimaryGroup, player -> {
            if (player != null) {
                Collection<String> groups = player.getGroups();
                if (groups.size() == 1) {
                    return groups.iterator().next();
                }
                for (String group : groups) {
                    if (!group.equals("default")) {
                        return group;
                    }
                }
            }
            return "default";
        });
        addProvider(BungeeData.BungeeCord_Rank, player -> {
            int rank = 0;
            for (String group : player.getGroups()) {
                if (!group.equals("default")) {
                    rank += 1;
                }
                if (group.equals("admin")) {
                    rank += 2;
                }
            }
            return Integer.MAX_VALUE - rank;
        });
        addProvider(BungeeData.BungeeCord_SessionDuration, BungeeCordSessionDurationProvider.getInstance(plugin));
        addProvider(BungeeData.BungeeCord_Server, player -> {
            Server server = player.getServer();
            return server != null ? server.getInfo().getName() : null;
        });

        Plugin p = ProxyServer.getInstance().getPluginManager().getPlugin("BungeePerms");
        if (p != null && !isClassPresent("net.alpenblock.bungeeperms.platform.bungee.BungeePlugin")) {
            // BungeePerms 2.x
            addProvider(BungeeData.BungeePerms_PrimaryGroup, new BungeePerms2MainGroupProvider());
            addProvider(BungeeData.BungeePerms_Prefix, new BungeePerms2PrefixProvider());
            addProvider(BungeeData.BungeePerms_DisplayPrefix, new BungeePerms2DisplayProvider());
            addProvider(BungeeData.BungeePerms_Suffix, new BungeePerms2SuffixProvider());
            addProvider(BungeeData.BungeePerms_Rank, new BungeePerms2RankProvider());
            addProvider(BungeeData.BungeePerms_PrimaryGroupPrefix, new BungeePermsPrefixProvider());
        } else if (p != null && isClassPresent("net.alpenblock.bungeeperms.platform.bungee.BungeePlugin")) {
            // BungeePerms 3.x
            addProvider(BungeeData.BungeePerms_PrimaryGroup, new BungeePermsMainGroupProvider());
            addProvider(BungeeData.BungeePerms_Prefix, new BungeePermsPrefixProvider());
            addProvider(BungeeData.BungeePerms_DisplayPrefix, new BungeePermsDisplayProvider());
            addProvider(BungeeData.BungeePerms_Suffix, new BungeePermsSuffixProvider());
            addProvider(BungeeData.BungeePerms_Rank, new BungeePermsRankProvider());
            addProvider(BungeeData.BungeePerms_PrimaryGroupPrefix, new BungeePermsGroupPrefixProvider());
            addProvider(BungeeData.BungeePerms_PlayerPrefix, new BungeePermsUserPrefixProvider());
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("LuckPerms");
        if (p != null) {
            addProvider(BungeeData.LuckPerms_PrimaryGroup, new LuckPermsPrimaryGroupProvider());
            addProvider(BungeeData.LuckPerms_Prefix, new LuckPermsPrefixProvider());
            addProvider(BungeeData.LuckPerms_Suffix, new LuckPermsSuffixProvider());
            addProvider(BungeeData.LuckPerms_Weight, new LuckPermsWeightProvider());
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("BungeeOnlineTime");
        if (p != null && isClassPresent("lu.r3flexi0n.bungeeonlinetime.Core")) {
            // BungeeOnlineTime 3.0 or older
            addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider30());
        } else if (p != null && isClassPresent("bungeeonlinetime.Main")) {
            // BungeeOnlineTime 3.1+
            addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider31());
        } else if (p != null && isClassPresent("lu.r3flexi0n.bungeeonlinetime.BungeeOnlineTime")) {
            // BungeeOnlineTime 4.3+
            addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider43());
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("ProtocolSupportBungee");
        if (p != null) {
            addProvider(BungeeData.ClientVersion, new ProtocolSupportBungeeClientVersionProvider());
        } else {
            addProvider(BungeeData.ClientVersion, new BungeeClientVersionProvider());
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
}
