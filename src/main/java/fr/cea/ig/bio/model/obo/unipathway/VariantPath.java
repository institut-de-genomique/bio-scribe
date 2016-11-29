package fr.cea.ig.bio.model.obo.unipathway;

import fr.cea.ig.bio.model.obo.Term;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class VariantPath implements Iterable<TermRelations > {
    
    private static final AtomicInteger counter = new AtomicInteger( );
    
    private final long        id;
    private       List<TermRelations>  children;
    private       Set<String> termVariants;
    
    private static Set<TermRelations> getStartTerms( @NonNull final Set<TermRelations> children ){
        return children.stream( )
                       .filter( term -> children.stream( )
                                                .noneMatch( t -> t.isBefore( term ) ) )
                       .collect( Collectors.toSet( ) );
    }


    private static Set<TermRelations> getLinkedTerms( @NonNull final TermRelations termRelations,  @NonNull final List<TermRelations> path, @NonNull final Set<TermRelations> processes){
        return processes.stream()
                        .filter( t -> path.stream().noneMatch( x -> t == x ) )
                        .filter( t -> t.isAfter( termRelations ) )
                        .collect( Collectors.toSet( ) );
    }

    private static Set<VariantPath > getVariants( @NonNull final List<TermRelations> path, @NonNull final Set<TermRelations> processes ){
        final Set<VariantPath > variantPaths = new HashSet<>(  );
        final TermRelations latest   = path.get( path.size() - 1 );
        final Set<TermRelations> nextTerms  = getLinkedTerms(latest, path, processes);
            if( nextTerms.size()  == 0 )
                variantPaths.add( new VariantPath( path ) );
            else if( nextTerms.size()  == 1 ) {
                path.addAll( nextTerms ); // add one
                variantPaths.addAll( getVariants( path, processes ) );
            }
            else{
                for( final TermRelations term : nextTerms ) {
                    final List< TermRelations > new_path = new ArrayList<>( path );
                    new_path.add( term );
                    variantPaths.addAll( getVariants( new_path, processes ) );
                }
            }
        return variantPaths;
    }

    /**
     * getVariant is a function which convert a two dimensional list of terms
     * which are part of another term to a list of each possible way using
     * Disjunctive normal form.
     * Result is wrote into variable named variantsList as this function used
     * his reference
     *
     * @param processes    list of process
     * @return             variants list of process
     */
    @NonNull
    public static Set<VariantPath > getVariant( @NonNull final Set< TermRelations > processes ) {
        final Set<TermRelations>        startTerms  = getStartTerms( processes );
        final Set<List<TermRelations>>  paths       = startTerms.stream()
                                                                .map( t-> new ArrayList<>( Collections.singletonList( t ) ) )
                                                                .collect( Collectors.toSet( ) );
        final Set<VariantPath > variantPaths = new HashSet<>(  );
        for( final List<TermRelations> path : paths ){
            variantPaths.addAll( getVariants( path, processes ) );
        }
        return variantPaths;
    }

    @NonNull
    public static Set<VariantPath > getVariantFrom( @NonNull final TermRelations termRelations ) {
        return getVariant( termRelations.getChildren( )
                                        .stream( )
                                        .filter( t -> TermRelations.class.isAssignableFrom( t.getClass( ) ) )
                                        .filter( t -> ! (t instanceof UCR ) )
                                        .map( t -> ( TermRelations ) t )
                                        .collect( Collectors.toSet( ) ) );
    }


//    public static void getVariant( @NonNull final List<List<Term>> terms, @NonNull final List<VariantPath> variantsList ) {
//        //Should be: terms.size() > 0 && terms.get( 0 ).size() > 0
//        final VariantPath variant = new VariantPath( );
//        if( terms.size() > 0 && terms.get( 0 ).size() > 0 )
//            getVariant( terms, variantsList, variant, 0, 0 );
//    }
//
//    private static void getVariant( @NonNull final List<List<Term>> terms, @NonNull final List<VariantPath> variantsList, @NonNull final VariantPath variant, final int line, final int column ) {
//        if( terms.get( line ).size() > column +1 ){
//            final Term  next            = terms.get( line ).get( column +1 );
//            final Term  previous        = variant.get( variant.size() - 1 );
//            if( next instanceof TermRelations && previous instanceof TermRelations) {
//                if( ( ( TermRelations ) next ).isAfter( ( TermRelations ) previous ) ) {
//                    final VariantPath variant2 = new VariantPath( variant.children );
//                    getVariant( terms, variantsList, variant2, line, column + 1 );
//                }
//                else
//                    getVariant( terms, variantsList, variant, line, column + 1 );
//            }
//            else
//                getVariant( terms, variantsList, variant, line, column + 1 );
//        }
//        if( line == 0 )
//            variant.add( terms.get( line ).get( column ) );
//        else{
//            final Term  next            = terms.get( line ).get( column );
//            final Term  previous        = variant.get( variant.size() - 1 );
//            if( next instanceof TermRelations && previous instanceof TermRelations) {
//                if( ((TermRelations) next).isAfter( (TermRelations) previous ) )
//                    variant.add(next);
//            }
//        }
//        if( terms.size() > line +1 ){
//            getVariant( terms, variantsList, variant, line + 1, 0  );
//        }
//        else if( variantsList.size() == 0 )
//            variantsList.add( variant );
//        else if(  variantsList.get( variantsList.size() -1 ) != variant )
//            variantsList.add( variant );
//    }
    
    
    public VariantPath( ) {
        id          = counter.incrementAndGet( );
        children    = new ArrayList<>( );
    }
    
    
    public VariantPath( @NonNull final TermRelations term ) {
        id          = counter.incrementAndGet( );
        children    = new ArrayList<>( Arrays.asList( term ) );
    }
    
    public VariantPath( @NonNull final List<TermRelations> termList ) {
        id          = counter.incrementAndGet( );
        children    = new ArrayList<>( termList );
    }
    
    
    public VariantPath( @NonNull final List<TermRelations> termList, @NonNull Set<String> variantId ) {
        id              = counter.incrementAndGet( );
        children        = new ArrayList<>( termList );
        termVariants    = variantId;
    }

    public long getId( ) {
        return id;
    }
    
    
    public void add( final int position, @NonNull final TermRelations node ) { // maybe check if node is not a variant and raise an error if it is
        children.add( position, node );
    }
    
    
    public void add( @NonNull final TermRelations term ) {
        children.add( term );
    }
    
    
    public void addAll( @NonNull final List<TermRelations> terms ) {
        children.addAll( terms );
    }
    
    
    public Set<String> getTermVariants( ) {
        return termVariants;
    }
    
    
    public void addAll( @NonNull final VariantPath variantPath ) {
        children.addAll( variantPath.getTerms( ) );
        termVariants.addAll( variantPath.getTermVariants( ) );
    }
    
    
    public List<TermRelations> getTerms( ) {
        return children;
    }
    
    
    @Override
    public Iterator<TermRelations> iterator( ) {
        return children.iterator( );
    }
    
    
    public Term get( final int index ) {
        return children.get( index );
    }
    
    
    public boolean has( final String termId ) {
        boolean        isRunning   = true;
        boolean        isPresent   = false;
        Iterator<TermRelations> iter        = children.iterator( );
        Term           currentTerm = null;
        
        while( isRunning ) {
            if( iter.hasNext( ) ) {
                currentTerm = iter.next( );
                if( currentTerm.getId( ).equals( termId ) ) {
                    isRunning = false;
                    isPresent = true;
                }
            }
            else
                isRunning = false;
        }
        
        return isPresent;
    }
    
    
    public Term find( final String termId ) {
        Term           result    = null;
        boolean        isRunning = true;
        Iterator<TermRelations> iter      = children.iterator( );
        Term           term      = null;
        
        while( isRunning ) {
            if( iter.hasNext( ) ) {
                term = iter.next( );
                if( term.getId( ).equals( termId ) ) {
                    isRunning = false;
                    result = term;
                }
            }
            else
                isRunning = false;
        }
        
        return result;
    }
    
    
    public int countUntil( final String termId ) {
        int            result       = -1;
        int            currentIndex = 0;
        boolean        isRunning    = true;
        Iterator<TermRelations> iter         = children.iterator( );
        Term           term         = null;
        
        while( isRunning ) {
            if( iter.hasNext( ) ) {
                term = iter.next( );
                if( term.getId( ).equals( termId ) ) {
                    isRunning = false;
                    result = currentIndex;
                }
                else
                    currentIndex++;
            }
            else
                isRunning = false;
        }
        
        return result;
    }
    
    
    @Override
    public String toString( ) {
        StringBuilder str = new StringBuilder( );
        for( Term term : children )
            str.append( term.toString( ) );
        return str.toString( );
    }
    

    @Deprecated
    public int countUntilVariantOf( final Term term ) {
        int            result      = -1;
        int            index       = 0;
        boolean        isSearching = true;
        Iterator<TermRelations> iter        = children.iterator( );
        Term           current     = null;
        
        while( isSearching ) {
            if( iter.hasNext( ) ) {
                current = iter.next( );
                if( term instanceof TermRelations && current instanceof TermRelations && ( ( TermRelations ) current ).isVariantOf( ( TermRelations ) term ) ) {
                    isSearching = false;
                    result = index;
                }
                else
                    index++;
            }
            else
                isSearching = false;
        }
        return result;
    }
    
    
    public List<String> getTermId( ) {
        return children.stream( )
                       .map( Term::getId )
                       .collect( Collectors.toList( ) );
    }
    
    
    public int size( ) {
        return children.size( );
    }
    
    
    public List<String> getTermName( ) {
        return children.stream( )
                       .map( Term::getName )
                       .collect( Collectors.toList( ) );
    }
}
