package org.algoavanzati.testexec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;
import org.algoavanzati.vertexcover.VertexCover;

/**
 * Questa test suite cerca di automatizzare il test che abbiamo eseguito per calcolare
 * i tempi di esecuzione ma sull'algoritmo ottimizzato RecursiveVC.
 * 
 * Fissato un incremento inc=5 si esegue un doWhile
 * in ogni ciclo
 * 		si calcola il numero di archi associati per avere una densità pari a 0.3
 * 		si calcola il k associato alla chiamata dell'analisi fatta, ossia pari a 4/5 di n
 * 		si genera un grafo avente |V| e |E| come definiti
 * 		lo si salva su file
 * 		si invoca l'algoritmo, calcolando i tempi e la soluzione associata, stampando a video i risultati
 * 
 * @author Sergio Giuseppe 
 */
public class DynTestRunningVC3 {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		long currdate = System.currentTimeMillis();
		
		int numV = 0;
		int numE = 0;
		int k = 0;
		
		int inc = 5;
		int j = 1;
		
		while( true ) {
			
			System.out.println("-------- Test " + j);
			
			numV += inc;
			
			numE = (int) ((((double)(numV * (numV-1))) * 0.3) / 2.0);
			k = (int)((4.0 * (double)numV)/5.0);
			//k = numV;
			
			System.out.printf("|V|=%d  |E|=%d  k=%d \n", numV, numE, k);
			
			Grafo G = GraphUtility.generaGrafo(numV, numE);
			GraphUtility.toMTX(G, "test/test_"+j+".mtx");
			boolean[][] M = G.getMatrix();
			
			long starttime = System.currentTimeMillis();
			Set<Integer> sol = VertexCover.optimizedRecursiveVC(M, numV, numE, k);
			long endtime = System.currentTimeMillis();
			
			
			System.out.println("TIME : " + (endtime-starttime) + "ms");
			System.out.println("solution : " + sol );
			if( sol != null ) {
				System.out.println("size(solution) : " + sol.size() );
			}
			
			j++;
		}
		

		
		
		
	}

}
