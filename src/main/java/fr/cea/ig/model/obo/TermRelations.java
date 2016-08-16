package fr.cea.ig.model.obo;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 *
 */
/*
 * @startuml
 *  class TermRelations extends Term {
 *      * relations     : Relations
 *      * children        : List<List<Term>>
 *      + TermRelations( @NonNull final String id, @NonNull final String name, @NonNull final String definition, Relations relations )
 *      + TermRelations( @NonNull final String id, @NonNull final String name, @NonNull final String definition )
 *      + getRelations()                                : Relations
 *      + getRelation( final String type )              : Set<Relation>
 *      + isPartOf( @NonNull final String id )          : boolean
 *      + hasInputCompound(@NonNull final String id )   : boolean
 *      + hasInputCompound(@NonNull final Term term )   : boolean
 *      + hasOutputCompound(@NonNull final String id )  : boolean
 *      + hasOutputCompound(@NonNull final Term term )  : boolean
 *      + hasAtLeastOneCommonOutputCompound( @NonNull final TermRelations term ) : boolean
 *      + hasAtLeastOneCommonInputCompound( @NonNull final TermRelations term ) : boolean
 *      + isVariantOf( @NonNull final TermRelations term )  : boolean
 *      + isAfter( @NonNull final TermRelations term )  : boolean
 *      + isBefore( @NonNull final TermRelations term ) : boolean
 *      + isLinked( @NonNull final TermRelations term ) : boolean
 *      + add( @NonNull final Term term )               : void
 *      + addAll( @NonNull final List<List<Term>> terms )  : void
 *      + get( final int index )                        : List<Term>
 *      + iterator()                                    : Iterator<List<Term>>
 *      + getChildren()                                   : List<List<Term>>
 *      + toString()                                    : String
 *  }
 * @enduml
 */
public class TermRelations extends Term {
    
    protected final Relations relations;
    
    
    protected List<List<Term>> children;
    
    
    /**
     * @param id         Id of this aggregated relations
     * @param name       name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     * @param xref       cross references with others resources
     */
    public TermRelations( final String id, final String name, final String definition, final Map<String, Set<Reference>> xref, Relations relations ) {
        super( id, name, definition, xref );
        this.relations = relations;
        this.children = new ArrayList<>( );
    }
    
    /**
     * @param id         Id of this aggregated relations
     * @param name       name of this aggregated relations
     * @param definition description  aggregated relations
     */
    public TermRelations( final String id, final String name, final String definition ) {
        super( id, name, definition );
        this.relations = new Relations( );
        this.children = new ArrayList<>( );
    }
    
    
    public Relations getRelations( ) {
        return relations;
    }
    
    
    public Set<Relation> getRelation( final String type ) {
        final String tokenInput  = "has_input_compound";
        final String tokenOutput = "has_output_compound";
        final String tokenPartOf = "part_of";
        
        Set<Relation> relation = null;
        
        if( tokenInput.equals( type ) )
            relation = relations.getInputCompound( );
        else if( tokenOutput.equals( type ) )
            relation = relations.getOutputCompound( );
        else if( tokenPartOf.equals( type ) )
            relation = relations.getPartOf( );
        return relation;
    }
    
    
    @Override
    public String toString( ) {
        return super.toString( ) + "\n" + relations.toString( );
    }
    
