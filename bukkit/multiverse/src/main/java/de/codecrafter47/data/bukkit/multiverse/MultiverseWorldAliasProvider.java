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

import com.onarandombox.MultiverseCore.api.MVPlugin;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class MultiverseWorldAliasProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        MVPlugin plugin = (MVPlugin) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        if (plugin != null) {
            MultiverseWorld mvWorld = plugin.getCore().getMVWorldManager().getMVWorld(player.getWorld());
            if (mvWorld != null) {
                return mvWorld.getAlias();
            }
        }
        return null;
    }
}
