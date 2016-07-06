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
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handle requests for {@link BioJavaRoutes#PDB}
 * @author Spencer Bliven
 *
 */
public class PDBRoute implements Route {
	public static Logger logger = LoggerFactory.getLogger(PDBRoute.class);

	@Override
	public String handle(Request request, Response response) throws Exception {
		String id = request.params(":id");
		if(id == null) {
			response.status(404);
			return "No structure specified";
		}
		try {
			Structure s = StructureIO.getStructure(id);
			if(s == null) {
				response.status(404);
				return "Error fetching "+id;
			}
			String pdb = s.toPDB();
			String filename = id+".pdb";
			response.header("Content-Type", "chemical/x-pdb");
			response.header("Content-Disposition", String.format("inline; filename=\"%s\"",filename));
			return pdb;
		} catch(StructureException e) {
			logger.error("Error",e);
			response.status(404);
			return "Error fetching "+id;
		}
	}

}
