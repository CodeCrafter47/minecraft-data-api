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

package de.codecrafter47.data.sponge.api;

import de.codecrafter47.data.api.DataKey;
import de.codecrafter47.data.api.DataKeyCatalogue;
import de.codecrafter47.data.api.TypeToken;
import de.codecrafter47.data.minecraft.api.MinecraftData;

public class SpongeData implements DataKeyCatalogue {

    // Player data
    public final static DataKey<String> Prefix = new DataKey<>("sponge:prefix", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> Suffix = new DataKey<>("sponge:suffix", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<String> PermissionGroup = new DataKey<>("sponge:permgroup", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
    public final static DataKey<Integer> PermissionGroupRank = new DataKey<>("sponge:permgrouprank", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);
    public final static DataKey<Integer> PermissionGroupWeight = new DataKey<>("sponge:permgroupweight", MinecraftData.SCOPE_PLAYER, TypeToken.INTEGER);

    public final static DataKey<Double> Balance = new DataKey<>("sponge:balance", MinecraftData.SCOPE_PLAYER, TypeToken.DOUBLE);

    // Server data
    public final static DataKey<String> CurrencyNameSingular = new DataKey<>("sponge:currencynamesingular", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
    public final static DataKey<String> CurrencyNamePlural = new DataKey<>("sponge:currencynameplural", MinecraftData.SCOPE_SERVER, TypeToken.STRING);
}
