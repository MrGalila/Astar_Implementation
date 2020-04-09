package etoile;


/**
 * Classe Main utilisant A* et l'affichant directement sur la console
 *
 */
public class Main {

	/**
	 * @param Méthode main, initialise le graph avec des obstacles puis lance les différentes méthodes de A*
	 */
	public static void main(String[] args) {
		Graph g = new Graph(10, 10);
		
		Node sourceNode = new Node(0, 0);
		Node targetNode = new Node(9, 9);
		
		g.setObstacle(0, 1);
		g.setObstacle(1, 1);
		g.setObstacle(2, 1);
		g.setObstacle(3, 1);
		g.setObstacle(3, 2);
		g.setObstacle(4, 2);
		g.setObstacle(5, 2);
		g.setObstacle(5, 1);
		
		g.setObstacle(9, 4);
		g.setObstacle(8, 4);
		g.setObstacle(7, 4);
		g.setObstacle(6, 5);
		g.setObstacle(6, 6);
		g.setObstacle(6, 7);
		
		g.affiche();
		
		System.out.println("Nodes adjacentes au sommet 2,8:");
		System.out.println(g.getAdjacent(new Node(2, 8)));
		
		
		g.findPath(sourceNode, targetNode); // retourne 1 si chemin trouvé sinon 2
		
		System.out.println("Chemin trouvé par la fonction:");
		System.out.println(g.calcPath(sourceNode, targetNode));
		
		g.affichageSolution(sourceNode, targetNode);

	}

}
