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

public class NGLParams {
	private String url;
	private String structUrl;
	private int size;

	public NGLParams(String structUrl) {
		url = "//cdn.jsdelivr.net/gh/nglviewer/ngl@v0.9.3/dist/ngl.js";
		this.structUrl = structUrl;
		this.setSize(500);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStructUrl() {
		return structUrl;
	}
	public void setStructUrl(String structUrl) {
		this.structUrl = structUrl;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
