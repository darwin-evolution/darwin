/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016, Darwin Project
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
package com.github.darwinevolution.darwin.typesafeconfig;

import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypesafeConfigImplementationChooserTest {

    @Test
    public void shouldReturnEvolvedImplementationForEvolvedEnabledConfigSetting() {
        ImplementationPreference implementationPreference = new TypesafeConfigImplementationChooser().chooseImplementation("EvolvedEnabled");

        assertThat(implementationPreference).isEqualTo(ImplementationPreference.EVOLVED);
    }

    @Test
    public void shouldReturnCurrentImplementationForEvolvedDisabledConfigSetting() {
        ImplementationPreference implementationPreference = new TypesafeConfigImplementationChooser().chooseImplementation("EvolvedDisabled");

        assertThat(implementationPreference).isEqualTo(ImplementationPreference.PROTOPLAST);
    }

    @Test
    public void shouldReturnCurrentImplementationForMissingConfigSetting() {
        ImplementationPreference implementationPreference = new TypesafeConfigImplementationChooser().chooseImplementation("EvolvedMissing");

        assertThat(implementationPreference).isEqualTo(ImplementationPreference.PROTOPLAST);
    }
}
