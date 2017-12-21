package org.algoavanzati.testexec;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;

/**
 * Questo test mostra come è possibile generare un grafo 
 * avente n-nodi ed m-archi.
 * Dopo averlo generato mostra anche la densità del grafo.
 * 
 * @author Sergio Giuseppe 
 *
 */
public class TestGenerateGrafo {

	public static void main(String[] args) {
		
		Grafo G = GraphUtility.generaGrafo(25, 50);
		System.out.println("density : " + G.getDensity());
		System.out.println("G : " + G.toString());
		
	}

}
