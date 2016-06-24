package fr.cea.ig.io.model.obo;


import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

// Chemical Reaction
/*
 *
 */
/*
 * @startuml
 *  class UCR extends TermRelations {
  *     + UCR( @NotNull final String id, @NotNull final String name, @NotNull final String definition, Relations relations )
 *  }
 * @enduml
 */
public class UCR extends TermRelations {
    
    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     * @param xref cross references with others resources
     */
    public UCR(@NotNull final String id, @NotNull final String name, @NotNull final String definition, final Map<String, Set<Reference>> xref, Relations relations ) {
        super(id, name, definition, xref, relations);
    }

}
