package org.algoavanzati.vertexcover;

import java.util.HashSet;
import java.util.Set;

import org.algoavanzati.vertexcover.Grafo.Edge;

/**
 * Implementazione di VertexCover
 * @author Sergio Giuseppe 
 *
 */
public class VertexCover {

	public static final String RECURSIVE_VC = "recursiveVC";
	
	/**
	 * Prima implementazione del RecursiveVC (OOP).
	 * Restituisce un Set se presente un vertexcover di taglia minore/uguale.
	 * Restituisce null se non esiste.
	 * @param G Grafo su cui calcolare il vertexcover
	 * @param k dimensione del VertexCover da cercare
	 * @return il vertexcover di taglia minore/uguale a k se esiste, in caso contrario null
	 */
	public static Set<Integer> recursiveVC(Grafo G, int k){
		
		//System.out.println(G.toString()+"numEdge:"+G.numEdges()+" numVertex:"+G.numVertex()+" hasEdge:"+G.hasEdges()+"\n");	
		
		if( !G.hasEdges() ) // se G non ha archi, restituisci l'insieme vuoto
			return new HashSet<Integer>();
		if(G.numVertex()<=k) // altrimenti se il ricoprimento è facile, restituisci tutti i vertici
			return G.getVertex();
		if( G.numEdges() > k * ( G.numVertex() - 1  ) ) // altrimenti se |E| > k * ( n - 1 ) allora non esiste il vertexcover
			return null;
		else{
			Edge e = G.nextEdge(); // seleziona il prossimo arco disponibile e=(u,v)
			//System.out.println("e : " + e);
							
			Grafo G1 = new Grafo( G ); // crea una copia:G1 del grafo:G
			G1.removeVertex(e.getU()); // rimuovi il nodo-u dal grafo G1
			//System.out.println("--- RIMOSSO u:"+ e.getU() +" ---> |V|:"+ G1.numVertex() +" |E|:"+ G1.numEdges() +"  \n" + G1.toString()+"\n");
			
			G.removeVertex( e.getV() ); // rimuovi il nodo-v dal grafo G
			//System.out.println("--- RIMOSSO v:"+ e.getV() +" ---> |V|:"+ G.numVertex() +" |E|:"+ G.numEdges() +"  \n" + G.toString()+"\n");
						
			Set<Integer> S1 = recursiveVC(G1, k - 1); // cerca una soluzione in G1
			Set<Integer> S2 = recursiveVC(G, k - 1);  // cerca una soluzione in G
			
			if( S1 == null && S2 == null ) // se nessuna della chiamate ritorna una soluzione, allora non esiste soluzione
				return null;
			else{
				
				if( S1 != null ){          // altrimenti se G1 ha soluzione, aggiungi nodo-u e restituisci
					S1.add(e.getU());
					return S1;
				}
				else{
					S2.add(e.getV());      // altrimenti se G ha soluzione, aggiungi nodo-v e restituisci
					return S2;
				}
				
			}
		}

	}

