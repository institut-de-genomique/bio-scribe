/*
 * Copyright LABGeM 12/08/15
 *
 * author: Jonathan MERCIER
 *
 * This software is a computer program whose purpose is to annotate a complete genome.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package fr.cea.ig.bio.model.genome_properties;

import lombok.NonNull;


public class GenomePropertyBuilder implements TermBuilder<GenomeProperty>, BuildTitle, BuildCategory {
    private int    threshold;
    private String definition;
    private String accession;
    private String category;
    private String name;
    private String id;
    private String title;
    
    @NonNull
    public GenomePropertyBuilder setThreshold( final int threshold ) {
        this.threshold = threshold;
        return this;
    }
    
    @NonNull
    public GenomePropertyBuilder setDefinition( @NonNull final String definition ) {
        this.definition = definition;
        return this;
    }
    
    @NonNull
    public GenomePropertyBuilder setAccession( @NonNull final String accession ) {
        this.accession = accession;
        return this;
    }
    
    @NonNull
    @Override
    public GenomePropertyBuilder setCategory( @NonNull final String category ) {
        this.category = category;
        return this;
    }
    
    @NonNull
    @Override
    public GenomePropertyBuilder setName( @NonNull final String name ) {
        this.name = name;
        return this;
    }
    
    @NonNull
    @Override
    public GenomePropertyBuilder setId( @NonNull final String id ) {
        this.id = id;
        return this;
    }
    
    @NonNull
    @Override
    public GenomePropertyBuilder setTitle( @NonNull final String title ) {
        this.title = title;
        return this;
    }
    
    @NonNull
    @Override
    public GenomeProperty create( ) {
        return new GenomePropertyImpl( threshold, definition, accession, category, name, id, title );
    }
    
    @NonNull
    @Override
    public String getName( ) {
        return name;
    }
}