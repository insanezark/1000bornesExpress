package joueurs;

import java.util.*;

import jeu.PartieDeJeu;

import carte.attaque.Attaque;
import carte.attaque.LimiteVitesse;
import carte.bottes.Botte;
import carte.etape.Etape;
import carte.parade.FeuVert;


public class JeuSurTable extends Observable {
	
	private int nbKmParcouru;
	

	private ArrayList<Attaque> pileBataille;
	private ArrayList<LimiteVitesse> pileVitesse;
	private ArrayList<Botte> pileBotte;
	private ArrayList<Etape> pileEtape;
	private ArrayList<FeuVert> pileFeuVertInitial;
	

	public JeuSurTable() {
		
		this.pileBataille=new ArrayList<Attaque>();
		this.pileVitesse=new ArrayList<LimiteVitesse>();
		this.pileBotte=new ArrayList<Botte>();
		this.pileEtape=new ArrayList<Etape>();
		this.pileFeuVertInitial = new ArrayList<FeuVert>();
		
		this.nbKmParcouru=0;

	}
	
	
	/** 
	 * @param Carte
	 * 
	 */
	
	public void ajouterCarteEtape(Etape carte)
	{
		this.pileEtape.add(carte);
	}
	
	public void ajouterCarteBataille(Attaque carte)
	{
		if(this.pileBataille.isEmpty()) {
			
			this.pileBataille.add(carte);
	
		}
	}
	
	public void ajouterCarteVitesse(LimiteVitesse carte)
	{
		if(this.pileVitesse.isEmpty()) {
			this.pileVitesse.add(carte);
		}
	}
	
	public void ajouterCarteBotte(Botte carte)
	{
		this.pileBotte.add(carte);
	}
	
	public void ajouterFeuVertInitial(FeuVert feuVert) {
		this.pileFeuVertInitial.add(feuVert);
	}
	
	public void retirerCarteVitesse(LimiteVitesse carte)
	{
		this.pileVitesse.remove(carte);
	}
	
	public void retirerCarteBataille(Attaque carte)
	{
		this.pileBataille.remove(carte);
	}
	
	public int nbKmParcouru() {
			
		for(Iterator<Etape> it = this.pileEtape.iterator(); it.hasNext() ;) {
			this.nbKmParcouru += it.next().getNbKm();
		}
		
		return this.nbKmParcouru;
	}
	

	/**
	 * Pour savoir si ce jeu sur table a posé ou non un feu vert initiateur.
	 * @return
	 */
	public boolean isDemarrer() {
		return !(this.pileFeuVertInitial.isEmpty());
	}
	
	public int getNbKmParcouru() {
		return this.nbKmParcouru;
	}
	public void setNbKmParcouru(int nbKmParcouru) {
		
		this.nbKmParcouru = nbKmParcouru;
		PartieDeJeu partie = PartieDeJeu.getInstance();
		if(this.nbKmParcouru() >= partie.getNbKmMax()) {
			partie.setTermine(true);
		}
	}
	
	public ArrayList<Attaque> getPileBataille() {
		return this.pileBataille;
	}
	public ArrayList<LimiteVitesse> getPileVitesse() {
		return this.pileVitesse;
	}
	public ArrayList<Botte> getPileBotte() {
		return this.pileBotte;
	}
	public ArrayList<Etape> getPileEtape() {
		return this.pileEtape;
	}
	public ArrayList<FeuVert> getPileFeuVertInitial() {
		return this.pileFeuVertInitial;
	}


	public String toString() {
		return " pileBataille "+this.getPileBataille().toString() +"\n"+" pileBotte "+this.getPileBotte().toString() +"\n"+ "pileEtape "+this.getPileEtape().toString() + "\n"+" pileVitesse "+this.getPileVitesse().toString() +"\n"+ " pileFeuVertInitial "+this.getPileFeuVertInitial().toString();
	}

	
}