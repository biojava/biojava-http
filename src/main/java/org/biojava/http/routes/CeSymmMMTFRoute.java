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

import java.io.IOException;

import org.biojava.http.BioJavaRoutes;
import org.biojava.http.compute.CeSymmPDBTransformer;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * Handle requests for {@link BioJavaRoutes#CESYMM_MMTF}
 * @author Spencer Bliven
 *
 */
public class CeSymmMMTFRoute extends CeSymmResultRoute {
	public static Logger logger = LoggerFactory.getLogger(CeSymmMMTFRoute.class);

	@Override
	public CeSymmResult handle(Request request, Response response) {
		CeSymmResult result = super.handle(request, response);
		if(result == null) {
			// Error should already be set
			return null;
		}
		try {
			Structure s = CeSymmPDBTransformer.resultToStructure(result);
			MMTFRoute.handleStructure(s, response);
			return result; // body is set explicitly
		} catch(StructureException | IOException e) {
			logger.error("Error",e);
			String id = request.params(":id");
			Spark.halt(404,"Error fetching "+id);
			return null;
		}

	}

}
