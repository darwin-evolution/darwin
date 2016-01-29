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

import io.darwin.execution.ImplementationPreferenceType;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

public class TypesafeConfigImplementationChooserTest {

    @Test
    public void shouldReturnEvolvedImplementationForEvolvedEnabledConfigSetting() {
        ImplementationPreferenceType implementationPreferenceType = new TypesafeConfigImplementationChooser().chooseImplementation("EvolvedEnabled");

        assertThat(implementationPreferenceType).isEqualTo(ImplementationPreferenceType.EVOLVED);
    }

    @Test
    public void shouldReturnCurrentImplementationForEvolvedDisabledConfigSetting() {
        ImplementationPreferenceType implementationPreferenceType = new TypesafeConfigImplementationChooser().chooseImplementation("EvolvedDisabled");

        assertThat(implementationPreferenceType).isEqualTo(ImplementationPreferenceType.CURRENT);
    }

    @Test
    public void shouldReturnCurrentImplementationForMissingConfigSetting() {
        ImplementationPreferenceType implementationPreferenceType = new TypesafeConfigImplementationChooser().chooseImplementation("EvolvedMissing");

        assertThat(implementationPreferenceType).isEqualTo(ImplementationPreferenceType.CURRENT);
    }

}
