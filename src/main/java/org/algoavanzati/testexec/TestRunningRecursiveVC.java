package org.algoavanzati.testexec;

import java.io.FileNotFoundException;
import java.util.Set;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;
import org.algoavanzati.vertexcover.VertexCover;

/**
 * Questo test mostra l'esecuzione della prima versione RecursiveVC.
 * Dopo aver caricato un file *.mtx si memorizzano gli istanti di tempo starttime
 * ed endtime per calcolare il tempo di esecuzione.
 * Dopo aver concluso, viene stampata la soluzione ed il tempo totale.
 *
 * @author Sergio Giuseppe 
 */
public class TestRunningRecursiveVC {

	public static void main(String[] args) throws FileNotFoundException {
		
		Grafo G = GraphUtility.fromMTX(TestVars.PATH+"dataset_40nodi.mtx");
		
		System.out.println("G : " + G);
		int k = 28;
		
		long starttime = System.currentTimeMillis();
		Set<Integer> sol = VertexCover.recursiveVC(G, k);
		long endtime = System.currentTimeMillis();
		
		System.out.println("TIME : " + (endtime-starttime));
		System.out.println("solution : " + sol );
		
	}
	
}
