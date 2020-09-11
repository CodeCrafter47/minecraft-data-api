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

package de.codecrafter47.data.bukkit;

import de.codecrafter47.data.bukkit.api.BukkitData;
import de.codecrafter47.data.bukkit.askyblock.ASkyBlockIslandLevelProvider;
import de.codecrafter47.data.bukkit.askyblock.ASkyBlockIslandNameProvider;
import de.codecrafter47.data.bukkit.askyblock.ASkyBlockTeamLeaderProvider;
import de.codecrafter47.data.bukkit.cmi.CMIIsVanishedProvider;
import de.codecrafter47.data.bukkit.essentials.EssentialsIsAFKProvider;
import de.codecrafter47.data.bukkit.essentials.EssentialsIsVanishedProvider;
import de.codecrafter47.data.bukkit.factions.FactionMembersProvider;
import de.codecrafter47.data.bukkit.factions.FactionNameProvider;
import de.codecrafter47.data.bukkit.factions.FactionOnlineMembersProvider;
import de.codecrafter47.data.bukkit.factions.FactionPowerProvider;
import de.codecrafter47.data.bukkit.factions.FactionRankProvider;
import de.codecrafter47.data.bukkit.factions.FactionWhereProvider;
import de.codecrafter47.data.bukkit.factions.*;
import de.codecrafter47.data.bukkit.factionsuuid.*;
import de.codecrafter47.data.bukkit.multiverse.MultiverseWorldAliasProvider;
import de.codecrafter47.data.bukkit.playerpoints.PlayerPointsProvider;
import de.codecrafter47.data.bukkit.protocolvanish.ProtocolVanishIsVanishedProvider;
import de.codecrafter47.data.bukkit.simpleclans.*;
import de.codecrafter47.data.bukkit.supervanish.SuperVanishIsVanishedProvider;
import de.codecrafter47.data.bukkit.vanishnopacket.VanishNoPacketIsVanishedProvider;
import de.codecrafter47.data.bukkit.vault.*;
import de.codecrafter47.data.minecraft.api.MinecraftData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;

public class PlayerDataAccess extends AbstractBukkitDataAccess<Player> {
    private final Plugin plugin;

    public PlayerDataAccess(Plugin plugin) {
        super(plugin.getLogger(), plugin);
        this.plugin = plugin;
        init();
    }

