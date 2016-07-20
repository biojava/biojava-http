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

package org.biojava.http.models;


public class CeSymmRouteParams extends NGLParams{
	private String structureId;
	private String tsvUrl;
	

	public CeSymmRouteParams(String structureId,String structUrl,String tsvUrl) {
		super(structUrl);
		this.structureId = structureId;
		this.tsvUrl = tsvUrl;
	}

	public String getStructureId() {
		return structureId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public String getTsvUrl() {
		return tsvUrl;
	}

	public void setTsvUrl(String tsvUrl) {
		this.tsvUrl = tsvUrl;
	}

}