    public boolean isPartOf( @NonNull final String id ) {
        return relations.isPartOf( id );
    }
    
    
    public boolean isPartOf( @NonNull final Term term ) {
        return relations.isPartOf( term.getId( ) );
    }
    
    
    public boolean hasInputCompound( @NonNull final String id ) {
        return relations.hasInputCompound( id );
    }
    
    
    public boolean hasInputCompound( @NonNull final Term term ) {
        return relations.hasInputCompound( term.getId( ) );
    }
    
    
    public boolean hasOutputCompound( @NonNull final String id ) {
        return relations.hasOutputCompound( id );
    }
    
    
    public boolean hasOutputCompound( @NonNull final Term term ) {
        return relations.hasOutputCompound( term.getId( ) );
    }
    
    
    public boolean hasAtLeastOneCommonOutputCompound( @NonNull final TermRelations term ) {
        boolean                  result      = false;
        boolean                  isSearching = true;
        final Set<Relation>      relation    = relations.getOutputCompound( );
        final Iterator<Relation> iter        = relation.iterator( );
        Relation                 rel         = null;
        while( isSearching ) {
            if( !iter.hasNext( ) )
                isSearching = false;
            else {
                rel = iter.next( );
                final Scanner in = new Scanner( rel.getIdRight( ) ).useDelimiter( "[^0-9]+" ); // filter h20 and other basic molecules
                if( in.nextInt( ) > 30 && term.hasOutputCompound( rel.getIdRight( ) ) ) {
                    isSearching = false;
                    result = true;
                }
            }
        }
        return result;
        
    }
    
    
    public boolean hasAtLeastOneCommonInputCompound( @NonNull final TermRelations term ) {
        boolean                  result      = false;
        boolean                  isSearching = true;
        final Set<Relation>      relation    = relations.getInputCompound( );
        final Iterator<Relation> iter        = relation.iterator( );
        Relation                 rel         = null;
        while( isSearching ) {
            if( !iter.hasNext( ) )
                isSearching = false;
            else {
                rel = iter.next( );
                final Scanner in = new Scanner( rel.getIdRight( ) ).useDelimiter( "[^0-9]+" ); // filter h20 and other basic molecules
                if( in.nextInt( ) > 30 && term.hasInputCompound( rel.getIdRight( ) ) ) {
                    isSearching = false;
                    result = true;
                }
            }
        }
        return result;
        
    }
    
    
    public boolean isVariantOf( @NonNull final TermRelations term ) {
        return hasAtLeastOneCommonOutputCompound( term ) && hasAtLeastOneCommonInputCompound( term );
    }
    
    
    public boolean isAfter( @NonNull final TermRelations term ) {
        boolean            result      = false;
        boolean            isSearching = true;
        Iterator<Relation> iter        = relations.getOutputCompound( ).iterator( );
        Relation           output      = null;
        while( isSearching ) {
            if( iter.hasNext( ) ) {
                output = iter.next( );
                if( term.hasInputCompound( output.getIdLeft( ) ) ) {
                    result = true;
                    isSearching = false;
                }
            }
            else
                isSearching = false;
        }
        return result;
    }
    
    
    public boolean isBefore( @NonNull final TermRelations term ) {
        boolean            result      = false;
        boolean            isSearching = true;
        Iterator<Relation> iter        = relations.getInputCompound( ).iterator( );
        Relation           input       = null;
        while( isSearching ) {
            if( iter.hasNext( ) ) {
                input = iter.next( );
                if( term.hasOutputCompound( input.getIdLeft( ) ) ) {
                    result = true;
                    isSearching = false;
                }
            }
            else
                isSearching = false;
        }
        return result;
    }
    
    
    public boolean isLinked( @NonNull final TermRelations term ) {
        return ( isAfter( term ) || isBefore( term ) );
    }
    
    
    public void add( @NonNull final Term term ) {
        boolean              isSearching = true;
        Iterator<List<Term>> iter        = children.iterator( );
        List<Term>           currentList = null;
        Term                 currentTerm = null;
        
        if( children.size( ) > 0 && children.get( 0 ).size( ) > 0 && !( children.get( 0 ).get( 0 ) instanceof TermRelations ) ) {
            children.add( new ArrayList<Term>( Arrays.asList( term ) ) );
            isSearching = false;
        }
        
        while( isSearching ) {
            if( iter.hasNext( ) ) {
                currentList = iter.next( );
                currentTerm = currentList.get( 0 );
                if( term instanceof TermRelations && ( ( TermRelations ) currentTerm ).isVariantOf( ( TermRelations ) term ) ) {
                    currentList.add( term );
                    isSearching = false;
                }
            }
            else {
                isSearching = false;
                children.add( new ArrayList<Term>( Arrays.asList( term ) ) );
            }
        }
        
    }
    
    
    public void addAll( @NonNull final List<List<Term>> terms ) {
        children.addAll( terms );
    }
    
    public List<Term> get( final int index ) {
        return children.get( index );
    }
    
    
    public Iterator<List<Term>> iterator( ) {
        return children.iterator( );
    }
    
    
    public List<List<Term>> getChildren( ) {
        return children;
    }
    
    
}
