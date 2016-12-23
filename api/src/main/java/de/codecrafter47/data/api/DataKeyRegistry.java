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

import com.google.common.base.Throwables;
import lombok.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Registry for accessing data keys by their identifier.
 */
public class DataKeyRegistry {
    private final Map<String, DataKey<?>> map;

    private DataKeyRegistry(Map<String, DataKey<?>> map) {
        this.map = map;
    }

    @SafeVarargs
    public static DataKeyRegistry of(Class<? extends DataKeyCatalogue>... catalogues) {
        return new DataKeyRegistry(Arrays.stream(catalogues)
                .map(Class::getFields)
                .flatMap(Arrays::stream)
                .filter(field -> (field.getModifiers() & Modifier.STATIC) != 0)
                .filter(field -> (field.getModifiers() & Modifier.FINAL) != 0)
                .filter(field -> (field.getModifiers() & Modifier.PUBLIC) != 0)
                .filter(field -> DataKey.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (DataKey<?>) field.get(null);
                    } catch (IllegalAccessException e) {
                        Throwables.propagate(e);
                        return null; // not going to happen!
                    }
                })
                .collect(Collectors.toMap(DataKey::getId, key -> key)));
    }

    @Nullable
    public DataKey<?> getKeyByIdentifier(@NonNull @Nonnull String identifier) {
        return map.get(identifier);
    }
}
