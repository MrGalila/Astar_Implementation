package etoile;

/**
 *
 * Classe representant nos noeuds
 */
public class Node {

    private int x;
    private int y;
    private boolean access;


    /** Noeud precedent, cette variable permettra à l'algorithme de faire marche arrière si jamais il se trouve coince ou pour retrouve le chemin parcouru lorsqu'il sera
     * arrive au noeud final */
    private Node previous;

    /** Indique dans la recherche si le mouvement pour arrive au noeud actuel etait en diagonale */
    private boolean diagonally;

    /** Contient le poids total du chemin */
    private int fCosts; 

    /** Contient le poids du chemin de la node de depart à la node actuelle */
    private int gCosts;

    /** Contient le poids du chemin de la node actuelle a la node d'arrivee ( estime ) */
    private int hCosts;
    
    /** Coût d'un mouvement basique (gauche, droite, haut bas) */
    protected static final int BASICMOVEMENTCOST =5;
    /** Coût d'un mouvement en diagonale */
    protected static final int DIAGONALMOVEMENTCOST = 10;
    
    /** Constructeur, prend en parametre une position x et y et rend le noeud accesible*/
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.access = true;
    
    }
    
    /** retourne la nature du deplacement jusqu'a ce noeud */
    public boolean isDiagonaly() {
        return diagonally;
    }
    
    /**permet d'indiquer la nature du mouvement precedent lorsque l'on parcours le graphe */
    public void setIsDiagonaly(boolean isDiagonaly) {
        this.diagonally = isDiagonaly;
    }
    
    
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getxPosition() {
        return x;
    }

    public int getyPosition() {
        return y;
    }
    
   
    /** Indique si la node est accessible */	
    public boolean isAccessible() {
        return access;
    }
    
    /** Permet d'indiquer si la case est accessible ou non par l'algorithme */
    public void setAccess(boolean access) {
        this.access = access;
    }    
    /** ---------------------- */
   
    
    /** Pour gerer l'avancer dans le graphe sans perdre le chemin empreinte*/
    public Node getPrevious() {
        return previous;
    }

    	/** Initialise au fur et a mesure que l'on parcours le graphe */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }
    /** ---------------------- */

    
    /** recupere la somme de gCosts et hCosts */
    public int getfCosts() {
        return gCosts + hCosts;
    }

    /** recupere gCosts */
    public int getgCosts() {
        return gCosts;
    }
    
    /** Methode pour enregistrer le chemin emprunte (deplacement en diagonale ou en croix ) */
    public void setgCosts(Node previousNode) {
        if (diagonally) {
            this.gCosts +=previousNode.getgCosts() + DIAGONALMOVEMENTCOST;
        } else {
        	this.gCosts +=previousNode.getgCosts() + BASICMOVEMENTCOST;
        }
    }
    
    /** Methode utilise dans le Graphe pour calculer le cout total du chemin */
    public int calculategCosts(Node previousNode) {
        if (diagonally) {
            return (previousNode.getgCosts()
                    + DIAGONALMOVEMENTCOST );
        } else {
            return (previousNode.getgCosts()
                    + BASICMOVEMENTCOST );
        }
    }

    /**public int calculategCosts(Node previousNode, int movementCost) {
        return (previousNode.getgCosts() + movementCost );
    }*/

    public int gethCosts() {
        return hCosts;
    }

    protected void sethCosts(int hCosts) {
        this.hCosts = hCosts;
    }

    /** Methode pour mettre a jour le cout estime du chemin */
    public void sethCosts(Node endNode){
    	this.sethCosts((absolute(this.getxPosition() - endNode.getxPosition())
    			+ absolute(this.getyPosition() - endNode.getyPosition()))
    			* BASICMOVEMENTCOST);
    }
    /** ---------------------- */

    /** Change le signe du parametre */
    private int absolute(int a) {
    	return a > 0 ? a : -a;
    }

    @Override
    public String toString() {
        return "(" + getxPosition() + ", " + getyPosition() + "): h: "
                + gethCosts() + " g: " + getgCosts() + " f: " + getfCosts();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.x;
        hash = 17 * hash + this.y;
        return hash;
    }

}
