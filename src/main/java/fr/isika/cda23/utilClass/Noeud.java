package fr.isika.cda23.utilClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import donneeFichier.Donnee;
import donneeFichier.GestionFichierBineaire;

public class Noeud {

	private Stagiaire cle;
	private Stagiaire stagiaireRem;
	private Noeud filsGauche;
	private Noeud filsDroit;
	public GestionFichierBineaire fichierLine = new GestionFichierBineaire();
	public ArbreStagiaire arbre = new ArbreStagiaire();

	public static ArrayList<Stagiaire> list = new ArrayList<>();

	Iterator<Stagiaire> it;

	public Noeud(Stagiaire cle) {
		this.cle = cle;
		this.filsGauche = null;
		this.filsDroit = null;
	}

	public Noeud() {

	}

	void modifGauche(Noeud noeud) {
		this.filsGauche = noeud;
	}

	void modifDroit(Noeud noeud) {
		this.filsDroit = noeud;
	}

	void modifValeur(Stagiaire cle) {
		this.cle = cle;
	}

	public Stagiaire getCle() {
		return cle;
	}

	public void setCle(Stagiaire cle) {
		this.cle = cle;
	}

	public Noeud getFilsGauche() {
		return filsGauche;
	}

	public void setFilsGauche(Noeud filsGauche) {
		this.filsGauche = filsGauche;
	}

	public Noeud getFilsDroit() {
		return filsDroit;
	}

	public void setFilsDroit(Noeud filsDroit) {
		this.filsDroit = filsDroit;
	}


