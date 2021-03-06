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

package de.codecrafter47.data.bungee.clans;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.clan.api.Clan;
import de.simonsator.partyandfriends.clan.api.ClansManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;

public class ClansClanNameProvider implements Function<ProxiedPlayer, String> {

    @Override
    public String apply(ProxiedPlayer player) {
        ClansManager clansManager = ClansManager.getInstance();
        if (clansManager == null)
            return null;

        PAFPlayerManager playerManager = PAFPlayerManager.getInstance();
        if (playerManager == null)
            return null;

        OnlinePAFPlayer pafPlayer = playerManager.getPlayer(player);
        if (pafPlayer == null)
            return null;

        Clan clan = clansManager.getClan(pafPlayer);
        if (clan == null)
            return null;

        return clan.getClanName();
    }
}
