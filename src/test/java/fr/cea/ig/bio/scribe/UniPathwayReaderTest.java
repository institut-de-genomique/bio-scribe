package fr.cea.ig.bio.scribe;

import fr.cea.ig.bio.model.obo.unipathway.Cardinality;
import fr.cea.ig.bio.model.obo.unipathway.Relation;
import fr.cea.ig.bio.model.obo.Term;
import fr.cea.ig.bio.model.obo.unipathway.UER;
import fr.cea.ig.bio.model.obo.unipathway.ULS;
import fr.cea.ig.bio.model.obo.unipathway.UPA;
import fr.cea.ig.bio.model.obo.unipathway.VariantPath;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UniPathwayReaderTest {
    
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
    public void testULSVariant1( ) {
        final UPA       upa33    = ( UPA ) uniPathwayOboReader.getTerm( "UPA00033" );
        final Set<Term> children = upa33.getChildren( );

        assertEquals( 3, children.size( ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00012" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00013" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00014" ) ) );

        final ULS uls12 = ( ULS ) uniPathwayOboReader.getTerm( "ULS00012" );
        final ULS uls13 = ( ULS ) uniPathwayOboReader.getTerm( "ULS00013" );
        final ULS uls14 = ( ULS ) uniPathwayOboReader.getTerm( "ULS00014" );

        assertNotNull( uls12 );
        assertNotNull( uls13 );
        assertNotNull( uls14 );

        final Set<VariantPath > variantPaths = VariantPath.getVariantFrom( upa33 );
        assertEquals( 2, variantPaths.size( ) );

        final VariantPath v1 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00013" ) )
                                           .findFirst().orElse( null );
        final VariantPath v2 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00014" ) )
                                           .findFirst().orElse( null );
        assertNotNull( v1 );
        assertTrue( v1.has("ULS00012"  ) );
        assertEquals( 2, v1.size() );
        assertNotNull( v2 );
        assertTrue( v2.has("ULS00012"  ) );
        assertEquals( 2, v2.size() );
    }

    @Test
    public void testULSVariant2( ) {
        final UPA           upa34       = ( UPA ) uniPathwayOboReader.getTerm( "UPA00034" );
        final Set< Term >   children    = upa34.getChildren( );

        assertEquals( 7, children.size( ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00006" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00007" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00008" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00009" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00010" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00011" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00227" ) ) );

        final Set<VariantPath > variantPaths = VariantPath.getVariantFrom( upa34 );
        assertEquals( 4, variantPaths.size( ) );

        final VariantPath v1 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00010" ) )
                                           .findFirst().orElse( null );
        final VariantPath v2 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00008" ) )
                                           .findFirst().orElse( null );
        final VariantPath v3 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00007" ) )
                                           .findFirst().orElse( null );
        final VariantPath v4 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00227" ) )
                                           .findFirst().orElse( null );
        assertNotNull( v1 );
        assertTrue( v1.has("ULS00006"  ) );
        assertTrue( v1.has("ULS00010"  ) );
        assertTrue( v1.has("ULS00011"  ) );
        assertEquals( 3, v1.size() );

        assertNotNull( v2 );
        assertTrue( v2.has("ULS00006"  ) );
        assertTrue( v2.has("ULS00008"  ) );
        assertTrue( v2.has("ULS00009"  ) );
        assertTrue( v2.has("ULS00011"  ) );
        assertEquals( 4, v2.size() );

        assertNotNull( v3 );
        assertTrue( v3.has("ULS00006"  ) );
        assertTrue( v3.has("ULS00007"  ) );
        assertTrue( v3.has("ULS00009"  ) );
        assertTrue( v3.has("ULS00011"  ) );
        assertEquals( 4, v3.size() );

        assertNotNull( v4 );
        assertTrue( v4.has("ULS00006"  ) );
        assertTrue( v4.has("ULS00227"  ) );
        assertTrue( v4.has("ULS00009"  ) );
        assertTrue( v4.has("ULS00011"  ) );
        assertEquals( 4, v4.size() );

    }

    @Test
    public void testULSVariant3( ) {
        final UPA       upa122   = ( UPA ) uniPathwayOboReader.getTerm( "UPA00122" );
        final Set<Term> children = upa122.getChildren( );

        assertEquals( 7, children.size( ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00483" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00484" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00485" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00486" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00487" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00149" ) ) );
        assertTrue( children.stream().anyMatch( t -> t.getId().equals( "ULS00150" ) ) );

        final Set<VariantPath > variantPaths = VariantPath.getVariantFrom( upa122 );
        assertEquals( 6, variantPaths.size( ) );

        final VariantPath v1 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00484" ) && v.has( "ULS00485" ) )
                                           .findFirst().orElse( null );
        final VariantPath v2 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00483" ) && v.has( "ULS00485" ) )
                                           .findFirst().orElse( null );
        final VariantPath v3 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00150" ) && v.has( "ULS00487" ) )
                                           .findFirst().orElse( null );
        final VariantPath v4 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00150" ) && v.has( "ULS00486" ) )
                                           .findFirst().orElse( null );
        final VariantPath v5 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00149" ) && v.has( "ULS00487" ) )
                                           .findFirst().orElse( null );
        final VariantPath v6 = variantPaths.stream( )
                                           .filter( v -> v.has( "ULS00149" ) && v.has( "ULS00486" ) )
                                           .findFirst().orElse( null );
        assertNotNull( v1 );
        assertEquals( 2, v1.size() );

        assertNotNull( v2 );
        assertEquals( 2, v2.size() );

        assertNotNull( v3 );
        assertEquals( 2, v3.size() );

        assertNotNull( v4 );
        assertEquals( 2, v4.size() );

        assertNotNull( v5 );
        assertEquals( 2, v5.size() );

        assertNotNull( v6 );
        assertEquals( 2, v6.size() );

    }

    @Test
    public void testULSVariant4( ) {
        final UPA               upa51       = ( UPA ) uniPathwayOboReader.getTerm( "UPA00051" );
        final Set<Term>         children    = upa51.getChildren( );
        final Set< VariantPath > variantPaths = VariantPath.getVariantFrom( upa51 );

        assertEquals(11, children.size( ) );
        assertEquals( 12, variantPaths.size( ) );
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
        Relation      relation1 = new Relation( "has_input_compound", "UPC00026", new Cardinality( "1", "", "", true, false ), "UPC00026", "2-oxoglutarate" );
        Relation      relation2 = new Relation( "has_output_compound", "UPC00956", new Cardinality( "1", "", "", true, false ), "UPC00956", "L-alpha-aminoadipate" );
        Relation      relation3 = new Relation( "part_of", "UPA00033", new Cardinality( "1", "", "", true, false ) );
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

    @Test
    public void testAlternate(){
        final UER uer63 = ( UER ) uniPathwayOboReader.getTerm( "UER00063" );
        assertTrue( uer63.hasAlternate( "UER00949" ) );
        assertTrue( uer63.hasAlternate( "UER00950" ) );
    }
}
