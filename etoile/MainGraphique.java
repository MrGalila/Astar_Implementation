package etoile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Main un peu plus cool avec une carte au Tresor, il utilise A* afin de te mener au tresor tout en evitant les dangers
 *
 */
public class MainGraphique extends JFrame implements MouseListener{
	private Panel pan = new Panel();
	private int height = 90;
	private int width = 90;
	
	private Node depart;
	private Node tresor; // arrivée
	List<Node> ns;
	Graph gr;
	

	private Image image;

	
	private Image back;
	
	/**
	 * Main initialisant un objet MainGraphique, héritant d'une fenêtre JFrame
	 */
	public static void main(String[] args) {
		MainGraphique fen = new MainGraphique();
		fen.setTitle("Carte au trésor");
		fen.setResizable(false);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.pack();
		fen.setVisible(true);
		
		
		
	}
		
	/**
	 * Constructeur de notre classe MainGraphique, il s'occupe d'utiliser l'algo A* et de l'afficher graphiquement
	 */
	public MainGraphique() {
		
		gr = new Graph(width, height);
		
		depart = new Node(62, 85);
		tresor = new Node(31, 32);
		
		/**  Le parametre corresponds au pourcentage d'obstacles */
		gr.setObstacleAlea(5);
		
		gr.findPath(depart, tresor);
		ns = gr.calcPath(depart, tresor);
				
		pan.setPreferredSize(new Dimension(height*10, width*10));
		pan.addMouseListener(this);
		
		this.add(pan);
		
	}
	
	/**
	 * @author Ziehl Benjamin
	 *
	 */
	class Panel extends JPanel {
		
		/** On redefinit la classe JPanel pour en avoir une qui fait l'interpretation graphique de notre chemin genere par A* */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
        	
			try {
				image= ImageIO.read(new File("SkeletonHead.png"));
				back = ImageIO.read(new File("carteTresor.jpg"));
			}catch(IOException e) {
				System.out.println("Y'a une image qui marche pas, t'est nul");
			}
			
			g.drawImage(back,-120,-80,this);
			
            
			for(int i = 0; i < width*10; i = i+10) {
				for(int j = 0; j < height*10; j = j+10) {
					if( gr.getNode(i/10, j/10).equals(tresor)) {	
						g.setColor(Color.red);
						g.fillRect(j, i, 10, 10);
					}
					else if(gr.getNode(i/10, j/10).equals(depart)) {
						g.setColor(Color.yellow);
						g.fillRect(j, i, 10, 10);
					}
					else if(ns.contains(gr.getNode(i/10, j/10))) {
						g.setColor(Color.orange);
						g.fillRect(j, i, 10, 10);
					}
					else if(gr.getNode(i/10, j/10).isAccessible()) {
						/** On ne fait rien pour ne pas recouvrir l'image en dessous, ici la carte mais si on veut faire une interface graphique différente on peu
						 * car notre boucle prend en compte chaque possibilitées. */
					}
					/** J'ai définis à la main et grossièrement les dimensions de la carte au trésor que j'ai chargé dans l'interface graphique pour que les
					 * têtes de morts ne s'affiche que sur l'île au trésor. */
					else if(i>200 && j>200 && i<550 && j<850 || i>549 && j>200 && i<700 && j<450 || i>549 && j>600 && i<650 && j<850){
				
						g.drawImage(image,j,i,this);	
						
					}
				}
			}
		}
	}

	/** Si on clique sur la carte, cela change le noeud 'cible' et ainsi change le chemin, cela a aussi pour effet de changer la disposition des obstacles*/
	public void mouseClicked(MouseEvent e) {
		tresor.setCoordinates(e.getY()/10, e.getX()/10);
		gr.init();
		gr.findPath(depart, tresor);
		ns = gr.calcPath(depart, tresor);
		gr.setObstacleAlea(5);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