    protected void init() {
        addProvider(MinecraftData.Health, Damageable::getHealth);
        addProvider(MinecraftData.Level, Player::getLevel);
        addProvider(MinecraftData.MaxHealth, Player::getMaxHealth);
        addProvider(MinecraftData.XP, Player::getExp);
        addProvider(MinecraftData.TotalXP, Player::getTotalExperience);
        addProvider(MinecraftData.PosX, player -> player.getLocation().getX());
        addProvider(MinecraftData.PosY, player -> player.getLocation().getY());
        addProvider(MinecraftData.PosZ, player -> player.getLocation().getZ());
        addProvider(MinecraftData.Team, player -> {
            Team team = player.getScoreboard().getPlayerTeam(player);
            return team != null ? team.getName() : null;
        });
        addProvider(MinecraftData.Permission, (player, key) -> {
            try {
                return player.hasPermission(key.getParameter());
            } catch (Throwable ignored) {
                return null;
            }
        });

        addProvider(MinecraftData.DisplayName, Player::getDisplayName);
        addProvider(BukkitData.PlayerListName, Player::getPlayerListName);
        addProvider(MinecraftData.World, player -> player.getWorld().getName());
        addProvider(BukkitData.BukkitPlayerMetadataVanished, player -> {
            if (player.hasMetadata("vanished") && !player.getMetadata("vanished").isEmpty())
                return player.getMetadata("vanished").get(0).asBoolean();
            return false;
        });

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            addProvider(MinecraftData.Economy_Balance, new VaultBalanceProvider(plugin));
            addProvider(MinecraftData.Permissions_PermissionGroup, new VaultGroupProvider());
            addProvider(MinecraftData.Permissions_PermissionGroupWeight, new VaultGroupWeightProvider());
            addProvider(MinecraftData.Permissions_PermissionGroupRank, new VaultGroupRankProvider());
            addProvider(MinecraftData.Permissions_Prefix, new VaultPrefixProvider());
            addProvider(MinecraftData.Permissions_Suffix, new VaultSuffixProvider());
            addProvider(MinecraftData.Permissions_PrimaryGroupPrefix, new VaultPrimaryGroupPrefixProvider());
            addProvider(MinecraftData.Permissions_PlayerPrefix, new VaultPlayerPrefixProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("VanishNoPacket") != null) {
            addProvider(BukkitData.VanishNoPacket_IsVanished, new VanishNoPacketIsVanishedProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("PlayerPoints") != null) {
            addProvider(BukkitData.PlayerPoints_Points, new PlayerPointsProvider(logger));
        }

        if (Bukkit.getPluginManager().getPlugin("Factions") != null) {
            if (classExists("com.massivecraft.factions.FPlayer")) {
                if (classExists("com.massivecraft.factions.perms.Role")) {
                    addProvider(BukkitData.Factions_FactionName, new FactionNameProvider05());
                    addProvider(BukkitData.Factions_FactionMembers, new FactionMembersProvider05());
                    addProvider(BukkitData.Factions_FactionPower, new FactionPowerProvider05());
                    addProvider(BukkitData.Factions_FactionsRank, new FactionRankProvider05());
                    addProvider(BukkitData.Factions_FactionsWhere, new FactionWhereProvider05());
                    addProvider(BukkitData.Factions_OnlineFactionMembers, new FactionOnlineMembersProvider05());
                    addProvider(BukkitData.Factions_PlayerPower, new FactionPlayerPowerProvider05());
                } else {
                    addProvider(BukkitData.Factions_FactionName, new de.codecrafter47.data.bukkit.factionsuuid.FactionNameProvider());
                    addProvider(BukkitData.Factions_FactionMembers, new de.codecrafter47.data.bukkit.factionsuuid.FactionMembersProvider());
                    addProvider(BukkitData.Factions_FactionPower, new de.codecrafter47.data.bukkit.factionsuuid.FactionPowerProvider());
                    addProvider(BukkitData.Factions_FactionsRank, new de.codecrafter47.data.bukkit.factionsuuid.FactionRankProvider());
                    addProvider(BukkitData.Factions_FactionsWhere, new de.codecrafter47.data.bukkit.factionsuuid.FactionWhereProvider());
                    addProvider(BukkitData.Factions_OnlineFactionMembers, new de.codecrafter47.data.bukkit.factionsuuid.FactionOnlineMembersProvider());
                    addProvider(BukkitData.Factions_PlayerPower, new de.codecrafter47.data.bukkit.factionsuuid.FactionPlayerPowerProvider());
                }
            } else if (classExists("com.massivecraft.factions.entity.MPlayer")) {
                addProvider(BukkitData.Factions_FactionName, new FactionNameProvider());
                addProvider(BukkitData.Factions_FactionMembers, new FactionMembersProvider());
                addProvider(BukkitData.Factions_FactionPower, new FactionPowerProvider());
                addProvider(BukkitData.Factions_FactionsRank, new FactionRankProvider());
                addProvider(BukkitData.Factions_FactionsWhere, new FactionWhereProvider());
                addProvider(BukkitData.Factions_OnlineFactionMembers, new FactionOnlineMembersProvider());
                addProvider(BukkitData.Factions_PlayerPower, new FactionsPlayerPowerProvider());
            } else {
                logger.warning("Unable to recognize your Factions version. Factions support is disabled. Please contact " +
                        "the plugin developer to request support for your Factions version (" +
                        Bukkit.getPluginManager().getPlugin("Factions").getDescription().getVersion() + ").");
            }
        }

        if (Bukkit.getPluginManager().getPlugin("SuperVanish") != null || Bukkit.getPluginManager().getPlugin("PremiumVanish") != null) {
            addProvider(BukkitData.SuperVanish_IsVanished, new SuperVanishIsVanishedProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("SimpleClans") != null) {
            addProvider(BukkitData.SimpleClans_ClanName, new SimpleClansClanNameProvider());
            addProvider(BukkitData.SimpleClans_ClanMembers, new SimpleClansMembersProvider());
            addProvider(BukkitData.SimpleClans_OnlineClanMembers, new SimpleClansOnlineClanMembersProvider());
            addProvider(BukkitData.SimpleClans_ClanTag, new SimpleClansClanTagProvider());
            addProvider(BukkitData.SimpleClans_ClanTagLabel, new SimpleClansClanTagLabelProvider());
            addProvider(BukkitData.SimpleClans_ClanColorTag, new SimpleClansClanColorTagProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
            addProvider(BukkitData.Essentials_IsVanished, new EssentialsIsVanishedProvider());
            addProvider(BukkitData.Essentials_IsAFK, new EssentialsIsAFKProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("Multiverse-Core") != null) {
            addProvider(BukkitData.Multiverse_WorldAlias, new MultiverseWorldAliasProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("ASkyBlock") != null) {
            addProvider(BukkitData.ASkyBlock_IslandLevel, new ASkyBlockIslandLevelProvider());
            addProvider(BukkitData.ASkyBlock_IslandName, new ASkyBlockIslandNameProvider());
            addProvider(BukkitData.ASkyBlock_TeamLeader, new ASkyBlockTeamLeaderProvider());
        }

        if (Bukkit.getPluginManager().getPlugin("CMI") != null) {
            if (isMethodPresent("com.Zrips.CMI.Containers.CMIUser", "isVanished")) {
                addProvider(BukkitData.CMI_IsVanished, new CMIIsVanishedProvider());
            }
        }

        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolVanish")) {
            addProvider(BukkitData.ProtocolVanish_IsVanished, new ProtocolVanishIsVanishedProvider());
        }
    }

    private static boolean classExists(String name) {
        try {
            Class.forName(name);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
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
