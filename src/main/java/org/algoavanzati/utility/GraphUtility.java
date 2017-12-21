package org.algoavanzati.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.algoavanzati.vertexcover.Grafo;
import org.algoavanzati.vertexcover.Grafo.Edge;

/**
 * Utility per grafi
 * @author Sergio Giuseppe 
 *
 */
public class GraphUtility {

	/**
	 * Carica un grafo da un file *.mtx .
	 * Il file in input è una codifica usata per le matrici, <a href="http://math.nist.gov/MatrixMarket/formats.html">vedi altro su nist.gov</a> .
	 * Restituisce una istanza Grafo
	 * @param pathname
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Grafo fromMTX( String pathname ) throws FileNotFoundException{
		File file = new File(pathname);
		Scanner scanner = new Scanner(file);

		Grafo G = null;
		int rows, cols;
		int numEdges=0, counterEdges=0;
		
		//finding first line
		boolean foundDefLine = false;
		while( scanner.hasNextLine() ){
			String currLine = scanner.nextLine();
			//System.out.println("line -> |" + currLine + "|");
			if( !currLine.startsWith("%%") && !currLine.equals("") ){
				Pattern patternDef = Pattern.compile("(\\d+) (\\d+) (\\d+)");
				Matcher m = patternDef.matcher(currLine);
				
				if(m.groupCount() != 3) throw new RuntimeException("Syntax Error : on define line");
				
				m.find();
				
				rows = Integer.valueOf(m.group(1));
				cols = Integer.valueOf(m.group(2));
				numEdges = Integer.valueOf(m.group(3));
				
				int maxDim = Math.max(rows, cols);
				G = new Grafo(maxDim);
				
				foundDefLine = true;
				break;
			}
			
		}		
		if( !foundDefLine ) throw new RuntimeException("Syntax Error : define line not found");
		if( G == null ) throw new RuntimeException("Error in init graph");
		
		
		
		//insert edge in graph
		while( scanner.hasNextLine() ){
			
			String currLine = scanner.nextLine();
			//System.out.println("line -> |" + currLine + "|");
			
			if( !currLine.startsWith("%%") && !currLine.equals("") ){
				Pattern patternNextEdge = Pattern.compile("(\\d+) (\\d+)");
				Matcher m = patternNextEdge.matcher(currLine);
				
				if(m.groupCount() != 2) throw new RuntimeException("Syntax Error : on define edge");
				
				m.find();
				int i = Integer.valueOf(m.group(1)) - 1;
				int j = Integer.valueOf(m.group(2)) - 1;
				
				G.setEdge(i, j);
				counterEdges++;
				
			}
			
			
		}
		
		if( counterEdges != numEdges ) throw new RuntimeException("Invalid num edge declared in define");
		
		scanner.close();
		
		return G;
	}
	
	/**
	 * Salva una istanza Grafo su file 
	 * @param G
	 * @param filePath path assoluto o relativo
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void toMTX( Grafo G, String filePath ) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filePath, "UTF-8");
		
		int numEdges = G.numEdges();
		int numVertex = G.numVertex();
		writer.print( numVertex + " " + numVertex + " " + numEdges );
		
		for( Edge e : G.getEdges() ){
			writer.print("\n" + (e.getU()+1) + " " + (e.getV()+1) );
		}
		
		writer.close();
	}
	
	/**
	 * Genera un grafo avente un numero di nodi pari a numNodi ed un numero di archi pari ad numArchi
	 * @param numNodi numero di nodi nel grafo
	 * @param numArchi numero di archi nel grafo
	 * @return
	 */
	public static Grafo generaGrafo(int numNodi, int numArchi){
		Random randomGenerator = new Random();
		
		Grafo G = new Grafo(numNodi);
		
		while( G.numEdges() < numArchi ){
			int random_j;
			int random_i = randomGenerator.nextInt( numNodi );
			while( (random_j = randomGenerator.nextInt(numNodi)) == random_i );
			
			G.setEdge(random_i, random_j);
		}
		
		return G;
	}
	
}
