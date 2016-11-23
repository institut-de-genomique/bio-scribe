package fr.cea.ig.bio.model.obo;

import fr.cea.ig.bio.model.obo.unipathway.Reference;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 *
 */
/*
 * @startuml
 * abstract class Term {
 *  - id                : String
 *  - name              : String
 *  - definition        : String
 *  + Term(@NonNull final String id, @NonNull final String name, @NonNull final String definition)
 *  + getId()           : String
 *  + getName()         : String
 *  + getDefinition()   : String
 *  + toString()        : String
 * }
 * @enduml
 */
public abstract class Term {
    
    protected final String                      id;
    protected final String                      name;
    protected final String                      definition;
    protected final Map<String, Set<Reference >> xref;
    
    /**
     * @param id         Term id
     * @param name       Term name
     * @param definition Term description
     */
    public Term( final String id, final String name, final String definition ) {
        this( id, name, definition, new HashMap<>( ) );
    }
    
    /**
     * @param id         Term id
     * @param name       Term name
     * @param definition Term description
     * @param xref       Cross references id
     */
    public Term( @NonNull final String id, @NonNull final String name, @NonNull final String definition, @NonNull final Map<String, Set<Reference>> xref ) {
        this.id         = id;
        this.name       = name;
        this.definition = definition;
        this.xref       = xref;
    }
    
    public String getId( ) {
        return id;
    }
    
    public String getName( ) {
        return name;
    }
    
    public String getDefinition( ) {
        return definition;
    }
    
    public Map<String, Set<Reference>> getXref( ) {
        return xref;
    }
    
    public boolean contains( @NonNull final String ref ) {
        return xref.containsKey( ref );
    }
    
    public Set<Reference> getXref( @NonNull final String ref ) {
        return xref.get( ref );
    }
    
    @Override
    public String toString( ) {
        return "[Term]"                     + "\n" +
                "id: UPa:"  + id            + "\n" +
                "name: "    + name          + "\n" +
                "def: "     + definition;
    }
    
    public String getNameSpace( ) { //(final Class<? extends Term> cls
        String       result    = null;
        final String className = this.getClass( ).getCanonicalName( );
        
        if( "fr.cea.ig.obo.model.UCR".equals( className ) )
            result = "reaction";
        else if( "fr.cea.ig.obo.model.UER".equals( className ) )
            result = "enzymatic_reaction";
        else if( "fr.cea.ig.obo.model.ULS".equals( className ) )
            result = "linear_sub_pathway";
        else if( "fr.cea.ig.obo.model.UPA".equals( className ) )
            result = "pathway";
        else if( "fr.cea.ig.obo.model.UPC".equals( className ) )
            result = "compound";
        
        return result;
    }
}
