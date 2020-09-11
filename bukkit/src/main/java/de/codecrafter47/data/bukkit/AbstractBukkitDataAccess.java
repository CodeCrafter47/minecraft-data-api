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

package de.codecrafter47.data.bukkit;

import de.codecrafter47.data.api.AbstractDataAccess;
import de.codecrafter47.data.api.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ConcurrentModificationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractBukkitDataAccess<B> extends AbstractDataAccess<B> {
    protected final Logger logger;
    protected final Plugin plugin;

    public AbstractBukkitDataAccess(Logger logger, Plugin plugin) {
        this.logger = logger;
        this.plugin = plugin;
    }

    @Override
    public <V> V get(DataKey<V> key, B context) {
        try {
            return super.get(key, context);
        } catch (Throwable th) {
            if (!Bukkit.isPrimaryThread() && isAsyncOpError(th)) {
                try {
                    return Bukkit.getScheduler().callSyncMethod(plugin, () -> get(key, context)).get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.log(Level.SEVERE, "Unexpected exception", e);
                }
            }
            logger.log(Level.SEVERE, "Unexpected exception", th);
        }
        return null;
    }

    private boolean isAsyncOpError(Throwable th) {
        StackTraceElement[] st;
        return th instanceof ConcurrentModificationException || ((st = th.getStackTrace()).length > 0 && st[0].getClassName().equals("org.spigotmc.AsyncCatcher")) || (th.getCause() != null && isAsyncOpError(th.getCause()));
    }
}
