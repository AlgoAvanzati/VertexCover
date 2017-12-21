package org.algoavanzati.testexec;

import java.io.FileNotFoundException;
import java.util.Set;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;
import org.algoavanzati.vertexcover.VertexCover;

/**
 * Questa versione di test utilizza la versione di RecursiveVC che 
 * effettua il debug dei livelli di ricorsione.
 * Dopo aver invocato l'algoritmo, stampiamo il restante dei messaggi presenti 
 * nel buffer (VertexCover.buffer_log)
 *
 * @author Sergio Giuseppe 
 */
public class TestRunningRecursiveVCWithRecursionLevel {

	public static void main(String[] args) throws FileNotFoundException {
		
		Grafo G = GraphUtility.fromMTX(TestVars.PATH + "dataset_2_30nodi.mtx");
		
		System.out.println("G : " + G);
		int k = 13;
		
		long starttime = System.currentTimeMillis();
		Set<Integer> sol = VertexCover.loggedRecursiveVC(G, k, 0, 0, starttime, starttime);
		long endtime = System.currentTimeMillis();
		System.out.println(VertexCover.buffer_log);
		
		System.out.println("TIME : " + (endtime-starttime));
		System.out.println("solution : " + sol );
		
	}
	
}
