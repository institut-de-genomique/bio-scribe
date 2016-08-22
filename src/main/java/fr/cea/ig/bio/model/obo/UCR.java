package fr.cea.ig.bio.model.obo;


import java.util.Map;
import java.util.Set;

// Chemical Reaction
/*
 *
 */
/*
 * @startuml
 *  class UCR extends TermRelations {
  *     + UCR( @NonNull final String id, @NonNull final String name, @NonNull final String definition, Relations relations )
 *  }
 * @enduml
 */
public class UCR extends TermRelations {
    
    /**
     * @param id         Id of this aggregated relations
     * @param name       name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     * @param xref       cross references with others resources
     */
    public UCR( final String id, final String name, final String definition, final Map<String, Set<Reference>> xref, Relations relations ) {
        super( id, name, definition, xref, relations );
    }
    
}
