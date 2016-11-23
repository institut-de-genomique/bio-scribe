package fr.cea.ig.bio.scribe;

import fr.cea.ig.bio.model.obo.unipathway.Cardinality;
import fr.cea.ig.bio.model.obo.unipathway.Relation;
import fr.cea.ig.bio.model.obo.Term;
import fr.cea.ig.bio.model.obo.unipathway.ULS;
import fr.cea.ig.bio.model.obo.unipathway.UPA;
import fr.cea.ig.bio.model.obo.unipathway.Variant;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UniPathwayReaderTest extends TestCase {
    
    private static URL file = Thread.currentThread( ).getContextClassLoader( )
                                    .getResource( "unipath.obo" );
    
    private UniPathwayOboReader uniPathwayOboReader;
    
    @Before
    public void setUp( ) {
        try {
            uniPathwayOboReader = new UniPathwayOboReader( file.getPath( ) );
        }
        catch( Exception e ) {
            e.printStackTrace( );
        }
    }
    
    @Test
    public void testGetTerm( ) {
        Term term = uniPathwayOboReader.getTerm( "UPA00033" );
        assertEquals( "UPA00033", term.getId( ) );
    }
    
    @Test
    public void testULSVariant( ) {
        UPA              term     = ( UPA ) uniPathwayOboReader.getTerm( "UPA00033" );
        List<List<Term>> children = term.getChildren( );
        
        assertEquals( 2, children.size( ) );
        assertEquals( "ULS00013", children.get( 1 ).get( 0 ).getId( ) );
        assertEquals( "ULS00014", children.get( 1 ).get( 1 ).getId( ) );
        
        ULS uls12 = ( ULS ) uniPathwayOboReader.getTerm( "ULS00012" );
        ULS uls13 = ( ULS ) uniPathwayOboReader.getTerm( "ULS00013" );
        ULS uls14 = ( ULS ) uniPathwayOboReader.getTerm( "ULS00014" );
        
        List<Variant> variants = new ArrayList<>( );
        Variant.getVariant( term.getChildren( ), variants );
        assertEquals( 2, variants.size( ) );
        assertEquals( 2, variants.get( 0 ).size( ) );
        assertEquals( uls12, variants.get( 0 ).get( 0 ) );
        assertEquals( uls13, variants.get( 0 ).get( 1 ) );
        assertEquals( 2, variants.get( 1 ).size( ) );
        assertEquals( uls12, variants.get( 1 ).get( 0 ) );
        assertEquals( uls14, variants.get( 1 ).get( 1 ) );
    }
    
    
    @Test
    public void testRelation( ) {
        UPA                term     = ( UPA ) uniPathwayOboReader.getTerm( "UPA00033" );
        Relation           relation = new Relation( "is_a", "UPA00404", "L-lysine biosynthesis" );
        Iterator<Relation> iter     = term.getIsA( ).iterator( );
        assertTrue( iter.hasNext( ) );
        assertEquals( iter.next( ).toString( ), relation.toString( ) );
    }
    
    @Test
    public void testCardinality( ) {
        ULS           term      = ( ULS ) uniPathwayOboReader.getTerm( "ULS00012" );
        Relation      relation1 = new Relation( "has_input_compound", "UPC00026", new Cardinality( "1" ), "UPC00026", "2-oxoglutarate" );
        Relation      relation2 = new Relation( "has_output_compound", "UPC00956", new Cardinality( "1" ), "UPC00956", "L-alpha-aminoadipate" );
        Relation      relation3 = new Relation( "part_of", "UPA00033", new Cardinality( "1" ) );
        Set<Relation> sr1       = term.getRelation( "has_input_compound" );
        Relation[]    arr1      = sr1.toArray( new Relation[ sr1.size( ) ] );
        Set<Relation> sr2       = term.getRelation( "has_output_compound" );
        Relation[]    arr2      = sr2.toArray( new Relation[ sr1.size( ) ] );
        Set<Relation> sr3       = term.getRelation( "part_of" );
        Relation[]    arr3      = sr3.toArray( new Relation[ sr1.size( ) ] );
        assertEquals( "ULS00012", term.getId( ) );
        assertEquals( arr1[ 0 ].equals( relation1 ), true );
        assertEquals( arr2[ 0 ].equals( relation2 ), true );
        assertEquals( arr3[ 0 ].equals( relation3 ), true );
        
    }
}
