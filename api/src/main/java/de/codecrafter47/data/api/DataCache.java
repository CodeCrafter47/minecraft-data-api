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

package de.codecrafter47.data.api;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache implements DataHolder {
    private final Map<DataKey<?>, Object> cache = new ConcurrentHashMap<>();
    private final Map<DataKey<?>, Set<Runnable>> listenerMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void updateValue(DataKey<T> dataKey, T object) {
        if (object == null) {
            cache.remove(dataKey);
        } else {
            cache.put(dataKey, object);
        }
        Set<Runnable> listeners = listenerMap.get(dataKey);
        AssertionError error = null;
        if (listeners != null) {
            for (Runnable listener : listeners) {
                try {
                    listener.run();
                } catch (Throwable th) {
                    if (error == null) {
                        error = new AssertionError("Failed to run listener");
                    } else {
                        error.addSuppressed(th);
                    }
                }
            }
        }
        if (error != null) {
            throw error;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(DataKey<V> key) {
        return (V) cache.get(key);
    }

    @Override
    public <T> void addDataChangeListener(DataKey<T> key, Runnable listener) {
        listenerMap.computeIfAbsent(key, k -> Sets.newConcurrentHashSet()).add(listener);
    }

    @Override
    public <T> void removeDataChangeListener(DataKey<T> key, Runnable listener) {
        Set<Runnable> listeners = listenerMap.get(key);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                listenerMap.remove(key);
            }
        }
    }

    public boolean hasListeners(DataKey<?> key) {
        return listenerMap.containsKey(key);
    }
}
