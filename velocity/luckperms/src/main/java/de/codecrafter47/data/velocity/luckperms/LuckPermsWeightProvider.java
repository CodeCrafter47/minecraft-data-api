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

package de.codecrafter47.data.velocity.luckperms;

import com.velocitypowered.api.proxy.Player;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.caching.UserData;

import java.util.List;
import java.util.function.Function;

public class LuckPermsWeightProvider implements Function<Player, Integer> {

    @Override
    public Integer apply(Player player) {
        LuckPermsApi lp = LuckPerms.getApiSafe().orElse(null);
        if (lp == null) {
            return null;
        }

        User user = lp.getUser(player.getUniqueId());
        if (user == null) {
            return null;
        }

        UserData data = user.getCachedData();
        Contexts context = lp.getContextManager().getApplicableContexts(player);
        MetaData metaData = data.getMetaData(context);

        List<String> values = metaData.getMetaMultimap().get("weight");

        for (String value : values) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        return 0;
    }
}