	// Ajouter un stagiaire
	public void ajouter(Stagiaire stagiaire) throws IOException {
		stagiaire.setNom(stagiaire.getNom().toUpperCase());
		int comparaison = stagiaire.getNom().compareTo(this.cle.getNom());
		if (comparaison < 0) {
			// le nom à ajouter est plus petit on va à gauche
			if (this.filsGauche == null) {
				this.filsGauche = new Noeud(stagiaire);
				this.getCle().setFilsG(this.getFilsGauche().getCle().getIndexStagiaire());
				fichierLine.ajouterDansFichierLineaire(stagiaire);
			} else {
				this.filsGauche.ajouter(stagiaire);
			}

//			le nom à ajouter est égal au nom de la cle, on regarde si la possède un doublon(qui a le meme nom);
		} else if (comparaison == 0 && this.cle.getDoublon() == null) {
			this.cle.setDoublon(stagiaire);
			fichierLine.ajouterDansFichierLineaire(stagiaire);
		} else if (comparaison == 0 && this.cle.getDoublon() != null) {
			this.cle.getDoublon().ajouterDoublon(stagiaire);
			fichierLine.ajouterDansFichierLineaire(stagiaire);
		} else {
			if (this.filsDroit == null) {
				this.filsDroit = new Noeud(stagiaire);
				this.getCle().setFilsD(this.getFilsDroit().getCle().getIndexStagiaire());
				fichierLine.ajouterDansFichierLineaire(stagiaire);
			} else {
				this.filsDroit.ajouter(stagiaire);
			}
		}
	}

// 	Rechercher stagiaire par multicritere
	public ArrayList<Stagiaire> rechercheMulti(Stagiaire stagiaire) {
		if (!stagiaire.getNom().equals("")) {
			parcoursListParNom(stagiaire.getNom());
		}
		if (!stagiaire.getPrenom().equals("") && list.size() > 1) {
			it = list.iterator();
			while (it.hasNext()) {
				if (!it.next().getPrenom().toLowerCase().contains(stagiaire.getPrenom().toLowerCase())) {
					it.remove();
				}
			}

		} else if (!stagiaire.getPrenom().equals("") && list.size() == 0) {
			parcoursListParPrenom(stagiaire.getPrenom());
		}

		if (!stagiaire.getDepartement().equals("") && list.size() > 1) {
			it = list.iterator();
			while (it.hasNext()) {
				if (!it.next().getDepartement().equals(stagiaire.getDepartement())) {
					it.remove();
				}
			}
		} else if (!stagiaire.getDepartement().equals("") && list.size() == 0) {

			parcoursListParDepartement(stagiaire.getDepartement());

		} else if (stagiaire.getAnneePromo().equals("") && stagiaire.getFormation().equals("")
				&& stagiaire.getDepartement().equals("") && stagiaire.getPrenom().equals("")
				&& stagiaire.getNom().equals("") && list.size() == 0) {

			parcoursListParDepartement(stagiaire.getDepartement());
		}
		if (!stagiaire.getFormation().equals("") && list.size() > 1) {
			it = list.iterator();
			while (it.hasNext()) {
				if (!it.next().getFormation().toLowerCase().contains(stagiaire.getFormation().toLowerCase())) {
					it.remove();
				}
			}
		} else if (!stagiaire.getFormation().equals("") && list.size() == 0) {
			parcoursListFormation(stagiaire.getFormation());
		}
		if (!stagiaire.getAnneePromo().equals("") && list.size() > 1) {
			it = list.iterator();
			while (it.hasNext()) {
				if (!it.next().getAnneePromo().equals(stagiaire.getAnneePromo())) {
					it.remove();
				}
			}
		} else if (!stagiaire.getAnneePromo().equals("") && list.size() == 0) {

			parcoursListParAnneePromo(stagiaire.getAnneePromo());

		}
		return list;
	}

//	parcourt l'arbre
	public void parcoursInfixeArbre() {
		if (this.filsGauche != null) {
			this.filsGauche.parcoursInfixeArbre();
		}
		System.out.println(cle);
		if (this.filsDroit != null) {
			this.filsDroit.parcoursInfixeArbre();
		}
	}

//	creation d'une liste de tous les stagiaires qui contiennet la meme partie de leurs noms 
//	ou qui ont le meme nom d'après l'arbre 
	public ArrayList<Stagiaire> parcoursListParNom(String varEntree) {

		// transformer toutes les lettres de varEntree en miniscule et effacer l'accent
		String lowerEntree = lowerEntree = Utility
				.removeAccents(varEntree.toLowerCase().replaceAll(Utility.regExp, ""));
		// transformer toutes les lettres du nom de cle en miniscule et effacer l'accent
		String nomSansAccent = Utility.removeAccents(this.cle.getNom().toLowerCase().replaceAll(Utility.regExp, ""));

		if (this.filsGauche != null) {
			this.filsGauche.parcoursListParNom(lowerEntree);
		}
		if (nomSansAccent.contains(lowerEntree)) {

			this.cle.addList(list);
//			for (Stagiaire i : list) {
//				System.out.println(i);
//			}
		}
		if (this.filsDroit != null) {
			this.filsDroit.parcoursListParNom(lowerEntree);
		}

		return list;

	}

//	creation d'une liste de tous les stagiaires qui contiennet la meme partie de leurs prenoms 
//	ou qui ont le meme prenom d'après l'arbre
	public ArrayList<Stagiaire> parcoursListParPrenom(String varEntree) {

		String lowerEntree = Utility.removeAccents(varEntree.toLowerCase().replaceAll(Utility.regExp, ""));

		String prenomSansAccent = Utility
				.removeAccents(this.cle.getPrenom().toLowerCase().replaceAll(Utility.regExp, ""));

		if (this.filsGauche != null) {
			this.filsGauche.parcoursListParPrenom(lowerEntree);
		}
		if (prenomSansAccent.contains(lowerEntree)) {
			list.add(this.cle);

			if (this.cle.getDoublon() != null) {
				this.cle.getDoublon().addListPrenom(list, lowerEntree);
			}
		}
		if (this.filsDroit != null) {
			this.filsDroit.parcoursListParPrenom(lowerEntree);
		}
		return list;
	}

//	creation d'une liste de tous les stagiaires qui ont le meme département d'après l'arbre
	public ArrayList<Stagiaire> parcoursListParDepartement(String varEntree) {
		if (this.filsGauche != null) {
			this.filsGauche.parcoursListParDepartement(varEntree);
		}

		if (this.cle.getDepartement().equals(varEntree)) {
			list.add(this.cle);
			if (this.cle.getDoublon() != null) {
				this.cle.getDoublon().addListDepartement(list, varEntree);
			}
		}
		if (this.filsDroit != null) {
			this.filsDroit.parcoursListParDepartement(varEntree);
		}
		return list;
	}

//	creation d'une liste de tous les stagiaires qui continnent la meme formation d'après l'arbre
	public ArrayList<Stagiaire> parcoursListFormation(String varEntree) {
		String lowerEntree = varEntree.toLowerCase();
		if (this.filsGauche != null) {
			this.filsGauche.parcoursListFormation(lowerEntree);
		}

		if (this.cle.getFormation().toLowerCase().replaceAll(" ", "").contains(lowerEntree)) {
			list.add(this.cle);
			if (this.cle.getDoublon() != null) {
				this.cle.getDoublon().addListFormation(list, lowerEntree);
			}
		}
		if (this.filsDroit != null) {
			this.filsDroit.parcoursListFormation(lowerEntree);
		}
		return list;
	}

//	creation d'une liste de tous les stagiaires qui ont la meme anneePromo d'après l'arbre
	public ArrayList<Stagiaire> parcoursListParAnneePromo(String varEntree) {
		if (this.filsGauche != null) {
			this.filsGauche.parcoursListParAnneePromo(varEntree);
		}

		if (this.cle.getAnneePromo().equals(varEntree)) {
			list.add(this.cle);
			if (this.cle.getDoublon() != null) {
				this.cle.getDoublon().addListAnneePromo(list, varEntree);
			}
		}
		if (this.filsDroit != null) {
			this.filsDroit.parcoursListParAnneePromo(varEntree);
		}
		return list;
	}


//  modifier d'un noeud
	public void modifierNoeud(Stagiaire stagiaire, String nom, String prenom, String departement, String formation,
			String anneePromo) throws IOException {
		arbre = GestionFichierBineaire.arbre;
		if (arbre == null) {
			arbre = Donnee.arbre;
		}
		arbre.supprimer(stagiaire);
		arbre.ajouterNoeud(new Stagiaire(nom, prenom, departement, formation, anneePromo));

	}

// 	creation d'une liste de tous les stagiaires d'après l'arbre 
	public void parcoursList() {
		if (this.filsGauche != null) {
			this.filsGauche.parcoursList();
		}
		if (cle != null)
			cle.addList(list);
//		for (Stagiaire i : list)
//			System.out.println(i);
		if (this.filsDroit != null) {
			this.filsDroit.parcoursList();
		}

	}// creation d'une fichier de tous les stagiaires d'après l'arbre

