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

package de.codecrafter47.data.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;

public class BungeeClientVersionProvider implements Function<ProxiedPlayer, String> {
    @Override
    public String apply(ProxiedPlayer player) {
        switch (player.getPendingConnection().getVersion()) {
            // todo find some way to automatically update this
            case 404:
                return "1.13.2";
            case 401:
                return "1.13.1";
            case 393:
                return "1.13";
            case 340:
                return "1.12.2";
            case 338:
                return "1.12.1";
            case 335:
                return "1.12";
            case 316:
                return "1.11.2";
            case 315:
                return "1.11";
            case 210:
                return "1.10";
            case 110:
                return "1.9.4";
            case 109:
                return "1.9.2";
            case 108:
                return "1.9.1";
            case 107:
                return "1.9";
            case 47:
                return "1.8";
            case 5:
                return "1.7.10";
            case 4:
                return "1.7.5";
            default:
                return null;
        }
    }
}
