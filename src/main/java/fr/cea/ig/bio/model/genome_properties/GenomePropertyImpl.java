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


public class GenomePropertyImpl implements GenomeProperty {
    private final int    threshold;
    @NonNull
    private final String definition;
    @NonNull
    private final String accession;
    @NonNull
    private final String category;
    @NonNull
    private final String name;
    @NonNull
    private final String id;
    @NonNull
    private final String title;
    
    public GenomePropertyImpl( final int threshold, @NonNull final String definition, @NonNull final String accession, @NonNull final String category, @NonNull final String name, @NonNull final String id, @NonNull final String title ) {
        this.threshold = threshold;
        this.definition = definition;
        this.accession = accession;
        this.category = category;
        this.name = name;
        this.id = id;
        this.title = title;
    }
    
    @NonNull
    @Override
    public int getThreshold( ) {
        return threshold;
    }
    
    @NonNull
    @Override
    public String getDefinition( ) {
        return definition;
    }
    
    @NonNull
    @Override
    public String getAccession( ) {
        return accession;
    }
    
    @NonNull
    @Override
    public String getCategory( ) {
        return category;
    }
    
    @NonNull
    @Override
    public String getName( ) {
        return name;
    }
    
    @NonNull
    @Override
    public String getId( ) {
        return id;
    }
    
    @NonNull
    @Override
    public String getTitle( ) {
        return title;
    }
}
