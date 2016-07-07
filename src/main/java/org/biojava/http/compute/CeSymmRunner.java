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
 
package org.biojava.http.compute;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.symmetry.internal.CESymmParameters;
import org.biojava.nbio.structure.symmetry.internal.CeSymm;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.biojava.nbio.structure.symmetry.utils.SymmetryTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class CeSymmRunner implements Callable<CeSymmResult> {
	private static final Logger logger = LoggerFactory.getLogger(CeSymmRunner.class);

	private String name;
	private CESymmParameters params;
	private AtomCache cache;

	public CeSymmRunner(String name, CESymmParameters params,
			AtomCache atomCache) {
		super();
		this.name = name;
		this.params = params;
		this.cache = atomCache;
	}

	@Override
	public CeSymmResult call() {

		try {
			// Obtain the structure representation
			Structure structure = null;
			try {
				structure = cache.getStructure(name);
			} catch (IOException | StructureException e) {
				logger.error("Could not load Structure " + name, e);
				return null;
			}

			Atom[] atoms = SymmetryTools.getRepresentativeAtoms(structure);

			// Run the symmetry analysis
			CeSymmResult result = CeSymm.analyze(atoms, params);
			return result;
		} catch (Exception e) {
			logger.error("Could not complete job: " + name, e);
			return null;
		} finally {
			logger.info("Finished job: " + name);
		}
	}

}