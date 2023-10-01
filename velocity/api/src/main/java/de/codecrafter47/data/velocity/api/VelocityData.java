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

package de.codecrafter47.data.velocity.api;

import de.codecrafter47.data.api.DataKey;
import de.codecrafter47.data.api.DataKeyCatalogue;
import de.codecrafter47.data.api.Scope;
import de.codecrafter47.data.api.TypeToken;

import java.time.Duration;

public class VelocityData implements DataKeyCatalogue {
    public final static Scope SCOPE_VELOCITY_PLAYER = new Scope("bungee:player");
    public final static Scope SCOPE_VELOCITY_SERVER = new Scope("bungee:server");
    public final static Scope SCOPE_VELOCITY_PROXY = new Scope("bungee:proxy");

    // Velocity player data keys
    public final static DataKey<String> Velocity_DisplayName = new DataKey<>("bungeecord:displayname", SCOPE_VELOCITY_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> Velocity_Ping = new DataKey<>("bungeecord:ping", SCOPE_VELOCITY_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Boolean> Velocity_Permission = new DataKey<>("bungeecord:permission", SCOPE_VELOCITY_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<String> LuckPerms_PrimaryGroup = new DataKey<>("luckperms:primarygroup", SCOPE_VELOCITY_PLAYER, TypeToken.STRING);
    public final static DataKey<String> LuckPerms_Prefix = new DataKey<>("luckperms:prefix", SCOPE_VELOCITY_PLAYER, TypeToken.STRING);
    public final static DataKey<String> LuckPerms_Suffix = new DataKey<>("luckperms:suffix", SCOPE_VELOCITY_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> LuckPerms_Weight = new DataKey<>("luckperms:weight", SCOPE_VELOCITY_PLAYER, TypeToken.INTEGER);
    public final static DataKey<String> ClientVersion = new DataKey<>("minecraft:clientversion", SCOPE_VELOCITY_PLAYER, TypeToken.STRING);
    public final static DataKey<Duration> Velocity_SessionDuration = new DataKey<>("minecraft:sessionduration", SCOPE_VELOCITY_PLAYER, TypeToken.DURATION);
    public final static DataKey<String> Velocity_Server = new DataKey<>("bungeecord:server", SCOPE_VELOCITY_PLAYER, TypeToken.STRING);

    public static DataKey<Boolean> permission(String permission) {
        return Velocity_Permission.withParameter(permission);
    }
}
