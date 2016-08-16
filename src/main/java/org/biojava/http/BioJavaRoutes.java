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
 
package org.biojava.http;
 
public class BioJavaRoutes {
	public static String PDB = "/pdb/:id";
	public static String MMCIF = "/mmcif/:id";
	public static String NGL = "/ngl/:id";
	public static String CESYMM = "/cesymm/:id";
	public static String CESYMM_MULTIPLE = "/cesymm/:id/multi";
	public static String CESYMM_JSON = "/cesymm/:id/json";
	public static String CESYMM_PDB = "/cesymm/:id/pdb";
	public static String CESYMM_AXES = "/cesymm/:id/axes";
	public static String CESYMM_TSV = "/cesymm/:id/tsv";
	public static String CESYMM_FASTA = "/cesymm/:id/fasta";
}
