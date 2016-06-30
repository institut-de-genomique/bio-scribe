package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Variant implements Iterable<Term> {

    private static final    AtomicInteger   counter     = new AtomicInteger();

    private final long  id;
    private List<Term>  children;
    private Set<String> termVariants;


    /**
     * getVariant is a function which convert a two dimensional list of terms
     * which are part of another term to a list of each possible way using 
     * Disjunctive normal form.
     * Result is wrote into variable named variantsList as this function used
     * his reference
     * @param terms two dimensional list, the second dimension tell which term is possible to find
     * @param variantsList convert terms to Disjunctive normal form. It is an inout variable.
     */
    public static void getVariant( @NotNull final List<List<Term>> terms, @NotNull List<Variant> variantsList ){
        getVariant( terms, variantsList, 0, 0 );
    }
    
    private static void getVariant( @NotNull final List<List<Term>> terms, @NotNull List<Variant> variantsList, final int line , final int column ){
        Variant variant  = null;
        if(  line < terms.size() && column < terms.get(line).size() ){
            if( variantsList.size() > 0 ){
                ArrayList<Term> r = new ArrayList<>( variantsList.get( variantsList.size() - 1 )
                                                                 .getTerms() );
                variant = new Variant( r );
                variantsList.get( variantsList.size() - 1 ).add( terms.get(line)
                                                                      .get(column) );
            }
            else{
                variant  = new Variant( );
                variantsList.add( new Variant( terms.get(line)
                                                    .get(column) ) );
            }
            if( line+1 < terms.size() )
                getVariant( terms, variantsList, line+1, 0 );
            if( line < terms.size() && column+1 < terms.get(line)
                                                       .size() ){
                variantsList.add( variant );
                getVariant( terms, variantsList, line, column+1 );
            }
        }
    }


    public Variant( ){
        id       = counter.incrementAndGet();
        children = new ArrayList<>();
    }


    public Variant( @NotNull final Term term ){
        id       = counter.incrementAndGet();
        children = new ArrayList<>(Arrays.asList(term));
    }

    public Variant( @NotNull final List<Term> termList ){
        id       = counter.incrementAndGet();
        children = termList;
    }


    public Variant( @NotNull final List<Term> termList, @NotNull Set<String> variantId  ){
        id              = counter.incrementAndGet();
        children        = termList;
        termVariants    = variantId;
    }


    public boolean hasVariantOf( @NotNull final Term term ) {
        boolean         result          = termVariants.contains( term.getId() );
        boolean         isRunning       = true;
        Iterator<Term>  iter            = children.iterator();
        Term   currentTerm = null;
        
        if( ! result ){
            
            while( isRunning ){
                
                if( iter.hasNext() ){
                    
                    currentTerm = iter.next();
                    
                    if( term instanceof TermRelations && currentTerm instanceof TermRelations && ((TermRelations)term).isVariantOf( (TermRelations) currentTerm ) ){
                        result      = true;
                        isRunning   = false;
                        termVariants.add( term.getId() );
                    }
                    
                }
                else
                    isRunning = false;
            }
        }
        
        return result;
    }


    public long getId() {
        return id;
    }


    public void add ( final int position, @NotNull final Term node ){ // maybe check if node is not a variant and raise an error if it is
        children.add(position, node);
    }


    public void add ( @NotNull final Term term ){
        children.add( term );
    }


    public void addAll ( @NotNull final List<Term> terms ){
        children.addAll( terms );
    }


    public Set<String> getTermVariants() {
        return termVariants;
    }


    public void addAll ( @NotNull final Variant variant ){
        children.addAll( variant.getTerms() );
        termVariants.addAll( variant.getTermVariants() );
    }


    public List<Term> getTerms() {
        return children;
    }


    @Override
    public Iterator<Term> iterator() {
        return children.iterator();
    }


    public Term get( final int index ){
        return children.get( index );
    }


    public boolean has( final String termId ){
        boolean                 isRunning   = true;
        boolean                 isPresent   = false;
        Iterator<Term>          iter        = children.iterator();
        Term   currentTerm = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                currentTerm = iter.next();
                if( currentTerm.getId().equals( termId ) ){
                    isRunning   = false;
                    isPresent   = true;
                }
            }
            else
                isRunning = false;
        }
        
        return isPresent;
    }


    public Term find( final String termId ){
        Term           result      = null;
        boolean                 isRunning   = true;
        Iterator<Term> iter        = children.iterator();
        Term           term        = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                term = iter.next();
                if( term.getId().equals( termId ) ){
                    isRunning   = false;
                    result      = term;
                }
            }
            else
                isRunning = false;
        }
        
        return result;
    }


    public int countUntil( final String termId ){
        int                     result      = -1;
        int                     currentIndex= 0;
        boolean                 isRunning   = true;
        Iterator<Term> iter        = children.iterator();
        Term           term        = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                term = iter.next();
                if( term.getId().equals( termId ) ){
                    isRunning   = false;
                    result      = currentIndex;
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
    public String toString(){
        StringBuilder str = new StringBuilder();
        for( Term term : children)
            str.append( term.toString() );
        return str.toString();
    }


    public int countUntilVariantOf( final Term term ) {
        int                     result      = -1;
        int                     index       = 0;
        boolean                 isSearching = true;
        Iterator<Term> iter        = children.iterator();
        Term           current     = null;
        
        while( isSearching ){
            if( iter.hasNext() ){
                current = iter.next();
                if( term instanceof TermRelations && current instanceof TermRelations && ((TermRelations)current).isVariantOf( (TermRelations) term ) ){
                    isSearching = false;
                    result      = index;
                }
                else
                    index++;
            }
            else
                isSearching = false;
        }
        return result;
    }


    public List<String> getTermId(){
        return children.stream()
                .map(Term::getId)
                .collect(Collectors.toList());
    }


    public int size(){
        return children.size();
    }


    public List<String> getTermName() {
        return children.stream()
                       .map(Term::getName)
                       .collect(Collectors.toList());
    }
}
