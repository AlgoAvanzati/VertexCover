package org.algoavanzati.testexec;

import java.io.FileNotFoundException;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;
import org.algoavanzati.vertexcover.VertexCover;

/**
 * Lancio di una TestSuite su determinati dataset 
 * usando determinati  k:dimensione_vertexcover 
 * 
 * @author Sergio Giuseppe 
 *
 */
public class TestSuiteRecursiveVC {

	public final static String PATH = "/home/sergio/UniSpec/corsi/AA/progetto/dataset/gen/";
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		int[] indexFiles = new int[]{0,1,2,3,4,5,6,7,8};
		int[] numbersK = new int[]{5,13,20}; 
		
		System.out.println("NOME-FILE, NUM-EDGE, K, TIME");
		for( int i = 0; i<indexFiles.length; i++ ){
			for( int j = 0; j < numbersK.length; j++ ){
				
				Grafo G = GraphUtility.fromMTX(PATH+"dataset_"+ indexFiles[i] +".mtx");
				int k = numbersK[j];
				
				long t1 = System.currentTimeMillis();
				VertexCover.recursiveVC(G, k);
				long t2 = System.currentTimeMillis();
				long time = t2-t1;
				
				System.out.println("dataset_"+ indexFiles[i] +".mtx, "+ G.numEdges() +", "+ k + ", " + time);
				
			}
		}

		
	}

}
