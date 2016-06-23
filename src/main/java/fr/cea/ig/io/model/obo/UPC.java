package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;
import java.util.Map;

// Compound
public class UPC extends Term {

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param xref cross references with others resources
     */
    public UPC(@NotNull final String id, @NotNull final String name, @NotNull final String definition, final Map<String, Reference>xref) {
        super(id, name, definition, xref);
    }

}
