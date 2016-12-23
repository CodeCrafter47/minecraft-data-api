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

package de.codecrafter47.data.bukkit.api;

import de.codecrafter47.data.api.DataKey;
import de.codecrafter47.data.api.DataKeyCatalogue;
import de.codecrafter47.data.api.TypeToken;
import de.codecrafter47.data.minecraft.api.MinecraftData;

public class BukkitData implements DataKeyCatalogue {

    public final static DataKey<String> PlayerListName = new DataKey<>("bukkit:playerlistname", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Vault_Prefix = new DataKey<>("bukkit:vault:prefix", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Vault_Suffix = new DataKey<>("bukkit:vault:suffix", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Vault_PermissionGroup = new DataKey<>("bukkit:vault:permgroup", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> Vault_PermissionGroupRank = new DataKey<>("bukkit:vault:permgrouprank", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Integer> Vault_PermissionGroupWeight = new DataKey<>("bukkit:vault:permgroupweight", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Double> Vault_Balance = new DataKey<>("bukkit:vault:balance", MinecraftData.SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<String> Vault_PrimaryGroupPrefix = new DataKey<>("bukkit:vault:primarygroupprefix", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Vault_PlayerPrefix = new DataKey<>("bukkit:vault:playerprefix", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Boolean> VanishNoPacket_IsVanished = new DataKey<>("bukkit:vanishnopacket:isvanished", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<Integer> PlayerPoints_Points = new DataKey<>("bukkit:playerpoints:points", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<String> Factions_FactionName = new DataKey<>("bukkit:factions:factionname", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> Factions_FactionMembers = new DataKey<>("bukkit:factions:members", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Integer> Factions_OnlineFactionMembers = new DataKey<>("bukkit:factions:onlinemembers", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<String> Factions_FactionsWhere = new DataKey<>("bukkit:factions:where", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Factions_FactionsRank = new DataKey<>("bukkit:factions:rank", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> Factions_FactionPower = new DataKey<>("bukkit:factions:factionpower", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Integer> Factions_PlayerPower = new DataKey<>("bukkit:factions:playerpower", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Boolean> SuperVanish_IsVanished = new DataKey<>("bukkit:supervanish:isvanished", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<String> SimpleClans_ClanName = new DataKey<>("bukkit:simpleclans:clanname", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> SimpleClans_ClanMembers = new DataKey<>("bukkit:simpleclans:clanmembers", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Integer> SimpleClans_OnlineClanMembers = new DataKey<>("bukkit:simpleclans:onlineclanmembers", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<String> SimpleClans_ClanTag = new DataKey<>("bukkit:simpleclans:clantag", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> SimpleClans_ClanTagLabel = new DataKey<>("bukkit:simpleclans:clantaglabel", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> SimpleClans_ClanColorTag = new DataKey<>("bukkit:simpleclans:clancolortag", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Boolean> Essentials_IsVanished = new DataKey<>("bukkit:essentials:vanished", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<Boolean> Essentials_IsAFK = new DataKey<>("bukkit:essentials:isafk", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<String> Multiverse_WorldAlias = new DataKey<>("bukkit:multiverse:worldalias", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);

    // Bukkit server data keys
    public final static DataKey<String> Vault_CurrencyNameSingular = new DataKey<>("bukkit:vault:currencynamesingular", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
    public final static DataKey<String> Vault_CurrencyNamePlural = new DataKey<>("bukkit:vault:currencynameplural", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
}
