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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache implements DataHolder {
    private final Map<DataKey<?>, Object> cache = new ConcurrentHashMap<>();
    private final Map<DataKey<?>, ListenerMapEntry> listenerMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void updateValue(DataKey<T> dataKey, T object) {
        if (object == null) {
            cache.remove(dataKey);
        } else {
            cache.put(dataKey, object);
        }
        ListenerMapEntry entry = listenerMap.get(dataKey);
        AssertionError error = null;
        if (entry != null) {
            entry.isNotifyingListeners = true;
            for (Runnable listener : entry.listeners) {
                try {
                    if (!entry.listenersRemoved.contains(listener)) {
                        listener.run();
                    }
                } catch (Throwable th) {
                    if (error == null) {
                        error = new AssertionError("Failed to run listener", th);
                    } else {
                        error.addSuppressed(th);
                    }
                }
            }
            entry.listeners.removeAll(entry.listenersRemoved);
            entry.listeners.addAll(entry.listenersAdded);
            entry.listenersRemoved.clear();
            entry.listenersAdded.clear();
            entry.isNotifyingListeners = false;
            if (entry.listeners.isEmpty()) {
                listenerMap.remove(dataKey);
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
        ListenerMapEntry entry = listenerMap.computeIfAbsent(key, k -> new ListenerMapEntry());
        if (entry.isNotifyingListeners) {
            entry.listenersRemoved.remove(listener);
            entry.listenersAdded.add(listener);
        } else {
            entry.listeners.add(listener);
        }
    }

    @Override
    public <T> void removeDataChangeListener(DataKey<T> key, Runnable listener) {
        ListenerMapEntry entry = listenerMap.get(key);
        if (entry != null) {
            if (entry.isNotifyingListeners) {
                entry.listenersAdded.remove(listener);
                entry.listenersRemoved.add(listener);
            } else {
                entry.listeners.remove(listener);
                if (entry.listeners.isEmpty()) {
                    listenerMap.remove(key);
                }
            }
        }
    }

    public boolean hasListeners(DataKey<?> key) {
        return listenerMap.containsKey(key);
    }

    private static class ListenerMapEntry {
        private final Set<Runnable> listeners;
        private boolean isNotifyingListeners;
        private final Set<Runnable> listenersAdded;
        private final Set<Runnable> listenersRemoved;

        public ListenerMapEntry() {
            this.listeners = Sets.newConcurrentHashSet();
            this.isNotifyingListeners = false;
            this.listenersAdded = new HashSet<>();
            this.listenersRemoved = new HashSet<>();
        }
    }
}
