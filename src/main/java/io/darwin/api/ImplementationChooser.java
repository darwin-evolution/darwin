package io.darwin.api;

import io.darwin.execution.ImplementationPreferenceType;

public abstract class ImplementationChooser {

    public abstract ImplementationPreferenceType chooseImplementation(String evolutionName);

}
