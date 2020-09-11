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

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractDataAccess<B> implements DataAccess<B> {
    private final Map<String, BiFunction<B, DataKey<?>, ?>> providers = new HashMap<>();

    protected <V> void addProvider(DataKey<V> dataKey, Function<B, V> provider) {
        addProvider(dataKey, (b, k) -> provider.apply(b));
    }

    @SuppressWarnings("unchecked")
    protected <V, K extends DataKey<V>> void addProvider(DataKey<V> dataKey, BiFunction<B, K, V> provider) {
        providers.put(dataKey.getId(), (BiFunction<B, DataKey<?>, ?>) provider);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <V> V get(DataKey<V> key, B context) {
        BiFunction<B, DataKey<?>, ?> function = providers.get(key.getId());
        if (function != null) {
            return (V) function.apply(context, key);
        }
        return null;
    }

    @Override
    public boolean provides(DataKey<?> key) {
        return providers.containsKey(key.getId());
    }
}
