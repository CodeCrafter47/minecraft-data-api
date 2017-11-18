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

package de.codecrafter47.data.api;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache implements DataHolder {
    private final Map<DataKey<?>, Object> cache = new ConcurrentHashMap<>();
    protected final Multimap<DataKey<?>, Runnable> listeners = Multimaps.synchronizedMultimap(MultimapBuilder.hashKeys().hashSetValues().build());

    @SuppressWarnings("unchecked")
    public <T> void updateValue(DataKey<T> dataKey, T object) {
        if (object == null) {
            cache.remove(dataKey);
        } else {
            cache.put(dataKey, object);
        }
        synchronized (listeners) {
            listeners.get(dataKey).forEach(Runnable::run);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(DataKey<V> key) {
        return (V) cache.get(key);
    }

    @Override
    public <T> void addDataChangeListener(DataKey<T> key, Runnable listener) {
        listeners.put(key, listener);
    }

    @Override
    public <T> void removeDataChangeListener(DataKey<T> key, Runnable listener) {
        listeners.remove(key, listener);
    }
}
