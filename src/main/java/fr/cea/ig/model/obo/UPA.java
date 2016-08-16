package fr.cea.ig.model.obo;


import lombok.NonNull;

import java.util.Map;
import java.util.Set;

// Pathway
/*
 *
 */
/*
 * @startuml
 *  class UPA extends TermRelations {
  *     + UPA( @NonNull final String id, @NonNull final String name, @NonNull final String definition, Relations relations )
 *  }
 * @enduml
 */
public class UPA extends TermRelations {
    private Set<Relation> isA;
    private Relation superPathway;

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     * @param xref cross references with others resources
     * @param isA pathway type
     * @param superPathway Relation to a super pathway
     */
    public UPA( final String id, final String name, final String definition, final Map<String, Set<Reference>> xref, final Relations relations, final Set<Relation> isA, final Relation superPathway) {
        super(id, name, definition, xref, relations);
        this.isA            = isA;
        this.superPathway   = superPathway;
    }

    public Set<Relation> getIsA() {
        return isA;
    }
    public Relation getSuperPathway() {
        return superPathway;
    }
}
