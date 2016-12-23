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

package de.codecrafter47.data.sponge;

import de.codecrafter47.data.api.AbstractDataAccess;
import de.codecrafter47.data.api.DataKey;
import org.slf4j.Logger;

public abstract class AbstractSpongeDataAccess<T> extends AbstractDataAccess<T> {
    private final Logger logger;

    protected AbstractSpongeDataAccess(Logger logger) {
        this.logger = logger;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(DataKey<V> key, T context) {
        try {
            return super.get(key, context);
        } catch (Throwable th) {
            logger.error("Unexpected exception", th);
        }
        return null;
    }
}