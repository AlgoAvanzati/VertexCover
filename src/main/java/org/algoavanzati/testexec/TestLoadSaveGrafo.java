package org.algoavanzati.testexec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.algoavanzati.utility.GraphUtility;
import org.algoavanzati.vertexcover.Grafo;

/**
 * In questo test viene mostrato come si carica e come si salva un Grafo
 * mediante i metodi offerti dalla classe GraphUtility.
 * Si consiglia l'utilizzo della variabile TestVars.PATH per facilitare 
 * la creazione di workspace per l'import/export del progetto
 *
 * @author Sergio Giuseppe 
 */
public class TestLoadSaveGrafo {
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
	
		//load
		//Grafo G = GraphUtility.fromMTX(TestVars.PATH+"dataset_50nodi.mtx");
		Grafo G = GraphUtility.generaGrafo(35, 178);
		
		System.out.println("numArchi : " + G.numEdges() );
		System.out.println("G : " + G.toString());
		
		//save
		GraphUtility.toMTX(G, TestVars.PATH+"dataset_stima_35nodi.mtx");
		
	}
	
}
