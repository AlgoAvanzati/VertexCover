package org.algoavanzati.vertexcover;

import java.util.HashSet;
import java.util.Set;

/**
 * Matrice di Incidenza del grafo
 * @author Sergio Giuseppe 
 *
 */
public class Grafo{
	
	// matrice di incidenza
 	public boolean[][] matrix;
	
 	// grandezza della matrice
 	public int size;
	
 	// numero di archi presenti nel grafo
	public int numArchi;
	
	// numero di vertici presente nel grafo
	public int numVertex;
	
	// nodi presenti nella matrice
	public Set<Integer>Vertex;
	
	
	/**
	 * Crea un grafo avente un numero di nodi pari numNodi.
	 * Ogni nodo presente nel grafo è etichettato con un numero compreso in [0,numNodi]
	 * @param numNodi numero di nodi presenti nel grafo
	 */
	public Grafo( int numNodi ){
		matrix = new boolean[numNodi][numNodi];
		size = numNodi;
		numArchi = 0;
		numVertex = numNodi;
		Vertex= new HashSet<Integer>();
		for (int i = 0; i < size; i++) {
			Vertex.add(i);
		}
	}
	
	/**
	 * Crea un grafo creandone una copia da un grafo G di partenza
	 * @param G Grafo da copiare, passato al costruttore
	 */
	public Grafo( Grafo G ){
		size = G.size;
		matrix=new boolean[size][size];
		for( int i = 0; i < size; i++ )
			for( int j = 0; j <= i; j++ ) {
				if(G.matrix[i][j]) {
					matrix[i][j] = true;
					matrix[j][i] = true;
				}	
			}				
		numArchi = G.numArchi;
		numVertex = G.numVertex;
		Vertex=G.getVertex();
	}
	
	/**
	 * Imposta un arco (i,j) tra un nodo-i ed un nodo-j
	 * Fissa anche l'arco inverso (j,i) nella matrice di incidenza
	 * @param i indice nodo-i
	 * @param j indice nodo-j
	 */
	public void setEdge(Integer i, Integer j){
		if( this.matrix[i][j] && this.matrix[j][i] ) 
			return;
		this.matrix[i][j] = true;
		this.matrix[j][i] = true;
		numArchi++;
	}
	
	/**
	 * Rimuovi un nodo-i dal grafo.
	 * Imposta tutti gli archi del grafo associati alla riga-i e alla colonna-i pari a 0. 
	 * @param i
	 */
	public void removeVertex( Integer i ){
		boolean found = false;
		for( int k = 0; k < size; k++ ){ //per ogni riga della matrice
			if( matrix[k][i] ){ // se esiste l'arco (k,i)
				found = true;
				matrix[k][i] = false;  //cancellalo
				matrix[i][k] = false;  // e cancella il suo inverso (i,k) che sicuramente esiste
				numArchi--; //decrementa il numero di archi
			}
		}
		if( found ) { // se è stato trovato un arco, allora esisteva il nodo
			numVertex--; // decrementa il contatore dei nodi
			Vertex.remove(i); // e rimuovilo dalla struttura di appoggio
		}
	}
	
	/**
	 * Restituisce true se il grafo contiene archi
	 * @return true, se il grafo contiene archi
	 */
	public boolean hasEdges(){return (numArchi != 0);}
	
	/**
	 * Restituisce il numero di archi presenti nel grafo
	 * @return numero di archi presenti nel grafo
	 */
	public int numEdges(){return numArchi;}
	
	/**
	 * Restituisce il numero di vertici presenti nel grafo
	 * @return numero di vertici presenti nel grafo
	 */
	public int numVertex(){return numVertex;}
	
	/**
	 * Restituisce l'insieme di vertici (Set<Integer>)presenti nel grafo.
	 * In esso sono presenti gli indici dei vertici del grafo
	 * @return Set, di indici presenti nel grafo
	 */
	public Set<Integer> getVertex(){return Vertex;}
	
	
	/**
	 * La classe Edge astrae il concetto di arco presente nel grafo
	 * Sostanzialmente rappresenta un wrap, contenete i due indici dell'arco
	 * @author sergio
	 *
	 */
	public static class Edge {
		private int u;
		private int v;
		
		public Edge( int u, int v ){
			this.u = u;
			this.v = v;
		}
		
		public Integer getU(){return u;}
		
		public Integer getV(){return v;}

		@Override
		public String toString() {return "Edge [u=" + u + ", v=" + v + "]";	}

	}
	
	/**
	 * Restituisce la lista degli archi presenti nel grafo.
	 * Il codice scorre la lista di adiacenza ed ad ogni occorrenza di 1 crea un Edge per l'arco associato,
	 * dopodiché viene inserito nel set da restituire
	 * @return Set<Edge>, la lista degli archi presenti nel grafo
	 */
	public Set<Edge> getEdges(){
		Set<Edge> edges = new HashSet<Edge>();
		for( int i = 0; i<size; i++ ){ // scorri nella triangolare...
			for( int j = 0; j<i; j++ ){
				if( matrix[i][j] ) edges.add(new Edge(i, j)); // se (i,j)=1 aggiungi alla lista Edge associato
			}
		}
		return edges;
	}
	
	/**
	 * Restituisce un prossimo arco presnete nella grafo.
	 * Questo metodo sarà utile nella ricerca di un arco nel grafo per la ricerca del vertexcover
	 * @return il prossimo arco disponibile presente nel grafo
	 */
	public Edge nextEdge(){
		for( int i = 0; i<size; i++ )
			for( int j = 0; j<=i; j++ )
				if( this.matrix[i][j]  )
					return new Edge(i, j);
		return null;
	}

	@Override
	public String toString() {
		String out = "Matrix : \n";
		for (int i = 0; i < this.size; i++) {
			out += " ";
			for (int j = 0; j < this.size; j++) {
				if(matrix[i][j])
					out+="1, ";//out+="t ";
				else
					out += "0, ";//out += "f ";
			}
			out += "\n";
		}
		return out;
	}

	/**
	 * Restituisce la lista dei nodi figli del nodo-i
	 * @param node nodo di cui si vogliono i figli
	 * @return
	 */
	public Set<Integer> getChildren(Integer node) {
		Set<Integer> currChildren = new HashSet<Integer>();
		for( int k = 0; k < this.size; k++ ){ //scorri tutte le righe del grafo con k
			if( this.matrix[node][k] == true ) currChildren.add(k); // se presente (k,node) aggiungilo alla lista dei figli
		}
		return currChildren;
	}

	/**
	 * Restituisci la lista dei vertici presenti nel grafo
	 * @return la lista dei vertici presenti nel grafo
	 */
	public Set<Integer> getVertices() {
		return this.Vertex;
	}


	public void setVertex(Integer v) {
		// TODO Auto-generated method stub
		
	}


	public void removeEdge(Integer u, Integer v) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Restituisce la densità del grafo (vedi wikipedia) compresa tra [0,1]
	 * @return
	 */
	public double getDensity(){
		return ((double)this.numEdges() * 2) / (this.numVertex() * (this.numVertex() - 1));
	}
	
	/**
	 * Restituisce un clone della matrice di incidenza usata dal grafo 
	 * @return matrice di incidenza
	 */
	public boolean[][] getMatrix(){
		return this.matrix.clone();
	}

}
