/* ngl_ui.js
 *
 * Functions for interacting with NGL
 */

/**
 * Assigns actions for the default keys
 */
function default_keypress(stage, e) {
    e = e || window.event;

    if (e !== undefined && e.key == "h") {
    	console.log("Supported Keys:\n" +
    			"s/m/l/x    screenshots of various sizes");
    }
    if (e !== undefined && e.key == "s") { // 115 is s
       console.log("s was pressed: small screenshot (1x)");
       screenshot(stage,1);
    }

    if (e !== undefined && e.key == "m") { // 109 is m
       console.log("m was pressed: medium screenshot (2x)");
       screenshot(stage,2);
    }

    if (e !== undefined && e.key == "l") { // 108 is l
       console.log("l was pressed: large screenshot (3x)");
       screenshot(stage,3);
    }

    if (e !== undefined && e.key == "x") { // 120 is x
       console.log("x was pressed: xtra large screenshot (4x)");
       screenshot(stage,4);
    }

};

/**
 * A function to trigger a png transparent screenshot with given factor
 * @param stage the NGL stage
 * @param factor the number of times the current canvas size will be multiplied by
 */
function screenshot(stage,factor) {
    if (stage!==undefined) {
       stage.makeImage({factor: factor, antialias: true, trim: false, transparent: true}).then(function(blob) { NGL.download(blob) } )
    }
}

function defaultNGLRepr(stage) {
    stage.addRepresentation("cartoon", {sele:""});
    stage.addRepresentation("ball+stick",{sele:"hetero and not ( water or ion )"});
}

/**
 * Returns a dynamic color scheme function that colors letters by their row
 */
function repeatScheme(numrows){
	var colors = palette('cb-Dark2', numrows);
	return function(letter,opts) {
		if(/[A-Z]/.exec(letter) && /[^a-z]/.exec(m.g.seqs.models[opts.y].attributes.seq[opts.pos]) ){
			return "#"+colors[opts.y];
			//return "#0f0";
		} else {
			return "#fff";
		}
	}
}