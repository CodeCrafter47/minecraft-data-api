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

import lombok.Value;

@Value
public class DataKey<T> {
    private final String id;
    private final Scope scope;
    private final TypeToken<T> type;
    private final String parameter;

    private DataKey(String id, Scope scope, TypeToken<T> type, String parameter) {
        this.id = id;
        this.scope = scope;
        this.type = type;
        this.parameter = parameter;
    }

    public DataKey(String id, Scope scope, TypeToken<T> type) {
        this(id, scope, type, null);
    }

    public DataKey<T> withParameter(String parameter) {
        return new DataKey<>(id, scope, type, parameter);
    }
}
