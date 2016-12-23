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

import de.codecrafter47.data.api.AbstractDataAccess;
import de.codecrafter47.data.api.DataKey;
import net.md_5.bungee.api.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBungeeDataAccess<B> extends AbstractDataAccess<B> {

    protected final Plugin plugin;
    private final Logger logger;

    public AbstractBungeeDataAccess(Plugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    @Nullable
    @Override
    public <V> V get(DataKey<V> key, B context) {
        try {
            return super.get(key, context);
        } catch (Throwable th) {
            logger.log(Level.WARNING, "Failed to acquire data " + key + " from " + context, th);
        }
        return null;
    }
}
