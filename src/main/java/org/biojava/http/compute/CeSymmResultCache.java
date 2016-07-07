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

package org.biojava.http.compute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.symmetry.internal.CESymmParameters;
import org.biojava.nbio.structure.symmetry.internal.CeSymmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CeSymmResultCache {
	private static final Logger logger = LoggerFactory.getLogger(CeSymmResultCache.class);

	private final transient Map<String, Future<CeSymmResult>> cache;
	private final transient AtomCache atomCache;
	private CESymmParameters params;
	private final ExecutorService executor;

	private CeSymmResultCache() {
		// TODO deal with cache clearing
		cache = Collections.synchronizedMap(new HashMap<String, Future<CeSymmResult>>());
		atomCache = new AtomCache();
		params = new CESymmParameters();
		int threads = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(threads);
	}

	public Future<CeSymmResult> analyze(String name) {
		Future<CeSymmResult> future;
		synchronized(cache) {
			if( !cache.containsKey(name)) {
				logger.info("Submitting ",name);
				CESymmParameters p = params.clone();
				Callable<CeSymmResult> worker = new CeSymmRunner(name, p, atomCache);
				future = executor.submit(worker);
				cache.put(name,future);
			} else {
				logger.info("Found previous calculation for {}",name);
				future = cache.get(name);
			}
		}

		return future;
	}

	@Override
	protected void finalize() throws Throwable {
		logger.info("Shutting down {} threads",getClass().getSimpleName());
		super.finalize();
		executor.shutdown();
		while (!executor.isTerminated())
			Thread.sleep(100); // sleep .1 seconds
	}
	
	private static CeSymmResultCache instance = null;
	public static CeSymmResultCache getInstance() {
		if( instance == null) {
			instance = new CeSymmResultCache();
		}
		return instance;
	}
}
