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

package org.biojava.http.json;

import java.lang.reflect.Type;

import org.biojava.nbio.structure.align.multiple.util.MultipleAlignmentWriter;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CeSymmResultSerializer implements JsonSerializer<CeSymmResult> {
	private static final Logger logger = LoggerFactory.getLogger(CeSymmResultSerializer.class);
	
	@Override
	public JsonElement serialize(CeSymmResult src, Type typeOfSrc, JsonSerializationContext context) {
		logger.info("Serializing to json");
		JsonObject json = new JsonObject();
		json.addProperty("multipleAlignment", MultipleAlignmentWriter.toAlignedResidues(src.getMultipleAlignment()));
		json.addProperty("structureId",src.getStructureId().getIdentifier());
		json.addProperty("numRepeats", src.getNumRepeats());
		json.addProperty("refined", src.isRefined());
		return json;
	}
}