package joueurs;

import java.util.Iterator;

import jeu.Menu;
import jeu.PartieDeJeu;



import carte.attaque.Attaque;
import carte.bottes.Botte;


import strategie.Strategy;
import tasDeCartes.Carte;
import tasDeCartes.Defausse;
import tasDeCartes.TasDeCarte;
import vue.CmdLineInterface;

public class Robot extends Joueur {
	
	public Robot(String nomJoueur, int numPassage, Strategy stratInitiale) {
		super(nomJoueur, numPassage);
		this.strat = stratInitiale;
	
	}

	private Strategy strat;

	public void jouer() {
		this.piocher(this.choixPioche());
		CmdLineInterface cmd = new CmdLineInterface();//TODO A VIRER
		cmd.afficherMainJoueur(this);//TODO A VIRER
		if(this.choixDefausser()) {
			
			this.defausser(this.choixCarte(true)); //Le robot choisit une carte à défausser.
						
			PartieDeJeu partie = PartieDeJeu.getInstance();
			partie.setNumeroJoueurActuel(this.getNumPassage() ); //Pour que ce soit au joueur suivant de jouer
			this.setChanged();
			this.notifyObservers(partie);
			
		} else {
			Carte carteChoisie = this.choixCarte(false);
			if(carteChoisie instanceof Attaque) {
				Joueur adversaire = this.choixCible((Attaque) carteChoisie);
				carteChoisie.jouer(this, adversaire);
				
				//On regarde si le joueur peut faire un coup fourre
				if(adversaire.canCoupFourre()) {
					if(adversaire instanceof Robot) {	
						adversaire.coupFourre();
					}
					PartieDeJeu partie = PartieDeJeu.getInstance();
					partie.setNumeroJoueurActuel(adversaire.getNumPassage() - 1); 
					this.setChanged();
					this.notifyObservers(partie);
					//Pour que ce soit au joueur qui a fait un coup fourre de jouer

				} else {
					PartieDeJeu partie = PartieDeJeu.getInstance();
					partie.setNumeroJoueurActuel(this.getNumPassage() ); //Pour que ce soit au joueur suivant de jouer
					this.setChanged();
					this.notifyObservers(partie);
				}
			} else {
				carteChoisie.jouer(this, null);
				
				PartieDeJeu partie = PartieDeJeu.getInstance();
				partie.setNumeroJoueurActuel(this.getNumPassage() ); //Pour que ce soit au joueur suivant de jouer
				this.setChanged();
				this.notifyObservers(partie);
			}
			
		}

	}
	
	public Strategy getStrat() {
		return strat;
	}

	/**
	 * Renvoie faux si l'action choisie est de jouer une carte.
	 * Renvoie vrai si l'action choisie est de défausser une carte.
	 * Le robot choisira de défausser uniquement quand il n'aura aucune carte à jouer.
	 * @return boolean
	 */
	public boolean choixDefausser(){ 

		for(Iterator<Carte> it = this.jeuEnMain.getMain().iterator(); it.hasNext() ; ) {
			//On teste toutes les cartes de la main du joueur
			Carte carte = it.next();
			Menu menu = Menu.getInstance();
			if(carte instanceof Attaque) {
				for(Iterator<Joueur> it1 = menu.getJoueurs().iterator() ; it1.hasNext() ; ) {
					//Pour chaque carte Attaque, on tente de les jouer sur tous les joueurs autre que nous même.
					Joueur joueur = it1.next();
					if(carte.isJouable(this, joueur) && joueur != this) {
						//Si une des carte Attaque du jeu du Robot est jouable alors il jouera et ne defaussera pas.
						return false;
					}
				}
			} else {
				if(carte.isJouable(this, null)) {
					//Si une des carte autre que Attaque du jeu du Robot est jouable alors il jouera et ne defaussera pas.
					return false;
				}
			}
		}
		//Si ce n'est pas le cas, il defausse et cette methode renvoie alors false.
		return true;
	}
	
	public Carte choixCarte(boolean defausser) {
		return this.strat.choixCarte(this, defausser);		
	}

	
	public Joueur choixCible(Attaque carte) {
		return this.strat.choixCible(this, carte);
	}
	
	@Override
	public void coupFourre() {
		//On va tout d'abord rechercher la botte à jouer
		for(Iterator<Carte> it = this.getJeuEnMain().getMain().iterator() ; it.hasNext() ; ) {
			Carte carte = it.next();
			if(carte instanceof Botte) {
				if(((Botte) carte).isJouableCoupFourre(this.getJeuSurTable())) {
					//Des qu'on trouve la bonne botte, on la joue en tant que coup Fourre.
					((Botte)carte).coupFourre(this);
				}
			}
		}
		PartieDeJeu partie = PartieDeJeu.getInstance();
		partie.setNumeroJoueurActuel(this.getNumPassage() - 1); //Pour que le robot rejoue
		
		this.setChanged();
		this.notifyObservers(partie);
	}

	
	public TasDeCarte choixPioche() {
		return this.strat.choixPioche();
	}
	
	public String toString() {
		return ""+this.strat;
	}


}
