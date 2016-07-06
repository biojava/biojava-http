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

package org.biojava.http.structure;

import static spark.Spark.get;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.StructureTools;
import org.biojava.nbio.structure.symmetry.internal.CeSymm;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


public class AlignmentGuiServer {
	public static Logger logger = LoggerFactory.getLogger(AlignmentGuiServer.class);
	
	public static void main(String[] args) {
		
		get("/", (r,r2) -> "BioJava HTTP");

		get("/pdb/:id", (request, response) -> {
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
		});

		get("/mmcif/:id", (request, response) -> {
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
				String pdb = s.toMMCIF();
				String filename = id+".cif";
				response.header("Content-Type", "chemical/x-pdb");
				response.header("Content-Disposition", String.format("inline; filename=\"%s\"",filename));
				return pdb;
			} catch(StructureException e) {
				logger.error("Error",e);
				response.status(404);
				return "Error fetching "+id;
			}
		});

		get("/cesymm/:id", (request, response) -> {
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

				CESymmParams params = new CESymmParams(result);
				return new ModelAndView(params, "cesymm.html.hbs");
			} catch(Exception e) {
				logger.error("Error",e);
				response.status(404);
				return null;
			}
		}, new HandlebarsTemplateEngine());

	}

}
