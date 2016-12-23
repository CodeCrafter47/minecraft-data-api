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

package de.codecrafter47.data.bukkit;

import de.codecrafter47.data.bukkit.api.BukkitData;
import de.codecrafter47.data.bukkit.vault.VaultCurrencyNamePluralProvider;
import de.codecrafter47.data.bukkit.vault.VaultCurrencyNameSingularProvider;
import de.codecrafter47.data.minecraft.api.MinecraftData;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class ServerDataAccess extends AbstractBukkitDataAccess<Server> {

    private final Plugin plugin;

    public ServerDataAccess(Plugin plugin) {
        super(plugin.getLogger(), plugin);
        this.plugin = plugin;
        init();
    }

    protected void init() {
        addProvider(MinecraftData.MinecraftVersion, Server::getVersion);
        addProvider(MinecraftData.ServerModName, Server::getName);
        addProvider(MinecraftData.ServerModVersion, Server::getBukkitVersion);
        addProvider(MinecraftData.TPS, ServerTPSProvider.getInstance(plugin));

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            addProvider(BukkitData.Vault_CurrencyNamePlural, new VaultCurrencyNamePluralProvider());
            addProvider(BukkitData.Vault_CurrencyNameSingular, new VaultCurrencyNameSingularProvider());
        }
    }

}
