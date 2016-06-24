package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

// Pathway
/*
 *
 */
/*
 * @startuml
 *  class UPA extends TermRelations {
  *     + UPA( @NotNull final String id, @NotNull final String name, @NotNull final String definition, Relations relations )
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
    public UPA(@NotNull final String id, @NotNull final String name, @NotNull final String definition, final Map<String, Set<Reference>> xref, final Relations relations, final Set<Relation> isA, final Relation superPathway ) {
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
