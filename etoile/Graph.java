package etoile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe principale utilisant la classe Node afin de creer une 'matrice' de noeuds ainsi que l'algorithme A*
 *
 */
public class Graph {

	
		private int width;
		private int height;
		private Node[][] nodes;
		private Set<Node> openList;
		private Set<Node> closedList;
		
		public Graph(int width, int height) {
			this.width = width;
			this.height = height;
			nodes = new Node[width][height];
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					nodes[i][j] = new Node(i, j);
				}
			}
			openList = new HashSet<Node>();
			closedList = new HashSet<Node>();
		}
		
		/** On initialise le graphe */
		public void init() {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					if(nodes[i][j].isAccessible()) {
						nodes[i][j] = new Node(i, j);
					}
				}
			}
			openList = new HashSet<Node>();
			closedList = new HashSet<Node>();
		}
		
		/** met un obstacle a la position x,y*/
		public void setObstacle(int x, int y) {	
			nodes[x][y].setAccess(false);		
		}
		
		/** on donne en parametre un entier qui sera convertit en pourcentage */
		public void setObstacleAlea(int pourcentage) {
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
						nodes[i][j].setAccess(true);
					
				}
			}
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					int r = (int) (Math.random()*100);
					if(r <= pourcentage) {
						setObstacle(i, j);
					
					}
				}
			}
			
		}
		
		/** affiche le graphe de base*/
		public void affiche() {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					if(nodes[i][j].isAccessible()) {
						System.out.print("1|");
					}
					else {
						System.out.print("0|");
					}
				}
				System.out.println();
			}
		}
		
		/** affiche le graphe en indiquant le chemin avec le chiffre 2*/
		public void affichageSolution(Node source, Node fin) {
			/** On récupère la solution et on construit le graphe dans la console */
			List<Node> ns = calcPath(source, fin);
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					if(ns.contains(nodes[i][j])) {
						System.out.print("2|");
					}
					else if(nodes[i][j].isAccessible()) {
						System.out.print("1|");
					}
					else {
						System.out.print("0|");
					}
				}
				System.out.println();
			}
		}
		
		/**  renvoie un noeud*/
		public Node getNode(int x, int y) {
			return nodes[x][y];
		}
		
		/** Recupere le noeud avec le cout minimal (fCosts) de la liste OpenList */
		public Node getNodeWithLowestCost() {
			Node min = null;
			int minV = Integer.MAX_VALUE;
			for(Node n : openList) {
				if(minV > n.getfCosts()) {
					minV = n.getfCosts();
					min = n;
				}
			}
			return min;
		}
		
		/** Renvoie les 8 voisins 'franchissables' autour du noeud 'current' et qui ne se trouvent pas dans la liste closedList */
		public Set<Node> getAdjacent(Node current) {
			Set<Node> nodes = new HashSet<Node>();
			/** Les deux boucles 'for' permettent de rechercher autour de la case où l'on est actuellement */
			for(int y = current.getyPosition()-1; y <= current.getyPosition()+1; y++) {
				
				for(int x = current.getxPosition()-1; x <= current.getxPosition()+1; x++) {
					
					/** avec cette condition, on exclu les opérations sur la case actuelle*/
					if(x != current.getxPosition() || y != current.getyPosition()) {
						
						/** deux conditions permettant de vérifier si l'on est pas au bord du graphe */
						if(x >= 0 && x < width) {			
							if(y >= 0 && y < height) {
								
								/** On vérifie que la case n'est pas bloqué */
								if(this.nodes[x][y].isAccessible()) {
									
									/** Puis on vérifie que la case n'est pas contenu dans le liste de noeuds fermés. */
									if(!closedList.contains(this.nodes[x][y])) {
										
										Node n = new Node(x, y);
										
										/** Ici on vérifie si la case sur laquelle on se déplace est en diagonale, si c'est le cas on 
										 * met l'attribut diagonally à true */
										if(x == current.getxPosition()-1 && y == current.getyPosition()-1 || 
											x == current.getxPosition()+1 && y == current.getyPosition()+1 ||
											x == current.getxPosition()-1 && y == current.getyPosition()+1 ||
											x == current.getxPosition()+1 && y == current.getyPosition()-1) {
											n.setIsDiagonaly(true);
										}
										else
											/** Sinon on ajoute la node au tableau de nodes que l'on retourne */
											nodes.add(n);
									}
								}
							}
						}
					}
				}
			}
			return nodes;
		}
		
		/** Calcul le chemin le plus court entre sourceNode et targetNode en utilisant l'algorithme A* */
		public int findPath(Node sourceNode, Node targetNode) {
			
			openList.add(sourceNode);
			
			/** boucle qui ne se termine que lorsque la liste des sommets ouvert est vide 
			 * ( c'est à dire lorsque le dernier sommet à atteint l'objectif )*/
			while(true) {
				Node current = getNodeWithLowestCost();
				openList.remove(current);
				closedList.add(current);
				
				if(current.equals(targetNode)) {
					return 1;
				}
				
				Set<Node> adjacentNodes = getAdjacent(current);
				
				/** on modifie les nodes dans notre tableau sinon ils ne sont pas enregistré. */
				for(Node adj : adjacentNodes) {
					if(!openList.contains(adj)) {
						nodes[adj.getxPosition()][adj.getyPosition()].setPrevious(nodes[current.getxPosition()][current.getyPosition()]);
						
						nodes[adj.getxPosition()][adj.getyPosition()].sethCosts(nodes[targetNode.getxPosition()][targetNode.getyPosition()]);
						
						nodes[adj.getxPosition()][adj.getyPosition()].setgCosts(nodes[current.getxPosition()][current.getyPosition()]);
						
						openList.add(nodes[adj.getxPosition()][adj.getyPosition()]);
					}
					else {
						if(adj.getgCosts() > adj.calculategCosts(current)) {
							nodes[adj.getxPosition()][adj.getyPosition()].setPrevious(nodes[current.getxPosition()][current.getyPosition()]);
							
							nodes[adj.getxPosition()][adj.getyPosition()].setgCosts(nodes[current.getxPosition()][current.getyPosition()]);
						}
					}
				}
				if(openList.isEmpty()) {
					return 0;
				}
			}
		}
		
		/** Renvoie le chemin entre sourceNode et targetNode */
		public List<Node> calcPath(Node sourceNode, Node targetNode){
			List<Node> chemin = new ArrayList<Node>();
			targetNode = nodes[targetNode.getxPosition()][targetNode.getyPosition()];
			chemin.add(targetNode);
			while(targetNode != null) {
				targetNode = targetNode.getPrevious();
				if(targetNode != null) {
					chemin.add(targetNode);
				}
			}
			Collections.reverse(chemin); //
			return chemin;
		}
	}
