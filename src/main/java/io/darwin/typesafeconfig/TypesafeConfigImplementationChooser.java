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