	/**
	 * Versione ottimizzata dell'algoritmo RecursiveVC
	 * Restituisce un Set se presente un vertexcover di taglia minore/uguale.
	 * Restituisce null se non esiste.
	 * @param M matrice di incidenza del grafo
	 * @param numNodi numero di nodi presenti nel grafo
	 * @param numArchi numero di archi presenti nel grafo
	 * @param k dimensione del vertexcover
	 * @return il vertexcover di taglia minore/uguale a k se esiste, in caso contrario null
	 */
	public static Set<Integer> optimizedRecursiveVC(boolean[][] M, int numNodi, int numArchi, int k){
		
		
		if( numArchi == 0 ) // se G non ha archi, restituisci l'insieme vuoto
			return new HashSet<Integer>();
		if( numArchi > k * (numNodi -1) )  // altrimenti se |E| > k * ( n - 1 ) allora non esiste il vertexcover
			return null;
		
		int i = 0, j = 0;
		
		//flag usato per cercare il prossimo arco presente nel grafo
		// -> se viene trovato un arco, le variabili i e j contengono gli indici del prossimo arco
		boolean found = false;


		// seleziona il prossimo arco disponibile e=(i,j)
		for( i=0; i<M.length; i++ ){
			for( j = 0; j<=i; j++ ){
				if( M[i][j] ){
					found = true;
					break;
				}
			}
			if( found ) break;
		}
		
		
		// crea una copia della matrice:M di incidenza del grafo
		boolean[][] M1 = new boolean[M.length][M.length];
		int numArchi1 = numArchi;
		int numNodi1 = numNodi;
		for(int m = 0; m < M.length; m++){
			for( int n = 0; n <= m; n++ ){
				if( M[m][n] ){
					M1[m][n] = M1[n][m] = true;
				}
			}
		}
		
		// rimuovi il nodo-i (u) dal dalla matrice di incidenza:M
		for( int m = 0; m < M.length; m++ ){
			if( M[m][i] ){
				M[m][i] = false;
				M[i][m] = false;
				numArchi--;
			}
		}
		numNodi--; //decremento quindi il numero di nodi presenti nella matrice:M di incidenza

		
		Set<Integer> S1 = optimizedRecursiveVC(M, numNodi, numArchi, k-1);
		
		if( S1 != null ){ // se M ha soluzione, aggiungi nodo-i e restituisci
			S1.add( i );
			return S1;
		}
		
		
		// rimuovi il nodo-j (v) dal dalla matrice di incidenza:M
		for( int m = 0; m < M1.length; m++ ){
			if( M1[m][j] ){
				M1[m][j] = false;
				M1[j][m] = false;
				numArchi1--;
			}
		}
		numNodi1--; //decremento quindi il numero di nodi presenti nella matrice:M1 di incidenza
		
		Set<Integer> S2 = optimizedRecursiveVC(M1, numNodi1, numArchi1, k-1);
		
		if( S2 == null ) // se M1 non ha soluzione restituisci null
			return null;
		else{            // se M1 ha soluzione, aggiungi il nodo-j e restituisci
			S2.add(j);
			return S2;
		}
		
		
	}

	
	
	
	
	public static long lasttime = 0L;
	public static String buffer_log = "";
	
