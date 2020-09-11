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

package de.codecrafter47.data.bungee.proxysuite;

import de.sabbertran.proxysuite.ProxySuiteAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxySuiteIsVanishedProvider implements Function<ProxiedPlayer, Boolean> {
    private final Logger logger;

    public ProxySuiteIsVanishedProvider(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Boolean apply(ProxiedPlayer player) {
        if (ProxyServer.getInstance().getPluginManager().getPlugin("ProxySuite") != null) {
            try {
                return ProxySuiteAPI.isVanished(player);
            } catch (Throwable th) {
                logger.log(Level.WARNING, "An error occurred while looking up a players vanish status from ProxyCore.", th);
            }
        }
        return null;
    }
}
