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

import javax.servlet.ServletOutputStream;

import org.biojava.http.BioJavaRoutes;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.io.mmtf.MmtfActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Handle requests for {@link BioJavaRoutes#MMTF}
 * @author Spencer Bliven
 *
 */
public class MMTFRoute implements Route {
	public static Logger logger = LoggerFactory.getLogger(MMTFRoute.class);

	@Override
	public String handle(Request request, Response response) {
		String id = request.params(":id");
		if(id == null) {
			Spark.halt(404, "No structure specified" );
			return null;
		}
		try {
			Structure s = StructureIO.getStructure(id);
			if(s == null) {
				response.status(404);
				return "Error fetching "+request;
			}
			handleStructure(s, response);
			return "200 OK"; // body is set explicitly
		} catch(StructureException | IOException e) {
			logger.error("Error",e);
			Spark.halt(404,"Error fetching "+id);
			return null;
		}
	}
	
	public static void handleStructure( Structure s, Response response) throws IOException {
		ServletOutputStream out = response.raw().getOutputStream();
		MmtfActions.writeToOutputStream(s,out);
		response.type( "application/octet-stream");
	}


}
