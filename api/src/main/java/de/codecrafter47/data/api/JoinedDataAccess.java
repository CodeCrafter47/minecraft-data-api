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
import java.util.Arrays;
import java.util.Collection;

public class JoinedDataAccess<B> implements DataAccess<B> {
    private final Collection<DataAccess<B>> collection;

    @SafeVarargs
    public static <B> JoinedDataAccess<B> of(DataAccess<B>... accessors) {
        return new JoinedDataAccess<>(Arrays.asList(accessors));
    }

    public JoinedDataAccess(Collection<DataAccess<B>> collection) {
        this.collection = collection;
    }

    @Override
    @Nullable
    public <V> V get(DataKey<V> key, B context) {
        DataAccess<B> dataAccess = getFirst(key);
        return null != dataAccess ? dataAccess.get(key, context) : null;
    }

    @Override
    public boolean provides(DataKey<?> key) {
        return null != getFirst(key);
    }

    @Nullable
    private DataAccess<B> getFirst(DataKey<?> key) {
        for (DataAccess<B> dataAccess : collection) {
            if (dataAccess.provides(key)) {
                return dataAccess;
            }
        }
        return null;
    }
}
