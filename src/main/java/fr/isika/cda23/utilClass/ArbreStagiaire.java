package fr.isika.cda23.utilClass;

import java.io.IOException;
import java.util.ArrayList;

import donneeFichier.Donnee;
import donneeFichier.GestionFichierBineaire;

public class ArbreStagiaire {

	private Noeud racine;
	public GestionFichierBineaire fichierLineaire;

	public ArbreStagiaire() {
		racine = null;
		fichierLineaire = new GestionFichierBineaire();
	}

	public Noeud getRacine() {
		return racine;
	}

	public void setRacine(Noeud racine) {
		this.racine = racine;
	}

	@Override
	public String toString() {
		return "ArbreStagiaire [racine=" + racine + "]";
	}

//	ajouter
	public void ajouterNoeud(Stagiaire stagiaire) throws IOException {
		if (racine == null) {
			racine = new Noeud(stagiaire);
			fichierLineaire.ajouterDansFichierLineaire(stagiaire);
		} else {
			racine.ajouter(stagiaire);
		}
	}

// 	recherche multicritère
	public ArrayList<Stagiaire> rechercheMulticritere(Stagiaire stagiaire) {
		if (racine == null) {
			System.out.println("l'arbre est vide il n'y a rien à afficher");
		} else {
			Noeud.list.clear();
			return racine.rechercheMulti(stagiaire);
		}
		return null;
	}

//	afficher
	public void afficherArbre() {
		if (racine == null) {
			System.out.println("l'arbre est vide il n'y a rien à afficher");
		} else {
			racine.parcoursInfixeArbre();
		}
	}

//	afficherList
	public void afficherList() {
		if (racine == null) {
			System.out.println("l'arbre est vide il n'y a rien à afficher");
		} else {
			racine.parcoursList();
		}
	}


	public void supprimer(Stagiaire sta) {
		// L'élément à supprimer ne peut pas être null.
		if (racine == null) {
			System.out.println("l'arbre est vide il n'y a rien à supprimer");
		}
		racine = supprimer(racine, sta);
	}

	/** * Supprime un élément en le recherchant à partir d'un noeud. */
	private Noeud supprimer(Noeud courant, Stagiaire stagiaire) {
		Noeud result = courant;
		int comparaisonNom = stagiaire.getNom().compareTo(courant.getCle().getNom());
		int comparaisonPrenom = stagiaire.getPrenom().compareTo(courant.getCle().getPrenom());
		int comparaisonFormation = stagiaire.getFormation().compareTo(courant.getCle().getFormation());
		if (comparaisonNom == 0) {
			if (comparaisonPrenom == 0 && comparaisonFormation == 0 && courant.getCle().getDoublon() == null) {
				if (courant.getFilsGauche() == null) {
					result = courant.getFilsDroit();
				} else if (courant.getFilsDroit() == null) {
					result = courant.getFilsGauche();
				} else {
					// Le noeud à supprimer n'est pas une feuille.
					// On recherche alors le noeud le plus à gauche, qui a donc la plus petite
					// valeur de son sous-arbre droit,
					// pour mettre cette valeur dans le noeud contenant celle à supprimer et qui
					// sera donc écrasée.
					// On supprime ensuite ce noeud dont la valeur vient d'être copiée.
					courant.modifValeur(plusPetiteValeurDuSousArbre(courant.getFilsDroit()));
					courant.modifDroit(supprimer(courant.getFilsDroit(), courant.getCle()));

				}

			} else if (comparaisonPrenom == 0 && comparaisonFormation == 0 && courant.getCle().getDoublon() != null) {
				courant.setCle(courant.getCle().getDoublon());
			} else if (comparaisonPrenom != 0 && courant.getCle().getDoublon() != null
					|| comparaisonFormation != 0 && courant.getCle().getDoublon() != null) {
				courant.getCle().supprimerDoublon(stagiaire);
			}
		} else if (comparaisonNom < 0) {
			// On parcourt le sous-arbre gauche
			courant.modifGauche(supprimer(courant.getFilsGauche(), stagiaire));
		} else {
			// On parcourt le sous-arbre droit
			courant.modifDroit(supprimer(courant.getFilsDroit(), stagiaire));
		}
		return result;
	}

	/**
	 * * Méthode pour renvoyer de l'élément le plus petit d'un sous-arbre (noeud).
	 */
	private Stagiaire plusPetiteValeurDuSousArbre(Noeud courant) {
		if (courant == null)
			throw new NullPointerException();
		if (courant.getFilsGauche() == null) {
			return courant.getCle();
		}
		return plusPetiteValeurDuSousArbre(courant.getFilsGauche());
	}

//	Modifier d'un noeud
	public void modifier(Stagiaire stagiaire, String nom, String prenom, String departement, String formation,
			String anneePromo) throws IOException {
		if (racine == null) {
			System.out.println("l'arbre est vide il n'y a rien à modifier");
		} else {
			racine.modifierNoeud(stagiaire, nom, prenom, departement, formation, anneePromo);
		}
	}
	
	

//	Ajouter dans le fichierLineaire
	public void ajouterDepuisFichierBineaire(Stagiaire stagiaire) {
		if (stagiaire.getIndexStagiaire() != -1) {
			if (racine == null) {
				racine = new Noeud(stagiaire);
			} else {
				racine.ajouterStagiaire(stagiaire);
			}
		}
	}

}
