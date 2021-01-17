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

package de.codecrafter47.data.minecraft.api;

import de.codecrafter47.data.api.DataKey;
import de.codecrafter47.data.api.DataKeyCatalogue;
import de.codecrafter47.data.api.Scope;
import de.codecrafter47.data.api.TypeToken;

public class MinecraftData implements DataKeyCatalogue {
    public final static Scope SCOPE_PLAYER = new Scope("minecraft:player");
    public final static Scope SCOPE_SERVER = new Scope("minecraft:server");

    // Player data keys
    public final static DataKey<String> Permissions_PlayerPrefix = new DataKey<>("minecraft:permissions:playerprefix", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Permissions_PrimaryGroupPrefix = new DataKey<>("minecraft:permissions:primarygroupprefix", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Double> Economy_Balance = new DataKey<>("minecraft:economy:balance", SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<Integer> Permissions_PermissionGroupWeight = new DataKey<>("minecraft:permissions:permgroupweight", SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Integer> Permissions_PermissionGroupRank = new DataKey<>("minecraft:permissions:permgrouprank", SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<String> Permissions_PermissionGroup = new DataKey<>("minecraft:permissions:permgroup", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Permissions_Suffix = new DataKey<>("minecraft:permissions:suffix", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Permissions_Prefix = new DataKey<>("minecraft:permissions:prefix", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> DisplayName = new DataKey<>("minecraft:displayname", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Double> Health = new DataKey<>("minecraft:health", SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<String> World = new DataKey<>("bukkit:world", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Team = new DataKey<>("minecraft:team", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> TeamColor = new DataKey<>("minecraft:teamcolor", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> TeamDisplayName = new DataKey<>("minecraft:teamdisplayname", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> TeamPrefix = new DataKey<>("minecraft:teamprefix", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> TeamSuffix = new DataKey<>("minecraft:teamsuffix", SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Double> PosZ = new DataKey<>("minecraft:posz", SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<Double> PosY = new DataKey<>("minecraft:posy", SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<Double> PosX = new DataKey<>("minecraft:posx", SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<Integer> TotalXP = new DataKey<>("minecraft:totalxp", SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Float> XP = new DataKey<>("minecraft:xp", SCOPE_PLAYER, TypeToken.FLOAT);
    public final static DataKey<Integer> Level = new DataKey<>("minecraft:xplevel", SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Double> MaxHealth = new DataKey<>("minecraft:maxhealth", SCOPE_PLAYER, TypeToken.DOUBLE);
    public final static DataKey<Boolean> Permission = new DataKey<>("minecraft:permission", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);

    public static DataKey<Boolean> permission(String permission) {
        return Permission.withParameter(permission);
    }

    // Server data keys
    public final static DataKey<Double> TPS = new DataKey<>("minecraft:tps", MinecraftData.SCOPE_SERVER, TypeToken.DOUBLE);
    public final static DataKey<String> MinecraftVersion = new DataKey<>("minecraft:version", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
    public final static DataKey<String> ServerModName = new DataKey<>("minecraft:modname", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
    public final static DataKey<String> ServerModVersion = new DataKey<>("minecraft:modversion", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
    public final static DataKey<String> Economy_CurrencyNamePlural = new DataKey<>("minecraft:economy:currencynameplural", SCOPE_SERVER, TypeToken.STRING);
    public final static DataKey<String> Economy_CurrencyNameSingular = new DataKey<>("minecraft:economy:currencynamesingular", SCOPE_SERVER, TypeToken.STRING);
}
