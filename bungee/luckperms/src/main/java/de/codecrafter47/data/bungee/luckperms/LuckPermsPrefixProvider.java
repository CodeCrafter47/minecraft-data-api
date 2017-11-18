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

package de.codecrafter47.data.bungee.luckperms;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.UserData;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;

public class LuckPermsPrefixProvider implements Function<ProxiedPlayer, String> {

    @Override
    public String apply(ProxiedPlayer player) {
        LuckPermsApi lp = LuckPerms.getApiSafe().orElse(null);
        if (lp == null) {
            return null;
        }

        User user = lp.getUserSafe(player.getUniqueId()).orElse(null);
        if (user == null) {
            return null;
        }

        UserData data = user.getCachedData();

        Contexts context = lp.getContextForUser(user).orElse(null);
        if (context == null) {
            return null;
        }

        return data.getMetaData(context).getPrefix();
    }
}
