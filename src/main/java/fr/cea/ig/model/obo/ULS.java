package fr.cea.ig.model.obo;

import lombok.NonNull;

import java.util.Map;
import java.util.Set;

// Linear Sub-pathway
/*
 *
 */
/*
 * @startuml
 *  class ULS extends TermRelations {
  *     + ULS( @NonNull final String id, @NonNull final String name, @NonNull final String definition, Relations relations )
 *  }
 * @enduml
 */
public class ULS extends TermRelations {

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param xref cross references with others resources
     * @param relations  List of Relation
     */
    public ULS( final String id, final String name, final String definition, final Map<String, Set<Reference>> xref, Relations relations) {
        super(id, name, definition, xref, relations );
    }
}
