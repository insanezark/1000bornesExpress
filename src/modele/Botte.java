package modele;
import java.util.*;

import controleur.*;


public class Botte extends Carte  {
	
	protected int Kilometres;
	
	
	
	
	

	//constructeur
	public Botte() {
	
	}
	
	//Augmenter les km lors de la pose d'une botte
	public void augmenterKmParcourus(JeuSurTable jeu){
		Kilometres=jeu.getNbKilometreParcouru();
		jeu.setNbKilometreParcouru(Kilometres+100);
		
	}
	
	public void coupFourre(){
		
		
		
	}
	
	
	
}
