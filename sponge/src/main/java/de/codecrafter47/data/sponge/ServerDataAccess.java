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

package de.codecrafter47.data.sponge;

import de.codecrafter47.data.minecraft.api.MinecraftData;
import org.slf4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.economy.EconomyService;

public class ServerDataAccess extends AbstractSpongeDataAccess<Server> {

    public ServerDataAccess(Logger logger) {
        super(logger);

        addProvider(MinecraftData.MinecraftVersion, server -> Sponge.getGame().getPlatform().getMinecraftVersion().getName());
        addProvider(MinecraftData.ServerModName, server -> Sponge.getGame().getPlatform().getImplementation().getName());
        addProvider(MinecraftData.ServerModVersion, server -> Sponge.getGame().getPlatform().getImplementation().getVersion().orElse(null));
        addProvider(MinecraftData.TPS, Server::getTicksPerSecond);

        addProvider(MinecraftData.Economy_CurrencyNamePlural, server -> Sponge.getGame().getServiceManager().provide(EconomyService.class).map(e -> e.getDefaultCurrency().getPluralDisplayName().toPlain()).orElse(null));
        addProvider(MinecraftData.Economy_CurrencyNameSingular, server -> Sponge.getGame().getServiceManager().provide(EconomyService.class).map(e -> e.getDefaultCurrency().getDisplayName().toPlain()).orElse(null));
    }
}