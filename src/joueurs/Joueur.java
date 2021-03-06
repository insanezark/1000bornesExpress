package joueurs;

import java.util.Iterator;
import java.util.Observable;

import tasDeCartes.Carte;
import tasDeCartes.Defausse;
import tasDeCartes.Talon;
import tasDeCartes.TasDeCarte;

import jeu.PartieDeJeu;



import carte.bottes.Botte;
/**
 * Represente les parametres du joueur
 * @author Damien
 *
 */

public abstract class Joueur extends Observable {

	protected static Joueur[] joueur;
	protected JeuSurTable jeuSurTable;
	protected JeuEnMain jeuEnMain;
	protected int numPassage;
	protected String nom;

	
	
	public Joueur (String nomJoueur, int numPassage){
		this.nom=nomJoueur;
		this.numPassage=numPassage;

		this.jeuEnMain = new JeuEnMain(this);
		this.jeuSurTable = new JeuSurTable(this);
	}
	
	public abstract void jouer();

	public abstract void coupFourre(Botte botte) ;
	
	/**
	 * determine si le joueur est gagnant
	 * 
	 */
	public boolean isGagnant() {
		PartieDeJeu partie = PartieDeJeu.getInstance();
		if(this.getJeuSurTable().getNbKmParcouruTotal() >= partie.getNbKmMax() ) {
			return true;
		} 
		return false;
	}
	
	/**
	 * Verifie si un coup fourre est realisable
	 * 
	 */
	public Botte canCoupFourre() {
		for(Iterator<Carte> it = this.getJeuEnMain().getMain().iterator() ; it.hasNext() ; ) {
			Carte carte = it.next();
			if(carte instanceof Botte) {
				if(((Botte) carte).isJouableCoupFourre(this.getJeuSurTable())) {
					return (Botte) carte;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Permet de piocher dans le talon, puis dans la defausse
	 * @param pioche
	 */
	public void piocher(TasDeCarte pioche) {
		if(pioche instanceof Talon) {
			this.piocherTalon();
		} else {
			this.piocherDefausse();
		}
			
	}
	
	/**
	 * Pioche dans le talon
	 */
	public void piocherTalon() {
		Talon talon = Talon.getInstance();
		this.jeuEnMain.ajouterCarte(talon.piocher());
		
	}
	
	/**
	 * Pioche dans la defausse
	 */
	public void piocherDefausse() {
		Defausse defausse = Defausse.getInstance();
		this.jeuEnMain.ajouterCarte(defausse.piocher());
	}
	


	/**
	 * Permet de defausser une carte
	 * @param carte
	 */
	public void defausser(Carte carte) {
		Defausse defausse = Defausse.getInstance();
		this.jeuEnMain.retirerCarte(carte);  
		defausse.ajouterCarte(carte);  
		PartieDeJeu.getInstance().setNumeroJoueurActuel(this.getNumPassage() ); 

	}
	
	
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
		
	}
	
	public int getNumPassage() {
		return this.numPassage;
	}

	public void setNumPassage(int numPassage) {
		this.numPassage = numPassage;
		
	}
	
	public JeuSurTable getJeuSurTable() {
		return this.jeuSurTable;
	}
	
	public JeuEnMain getJeuEnMain() {
		return this.jeuEnMain;
	}
	
	public String toString() {
		return this.nom;
	}

}
