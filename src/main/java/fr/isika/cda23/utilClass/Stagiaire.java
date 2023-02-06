package fr.isika.cda23.utilClass;

import java.util.ArrayList;
import java.util.Objects;

public class Stagiaire {
	private String nom;
	private String prenom;
	private String departement;
	private String formation;
	private String anneePromo;
	private Stagiaire doublon;
	public static int index = 0;
	private int filsG;
	private int filsD;
	private int indexStagiaire;

	// taille maximal dans le fichier binaire pour les attributs
	public final static int TAILLE_NOM_MAX = 21;
	public final static int TAILLE_PRENOM_MAX = 20;
	public final static int TAILLE_DEPARTEMENT_MAX = 3;
	public final static int TAILLE_FORMATION_MAX = 11;
	public final static int TAILLE_DATE_MAX = 4;
	public final static int TAILLE_INDEX_MAX = 4;
	public final static int TAILLE_OBJET_OCTET = 126;


	public Stagiaire(String nom, String prenom, String departement, String formation, String anneePromo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.formation = formation;
		this.anneePromo = anneePromo;
		this.doublon = null;
		this.filsD = -1;
		this.filsG = -1;
		this.indexStagiaire = 0;
	}

	public Stagiaire(int indexStagiaire, String nom, String prenom, String departement, String formation,
			String anneePromo) {
		super();
		this.indexStagiaire = indexStagiaire;
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.formation = formation;
		this.anneePromo = anneePromo;
		this.doublon = null;
		this.filsD = -1;
		this.filsG = -1;
	}

	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom + ", departement=" + departement + ", formation="
				+ formation + ", anneePromo=" + anneePromo + ", doublon=" + doublon + ", filsG=" + filsG + ", filsD="
				+ filsD + ", indexStagiaire=" + indexStagiaire + "]";
	}

	public int getIndexStagiaire() {
		return indexStagiaire;
	}

	public void setIndexStagiaire(int indexStagiaire) {
		this.indexStagiaire = indexStagiaire;
	}

	// Constructeur vide:
	public Stagiaire() {
		super();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getAnneePromo() {
		return anneePromo;
	}

	public void setAnneePromo(String anneePromo) {
		this.anneePromo = anneePromo;
	}

	public Stagiaire getDoublon() {
		return doublon;
	}

	public void setDoublon(Stagiaire doublon) {
		this.doublon = doublon;
	}

	public int getFilsG() {
		return filsG;
	}

	public void setFilsG(int filsG) {
		this.filsG = filsG;
	}

	public int getFilsD() {
		return filsD;
	}

	public void setFilsD(int filsD) {
		this.filsD = filsD;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anneePromo, departement, formation, nom, prenom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stagiaire other = (Stagiaire) obj;
		return Objects.equals(anneePromo, other.anneePromo) && Objects.equals(departement, other.departement)
				&& Objects.equals(formation, other.formation) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom);
	}

// 	méthode recursive pour ajouter un doublon qui est associé à l'arbre
	public void ajouterDoublon(Stagiaire stagiaire) {
		if (this.doublon == null)
			this.doublon = stagiaire;
		else
			this.doublon.ajouterDoublon(stagiaire);
	}

// 	méthode recursive pour ajouter tous les stagiaires dans une list depuis
// 	l'arbre;

	public void addList(ArrayList<Stagiaire> list) {
		if (this.doublon == null) {
			list.add(this);
		} else {
			list.add(this);
			this.doublon.addList(list);
		}
	}

// 	méthode recursive pour ajouter tous les stagiaires qui contiennet le meme ou
// 	une partie de leurs prenom
//	y compris les doublons dans une list depuis l'arbre;
	public void addListPrenom(ArrayList<Stagiaire> list, String prenom) {
		String prenomSansAccent = Utility.removeAccents(this.getPrenom().toLowerCase().replaceAll(Utility.regExp, ""));
		if (prenomSansAccent.contains(prenom)) {
			list.add(this);
		}
		if (this.doublon != null) {
			this.doublon.addListPrenom(list, prenom);
		}
	}

// 	méthode recursive pour ajouter tous les stagiaires qui ont le meme
// 	departement
//	y compris les doublons dans une list depuis l'arbre;
	public void addListDepartement(ArrayList<Stagiaire> list, String departement) {
		if (this.getDepartement().equals(departement)) {
			list.add(this);
		}
		if (this.doublon != null) {
			this.doublon.addListDepartement(list, departement);
		}
	}

// 	méthode recursive pour ajouter tous les stagiaires qui contiennet la meme ou
// 	une partie de leurs formation
//	y compris les doublons dans une list depuis l'arbre;
	public void addListFormation(ArrayList<Stagiaire> list, String formation) {
		if (this.getFormation().toLowerCase().replaceAll(" ", "").contains(formation)) {
			list.add(this);
		}
		if (this.doublon != null) {
			this.doublon.addListFormation(list, formation);
		}
	}

