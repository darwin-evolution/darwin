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
