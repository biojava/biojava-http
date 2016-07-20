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
 * Created on Jul 20, 2016
 * Author: blivens 
 *
 */
 
package org.biojava.http.compute;

import org.biojava.nbio.structure.align.multiple.MultipleAlignment;
import org.biojava.nbio.structure.align.multiple.util.MultipleAlignmentWriter;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;

import spark.ResponseTransformer;
 
public class CeSymmTSVTransformer implements ResponseTransformer {
	@Override
	public String render(Object resultObj) throws Exception {
		CeSymmResult result = (CeSymmResult) resultObj;
		MultipleAlignment alignment = result.getMultipleAlignment();
		if (alignment != null)
			return MultipleAlignmentWriter.toAlignedResidues(alignment);
		else {
			// No alignment; just write header
			return String.format("#Struct1:\t%s%n", result.getStructureId());
		}
	}

}