// 	méthode recursive pour ajouter tous les stagiaires qui ont le meme
// 	departement
//	y compris les doublons dans une list depuis l'arbre;
	public void addListAnneePromo(ArrayList<Stagiaire> list, String anneePromo) {
		if (this.getAnneePromo().equals(anneePromo)) {
			list.add(this);
		}
		if (this.doublon != null) {
			this.doublon.addListAnneePromo(list, anneePromo);
		}
	}

//  supprimer doublon
	public void supprimerDoublon(Stagiaire stagiaire) {
		if (this.getDoublon().getPrenom().compareTo(stagiaire.getPrenom()) == 0
				&& this.getDoublon().getFormation().compareTo(stagiaire.getFormation()) == 0) {
			this.setDoublon(this.doublon.getDoublon());
		} else {
			this.doublon.supprimerDoublon(stagiaire);
		}

	}

	/*
	 * Methode utilisee pour le fichier binaire, qui permet d'ajouter des espaces au
	 * nom du stagiaire
	 */
	public String nomLong() {
		String nomLong = "";
		if (nom.length() < TAILLE_NOM_MAX) {
			nomLong = nom;
			// Boucle pour ajouter des espaces au nom si celui ci est plus petit que la
			// taille du nom max
			for (int i = nom.length(); i < TAILLE_NOM_MAX; i++) {
				nomLong += " ";
			}
		} else {
			// prend la chaine de caractère compris entre les index 0 inclus et 21 exclus
			nomLong = nom.substring(0, TAILLE_NOM_MAX);
		}
		return nomLong;
	}

	/*
	 * Methode utilisee pour le fichier binaire, qui permet d'ajouter des espaces au
	 * prenom du stagiaire
	 */
	public String prenomLong() {
		String prenomLong = "";
		if (prenom.length() < TAILLE_PRENOM_MAX) {
			prenomLong = prenom;
			for (int i = prenom.length(); i < TAILLE_PRENOM_MAX; i++) {
				prenomLong += " ";
			}
		} else {
			// prend la chaine de caractère compris entre les index 0 inclus et 20 exclus
			prenomLong = prenom.substring(0, TAILLE_PRENOM_MAX);
		}
		return prenomLong;
	}

	/*
	 * Methode utilisée pour le fichier binaire, qui permet d'ajouter des espaces au
	 * departement du stagiaire
	 */
	public String departementLong() {
		String departementLong = "";
		if (departement.length() < TAILLE_DEPARTEMENT_MAX) {
			departementLong = departement;
			for (int i = departement.length(); i < TAILLE_DEPARTEMENT_MAX; i++) {
				departementLong += " ";
			}
		} else {
			// prend la chaine de caractere compris entre les index 0 inclus et 3 exclus
			departementLong = departement.substring(0, TAILLE_DEPARTEMENT_MAX);
		}
		return departementLong;
	}

	/*
	 * Methode utilisee pour le fichier binaire qui permet d'ajouter des esapces a
	 * la formation du stagiaire
	 */
	public String formationLong() {
		String formationLong = "";
		if (formation.length() < TAILLE_FORMATION_MAX) {
			formationLong = formation;
			for (int i = formation.length(); i < TAILLE_FORMATION_MAX; i++) {
				formationLong += " ";
			}
		} else {
			formationLong = formation.substring(0, TAILLE_FORMATION_MAX);
		}
		return formationLong;
	}

	/*
	 * Methode utilisee pour le fichier binaire, qui permet d'ajouter des espaces a
	 * l'annee de promo du stagiaire
	 */
	public String anneePromoLong() {
		String anneePromoLong = "";
		if (anneePromo.length() < TAILLE_DATE_MAX) {
			anneePromoLong = anneePromo;
			for (int i = anneePromo.length(); i < TAILLE_DATE_MAX; i++) {
				anneePromoLong += " ";
			}
		} else {
			anneePromoLong = anneePromo.substring(0, TAILLE_DATE_MAX);
		}
		return anneePromoLong;
	}

	public String filsGaucheLong() {
		String filsGauche = Integer.toString(this.filsG);
		if (filsGauche.length() < TAILLE_DATE_MAX) {
			for (int i = filsGauche.length(); i < TAILLE_DATE_MAX; i++) {
				filsGauche += " ";
			}
		}
		return filsGauche;
	}

	public String filsDroitLong() {
		String filsDroit = Integer.toString(this.filsD);
		if (filsDroit.length() < TAILLE_DATE_MAX) {
			for (int i = filsDroit.length(); i < TAILLE_DATE_MAX; i++) {
				filsDroit += " ";
			}
		}
		return filsDroit;
	}

	public String indexStagiaireLong() {
		String index = Integer.toString(this.indexStagiaire);
		if (index.length() < TAILLE_DATE_MAX) {
			for (int i = index.length(); i < TAILLE_DATE_MAX; i++) {
				index += " ";
			}
		}
		return index;
	}
}
