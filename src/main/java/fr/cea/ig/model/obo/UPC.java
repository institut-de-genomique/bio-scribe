package fr.cea.ig.model.obo;

import lombok.NonNull;

import java.util.Map;
import java.util.Set;

// Compound
public class UPC extends Term {
    
    /**
     * @param id         Id of this aggregated relations
     * @param name       name of this aggregated relations
     * @param definition description  aggregated relations
     * @param xref       cross references with others resources
     */
    public UPC( final String id, final String name, final String definition, final Map<String, Set<Reference>> xref ) {
        super( id, name, definition, xref );
    }
    
}
