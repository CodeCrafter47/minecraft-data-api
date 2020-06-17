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

    public final static DataKey<Boolean> Sponge_IsVanished = new DataKey<>("sponge:isvanished", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<Boolean> Nucleus_IsAFK = new DataKey<>("sponge:nucleus:isafk", MinecraftData.SCOPE_PLAYER, TypeToken.BOOLEAN);
    public final static DataKey<String> Nucleus_Nick = new DataKey<>("sponge:nucleus:nick", MinecraftData.SCOPE_PLAYER, TypeToken.STRING);
}
