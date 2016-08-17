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
import org.biojava.http.models.CeSymmRouteParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Handle requests for {@link BioJavaRoutes#CESYMM}
 * @author Spencer Bliven
 *
 */
public class CeSymmRoute implements TemplateViewRoute {
	public static Logger logger = LoggerFactory.getLogger(CeSymmRoute.class);

	private final String template;
	public CeSymmRoute() {
		this("cesymm.html.hbs");
	}
	public CeSymmRoute(String template) {
		this.template = template;
	}

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		String id = request.params(":id");
		if(id == null) {
			response.status(404);
			logger.error("null id");
			return null;
		}
		try {
			String structUrl = BioJavaRoutes.CESYMM_MMTF.replace(":id", id);
			String jsonUrl = BioJavaRoutes.CESYMM_TSV.replace(":id", id);
			CeSymmRouteParams params = new CeSymmRouteParams(id,structUrl,jsonUrl);
			return new ModelAndView(params, template);
		} catch(Exception e) {
			logger.error("Error",e);
			response.status(404);
			return null;
		}
	}

}
