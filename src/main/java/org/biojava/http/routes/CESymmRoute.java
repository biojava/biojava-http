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

package org.biojava.http.routes;

import org.biojava.http.BioJavaRoutes;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.StructureTools;
import org.biojava.nbio.structure.symmetry.internal.CeSymm;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handle requests for {@link BioJavaRoutes#MMCIF}
 * @author Spencer Bliven
 *
 */
public class CESymmRoute implements Route {
	public static Logger logger = LoggerFactory.getLogger(CESymmRoute.class);

	@Override
	public CeSymmResult handle(Request request, Response response) {
		String id = request.params(":id");
		if(id == null) {
			response.status(404);
			logger.error("null id");
			return null;
		}
		try {
			Structure s = StructureIO.getStructure(id);
			if(s == null) {
				response.status(404);
				logger.error("null structure for "+id);
				return null;
			}
			Atom[] ca = StructureTools.getRepresentativeAtomArray(s);
			CeSymmResult result = CeSymm.analyze(ca);
			return result;
		} catch(Exception e) {
			logger.error("Error",e);
			response.status(404);
			return null;
		}
	}

}
