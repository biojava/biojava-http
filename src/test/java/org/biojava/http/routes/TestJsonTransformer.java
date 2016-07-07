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
 * Created on Jul 7, 2016
 * Author: blivens 
 *
 */
 
package org.biojava.http.routes;

import static org.junit.Assert.*;

import java.io.IOException;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.StructureTools;
import org.biojava.nbio.structure.symmetry.internal.CESymmParameters;
import org.biojava.nbio.structure.symmetry.internal.CeSymm;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.junit.Test;

import com.google.gson.Gson;
 
public class TestJsonTransformer {

	@Test
	public void testCeSymmResult() throws IOException, StructureException {
		Structure s = StructureIO.getStructure("1HIV.A");
		Atom[] ca = StructureTools.getRepresentativeAtomArray(s);
		CESymmParameters params = new CESymmParameters();
		params.setSymmLevels(1);
		params.setOptimization(false);
		CeSymmResult result = CeSymm.analyze(ca);
		JsonTransformer trans = new JsonTransformer();
		String json = trans.render(result);

		assertNotNull(json);
		System.out.println(json);
	}

}