	/// duplicato di recursiveVC ma con recursion_level
	/**
	 * Versione RecursiveVC dell'algoritmo del VertexCover
	 * Questa versione utilizza le variabili di classe lasttime e buffer_log : in sostanza ad ogni chiamata vengono
	 * passati i livelli di ricorsione di sinistra, di destra e l'instante di tempo in cui vengono invocate.
	 * Il primo che arriva in un certo tempo (currtime - VertexCover.lasttime) stampa il buffer e lo svuota.
	 * La stampa è strutturata come una visita post-order dell'albero di computazione
	 * Alla fine della ricorsione il buffer resta pieno e resta al chiamate svuotarlo.
	 * 
	 * Restituisce un Set se presente un vertexcover di taglia minore/uguale.
	 * Restituisce null se non esiste.
	 * @param G Grafo su cui calcolare il vertexcover
	 * @param k dimensione del VertexCover da cercare
	 * @param recursion_level_left livello di ricorsione sinistra
	 * @param recursion_level_right livello di ricorsione destra
	 * @param starttime istante in cui viene per la prima volta invocato l'algoritmo
	 * @param currtime istante in cui viene invocato l'algo durante la ricorsione (da porrere inizialmente a starttime)
	 * @return il vertexcover di taglia minore/uguale a k se esiste, in caso contrario null
	 */
	public static Set<Integer> loggedRecursiveVC(Grafo G, int k, int recursion_level_left, int recursion_level_right, long starttime, long currtime ){
		
		long newCurrtime = System.currentTimeMillis();

		
		if( !G.hasEdges() ) // se G non ha archi, restituisci l'insieme vuoto
			return new HashSet<Integer>();
		if(G.numVertex()<=k) // altrimenti se il ricoprimento è facile, restituisci tutti i vertici
			return G.getVertex();
		if( G.numEdges() > k * ( G.numVertex() - 1  ) ) // altrimenti se |E| > k * ( n - 1 ) allora non esiste il vertexcover
			return null;
		else{
			Edge e = G.nextEdge(); // seleziona il prossimo arco disponibile e=(u,v)

							
			Grafo G1 = new Grafo( G ); // crea una copia:G1 del grafo:G
			G1.removeVertex(e.getU()); // rimuovi il nodo-u dal grafo G1

			G.removeVertex( e.getV() ); // rimuovi il nodo-v dal grafo G

			
			// quindi scendi nel livello di ricorsione a sinistra...
			recursion_level_left++;
			// cerca una soluzione in G1
			Set<Integer> S1 = loggedRecursiveVC(G1, k - 1, recursion_level_left, recursion_level_right,starttime, newCurrtime );
			// risali 
			recursion_level_left--;		
			
			
			//e quindi scendi nel livello di ricorsione a destra...
			recursion_level_right++;
			// cerca una soluzione in G
			Set<Integer> S2 = loggedRecursiveVC(G, k - 1, recursion_level_left, recursion_level_right,starttime, newCurrtime );
			//quindi risali ancora
			recursion_level_right--;
			
			
			// Aggiungi i dati della chiamata nel buffer di stampa
			buffer_log += "TIME:"+ (newCurrtime-starttime) +"ms | recursion == LEFT : " + recursion_level_left + " , RIGHT : " + recursion_level_right + "\n";

			
			// Se una chiamata raggiunge l'intervallo, stampa ed azzera il buffer
			long interval_time = currtime - VertexCover.lasttime;
			if( interval_time > 5000L  ){
				System.out.println(buffer_log);
				buffer_log = "";
				VertexCover.lasttime = newCurrtime;
			}
			
			
			if( S1 == null && S2 == null ) // se nessuna della chiamate ritorna una soluzione, allora non esiste soluzione
				return null;
			else{
				
				if( S1 != null ){          // altrimenti se G1 ha soluzione, aggiungi nodo-u e restituisci
					S1.add(e.getU());
					return S1;
				}
				else{
					S2.add(e.getV());      // altrimenti se G ha soluzione, aggiungi nodo-v e restituisci
					return S2;
				}
				
			}
		}

	}
	
	/**
	 * Versione BruteForce dell'algoritmo del VertexCover
	 * Restituisce un Set se presente un vertexcover di taglia minore/uguale.
	 * Restituisce null se non esiste.
	 * @param G Grafo su cui calcolare il vertexcover
	 * @param k dimensione del VertexCover da cercare
	 * @return insieme di nodi del vertexcover
	 */
	public Set<Integer> bruteForceVertexCover(Grafo G,int k){
		
		if(!G.hasEdges()) // se G non ha archi, restituisci l'insieme vuoto
			return new HashSet<Integer>();
		if(G.numVertex()<=k) // altrimenti se il ricoprimento è facile, restituisci tutti i vertici
			return G.getVertex();
		else{
			Edge e = G.nextEdge(); // seleziona il prossimo arco disponibile e=(u,v)
			Grafo G1 = new Grafo(G); // crea una copia:G1 del grafo:G
			G1.removeVertex(e.getU()); // rimuovi il nodo-v dal grafo G
			
			G.removeVertex(e.getV()); // rimuovi il nodo-v dal grafo G
			
			Set<Integer> S1 = bruteForceVertexCover(G1,k-1); // cerca una soluzione in G1
			Set<Integer> S2 = bruteForceVertexCover(G,k-1);  // cerca una soluzione in G
			if( S1 == null && S2 == null )  // se nessuna della chiamate ritorna una soluzione, allora non esiste soluzione
				return null;
			else{
				
				if( S1 != null ){           // altrimenti se G1 ha soluzione, aggiungi nodo-u e restituisci
					S1.add(e.getU());
					return S1;
				}
				else{
					S2.add(e.getV());       // altrimenti se G ha soluzione, aggiungi nodo-v e restituisci
					return S2;
				}
				
			}
		}
		
	}
}
