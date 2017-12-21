package org.algoavanzati.testexec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;
import org.algoavanzati.vertexcover.VertexCover;

/**
 * In questo test viene mostarto come utilizzare la versione ottimizzata di RecursiveVC
 * Per non prendere il codice costruito per la prima versione, utilizziamo il metoto toMatrix()
 * che restituisce la matrice di incidenza del grafo.
 * Allo stesso modo della prima versione vengono calcolato il tempo di esecuzione
 *
 * @author Sergio Giuseppe 
 */
public class TestRunningRecursiveVC3 {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		//Grafo G = GraphUtility.fromMTX(TestVars.PATH+"dataset_2_30nodi.mtx");
		Grafo G = GraphUtility.generaGrafo(30, 147);
		GraphUtility.toMTX(G, "tmp_grafo.mtx");
		System.out.println("G : " + G);
		
		
		boolean[][] M = G.getMatrix();
		int numNodi = G.numVertex;
		int numArchi = G.numArchi;
		int k = 25;
		
		long starttime = System.currentTimeMillis();
		Set<Integer> sol = VertexCover.optimizedRecursiveVC(M, numNodi, numArchi, k);
		long endtime = System.currentTimeMillis();
		
		System.out.println("TIME : " + (endtime-starttime) + "ms");
		System.out.println("solution : " + sol );
		
		if( sol != null ) {
			System.out.println("size(solution) : " + sol.size() );
		}
		
		
	}
	
}