	public void ajouterStagiaire(Stagiaire stagiaire) {
		stagiaire.setNom(stagiaire.getNom().toUpperCase());
		int comparaison = stagiaire.getNom().compareTo(this.cle.getNom());
		if (comparaison < 0) {
			// le nom à ajouter est plus petit on va à gauche
			if (this.filsGauche == null) {
				this.filsGauche = new Noeud(stagiaire);
				this.getCle().setFilsG(this.getFilsGauche().getCle().getIndexStagiaire());
			} else {
				this.filsGauche.ajouterStagiaire(stagiaire);
			}

//			le nom à ajouter est égal au nom de la cle, on regarde si la possède un doublon(qui a le meme nom);
		} else if (comparaison == 0 && this.cle.getDoublon() == null) {
			this.cle.setDoublon(stagiaire);
		} else if (comparaison == 0 && this.cle.getDoublon() != null) {
			this.cle.getDoublon().ajouterDoublon(stagiaire);
		} else {
			if (this.filsDroit == null) {
				this.filsDroit = new Noeud(stagiaire);
				this.getCle().setFilsD(this.getFilsDroit().getCle().getIndexStagiaire());
			} else {
				this.filsDroit.ajouterStagiaire(stagiaire);
			}
		}
	}

	@Override
	public String toString() {
		return "Noeud [cle=" + cle + ", filsGauche=" + filsGauche + ", filsDroit=" + filsDroit + "]";
	}

}
