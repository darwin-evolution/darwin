/**
 * The MIT License
 *
 * Copyright (c) 2016 Darwin contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.darwin.typesafeconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.darwin.execution.ImplementationPreferenceType;
import io.darwin.api.ImplementationChooser;

public class TypesafeConfigImplementationChooser extends ImplementationChooser {

    public static final String CONFIG_PATH_PREFIX = "darwin.evolution";
    public static final String CONFIG_PATH_SUFFIX = "evolved";

    private Config config;

    public TypesafeConfigImplementationChooser() {
        this.config = ConfigFactory.load();
    }

    protected TypesafeConfigImplementationChooser(Config config) {
        this.config = config;
    }

    @Override
    public ImplementationPreferenceType chooseImplementation(String evolutionName) {
        String evolvedImplementationEnabled = CONFIG_PATH_PREFIX + "." + evolutionName + "." + CONFIG_PATH_SUFFIX;
        if (config.hasPath(evolvedImplementationEnabled) && config.getBoolean(evolvedImplementationEnabled)) {
            return ImplementationPreferenceType.EVOLVED;
        } else {
            return ImplementationPreferenceType.CURRENT;
        }
    }
}
