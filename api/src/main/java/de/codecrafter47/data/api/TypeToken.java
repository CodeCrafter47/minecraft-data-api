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

import java.time.Duration;
import java.util.List;

public class TypeToken<T> {

    public static final TypeToken<Boolean> BOOLEAN = create();
    public static final TypeToken<Integer> INTEGER = create();
    public static final TypeToken<Float> FLOAT = create();
    public static final TypeToken<Double> DOUBLE = create();
    public static final TypeToken<String> STRING = create();
    public static final TypeToken<Duration> DURATION = create();
    public static final TypeToken<List<String>> STRING_LIST = create();

    public static <T> TypeToken<T> create() {
        return new TypeToken<>();
    }

    private TypeToken () {
    }
}
