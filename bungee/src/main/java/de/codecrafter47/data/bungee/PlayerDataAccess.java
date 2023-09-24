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

import de.codecrafter47.data.bungee.api.BungeeData;
import de.codecrafter47.data.bungee.bungeeonlinetime.*;
import de.codecrafter47.data.bungee.bungeeperms2.*;
import de.codecrafter47.data.bungee.bungeeperms3.*;
import de.codecrafter47.data.bungee.clans.*;
import de.codecrafter47.data.bungee.luckperms.LuckPermsPrefixProvider;
import de.codecrafter47.data.bungee.luckperms.LuckPermsPrimaryGroupProvider;
import de.codecrafter47.data.bungee.luckperms.LuckPermsSuffixProvider;
import de.codecrafter47.data.bungee.luckperms.LuckPermsWeightProvider;
import de.codecrafter47.data.bungee.luckperms5.LuckPerms5PrefixProvider;
import de.codecrafter47.data.bungee.luckperms5.LuckPerms5PrimaryGroupProvider;
import de.codecrafter47.data.bungee.luckperms5.LuckPerms5SuffixProvider;
import de.codecrafter47.data.bungee.luckperms5.LuckPerms5WeightProvider;
import de.codecrafter47.data.bungee.premiumvanish.PremiumVanishIsVanishedProvider;
import de.codecrafter47.data.bungee.protocolsupportbungee.ProtocolSupportBungeeClientVersionProvider;
import de.codecrafter47.data.bungee.proxysuite.ProxySuiteIsVanishedProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.UUID;
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
        addProvider(BungeeData.BungeeCord_Permission, (player, key) -> {
            try {
                return player.hasPermission(key.getParameter());
            } catch (Throwable ignored) {
                return null;
            }
        });

        Plugin p = ProxyServer.getInstance().getPluginManager().getPlugin("BungeePerms");
        if (p != null && !isClassPresent("net.alpenblock.bungeeperms.platform.bungee.BungeePlugin")) {
            // BungeePerms 2.x
            addProvider(BungeeData.BungeePerms_PrimaryGroup, new BungeePerms2MainGroupProvider());
            addProvider(BungeeData.BungeePerms_Prefix, new BungeePerms2PrefixProvider());
            addProvider(BungeeData.BungeePerms_DisplayPrefix, new BungeePerms2DisplayProvider());
            addProvider(BungeeData.BungeePerms_Suffix, new BungeePerms2SuffixProvider());
            addProvider(BungeeData.BungeePerms_Rank, new BungeePerms2RankProvider());
            addProvider(BungeeData.BungeePerms_Weight, new BungeePerms2WeightProvider());
            addProvider(BungeeData.BungeePerms_PrimaryGroupPrefix, new BungeePermsPrefixProvider());
        } else if (p != null && isClassPresent("net.alpenblock.bungeeperms.platform.bungee.BungeePlugin")) {
            // BungeePerms 3.x
            addProvider(BungeeData.BungeePerms_PrimaryGroup, new BungeePermsMainGroupProvider());
            addProvider(BungeeData.BungeePerms_Prefix, new BungeePermsPrefixProvider());
            addProvider(BungeeData.BungeePerms_DisplayPrefix, new BungeePermsDisplayProvider());
            addProvider(BungeeData.BungeePerms_Suffix, new BungeePermsSuffixProvider());
            addProvider(BungeeData.BungeePerms_Rank, new BungeePermsRankProvider());
            addProvider(BungeeData.BungeePerms_Weight, new BungeePermsWeightProvider());
            addProvider(BungeeData.BungeePerms_PrimaryGroupPrefix, new BungeePermsGroupPrefixProvider());
            addProvider(BungeeData.BungeePerms_PlayerPrefix, new BungeePermsUserPrefixProvider());
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("LuckPerms");
        if (p != null) {
            if (isClassPresent("net.luckperms.api.LuckPerms")) {
                // API v5.0
                addProvider(BungeeData.LuckPerms_PrimaryGroup, new LuckPerms5PrimaryGroupProvider());
                addProvider(BungeeData.LuckPerms_Prefix, new LuckPerms5PrefixProvider());
                addProvider(BungeeData.LuckPerms_Suffix, new LuckPerms5SuffixProvider());
                addProvider(BungeeData.LuckPerms_Weight, new LuckPerms5WeightProvider());
            } else {
                // API v4.0
                addProvider(BungeeData.LuckPerms_PrimaryGroup, new LuckPermsPrimaryGroupProvider());
                addProvider(BungeeData.LuckPerms_Prefix, new LuckPermsPrefixProvider());
                addProvider(BungeeData.LuckPerms_Suffix, new LuckPermsSuffixProvider());
                addProvider(BungeeData.LuckPerms_Weight, new LuckPermsWeightProvider());
            }
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("BungeeOnlineTime");
        if (p != null && isClassPresent("lu.r3flexi0n.bungeeonlinetime.Core")) {
            // BungeeOnlineTime 3.0 or older
            addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider30());
        } else if (p != null && isClassPresent("bungeeonlinetime.Main")) {
            // BungeeOnlineTime 3.1+
            addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider31());
        } else if (p != null && isClassPresent("lu.r3flexi0n.bungeeonlinetime.BungeeOnlineTime")) {
            if (isMethodPresent("lu.r3flexi0n.bungeeonlinetime.utils.MySQL", "querySync", String.class)) {
                // BungeeOnlineTime 4.3+
                addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider43());
            } else if (isClassPresent("lu.r3flexi0n.bungeeonlinetime.utils.MySQL")) {
                // BungeeOnlineTime 5.2+
                addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider53());
            } else if (isClassPresent("lu.r3flexi0n.bungeeonlinetime.MySQL")) {
                // BungeeOnlineTime 5.4+
                addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider55());
            } else if (isMethodPresent("lu.r3flexi0n.bungeeonlinetime.database.SQL", "getOnlineTime", UUID.class, long.class)) {
                // BungeeOnlineTime 6.1+
                addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider61());
            } else if (isMethodPresent("lu.r3flexi0n.bungeeonlinetime.database.SQL", "getOnlineTime", UUID.class)) {
                // BungeeOnlineTime 7.0+
                addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider70());
            } 
        } else if (p != null && isClassPresent("lu.r3flexi0n.bungeeonlinetime.BungeeOnlineTimePlugin")) {
            // BungeeOnlineTime 8.3+
            addProvider(BungeeData.BungeeOnlineTime_OnlineTime, new BungeeOnlineTimeOnlineTimeProvider83());
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("ProtocolSupportBungee");
        if (p != null) {
            addProvider(BungeeData.ClientVersion, new ProtocolSupportBungeeClientVersionProvider());
        } else {
            addProvider(BungeeData.ClientVersion, new BungeeClientVersionProvider());
        }

        p = ProxyServer.getInstance().getPluginManager().getPlugin("Clans");
        if (p != null && isClassPresent("de.simonsator.partyandfriends.clan.api.ClansManager")) {
            addProvider(BungeeData.PAFClans_ClanName, new ClansClanNameProvider());
            addProvider(BungeeData.PAFClans_ClanTag, new ClansClanTagProvider());
            addProvider(BungeeData.PAFClans_IsLeader, new ClansIsLeaderProvider());
            addProvider(BungeeData.PAFClans_MemberCount, new ClansClanMembersProvider());
            addProvider(BungeeData.PAFClans_OnlineMemberCount, new ClansClanOnlineMembersProvider());
        }

        if (ProxyServer.getInstance().getPluginManager().getPlugin("ProxySuite") != null) {
            addProvider(BungeeData.ProxyCore_IsHidden, new ProxySuiteIsVanishedProvider(logger));
        }

        if (isClassPresent("de.myzelyam.api.vanish.BungeeVanishAPI")) {
            addProvider(BungeeData.PremiumVanish_IsHidden, new PremiumVanishIsVanishedProvider());
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
