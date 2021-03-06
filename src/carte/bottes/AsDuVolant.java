package carte.bottes;

import joueurs.JeuSurTable;
import carte.attaque.Accident;
import carte.attaque.Attaque;
/**
 * Represente la carte botte As du volant
 * On y trouve les booleens precisant si elle est jouable en coup fourré et quelle attaque elle bloque
 * 
 *
 */
public class AsDuVolant extends Botte {

	public AsDuVolant() {
		
	}
	
	/**
	 * Indique si elle est jouable en coup fourré
	 * 
	 */
	public boolean isJouableCoupFourre(JeuSurTable notreJeu) {
		if(notreJeu.getPileBataille().isEmpty() == false) {	
			if(notreJeu.getPileBataille().get(0) instanceof Accident) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * indique la compatibilité avec l'Attaque indiqué.
	 */
	public boolean isCompatible(Attaque attaque) {
		if(attaque instanceof Accident) {
			return true;
		} else { return false; }
	}
	
	/**
	 * Retourne "AsDuVolant"
	 */
	public String toString() {
		return "AsDuVolant";
	}

}
