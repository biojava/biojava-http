/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Jul 6, 2016
 * Author: blivens 
 *
 */

package org.biojava.http.compute;

import java.util.List;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.align.multiple.MultipleAlignment;
import org.biojava.nbio.structure.align.multiple.util.MultipleAlignmentDisplay;
import org.biojava.nbio.structure.align.multiple.util.MultipleAlignmentTools;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ResponseTransformer;

public class CeSymmPDBTransformer implements ResponseTransformer {
	public static Logger logger = LoggerFactory.getLogger(CeSymmPDBTransformer.class);

	public CeSymmPDBTransformer() {
		super();
	}
	
	@Override
	public String render(Object model) {
		CeSymmResult result = (CeSymmResult) model;
		
		try {
			Structure artificial = resultToStructure(result);
			return artificial.toPDB();
		} catch (StructureException e) {
			logger.error("error rendering CeSymm PDB",e);
			return null;
		}

	}

	public static Structure resultToStructure(CeSymmResult result)
			throws StructureException {
		MultipleAlignment multAln = result.getMultipleAlignment();
		List<Atom[]> rotatedAtoms = MultipleAlignmentDisplay.getRotatedAtoms(multAln );
		Structure artificial = MultipleAlignmentTools.toMultimodelStructure(multAln, rotatedAtoms);
		return artificial;
	}

}
