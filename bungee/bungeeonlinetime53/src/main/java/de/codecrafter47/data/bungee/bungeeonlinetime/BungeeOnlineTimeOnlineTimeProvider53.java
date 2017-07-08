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

package de.codecrafter47.data.bungee.bungeeonlinetime;

import lu.r3flexi0n.bungeeonlinetime.BungeeOnlineTime;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class BungeeOnlineTimeOnlineTimeProvider53 implements Function<ProxiedPlayer, Duration> {

    @Override
    public Duration apply(ProxiedPlayer player) {
        try {
            int time = BungeeOnlineTime.mysql.getOnlineTime(player.getUniqueId());
            return Duration.of(time, ChronoUnit.MINUTES);
        } catch (SQLException | ClassNotFoundException ignored) {

        }
        return null;
    }
}