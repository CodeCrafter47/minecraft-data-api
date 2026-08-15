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

package de.codecrafter47.data.bukkit.multiverse;

import org.bukkit.entity.Player;
import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.external.vavr.control.Option;

import java.util.function.Function;

/**
 * World alias lookup for Multiverse-Core 5.x.
 *
 * <p>Multiverse 5 renamed its packages from {@code com.onarandombox.MultiverseCore} to
 * {@code org.mvplugins.multiverse.core}, so {@code MultiverseWorldAliasProvider} - which targets
 * the 4.x API - fails with {@code NoClassDefFoundError} on a Multiverse 5 server.
 *
 * <p>Uses {@code getAliasOrName()} rather than {@code getAlias()} so a world without an alias
 * yields its name instead of an empty string.
 */
public class MultiverseWorldAliasProvider5 implements Function<Player, String> {

    @Override
    public String apply(Player player) {
        Option<MultiverseWorld> world = MultiverseCoreApi.get().getWorldManager().getWorld(player.getWorld());
        return world.isDefined() ? world.get().getAliasOrName() : null;
    }
}
