package donneeFichier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import fr.isika.cda23.utilClass.ArbreStagiaire;
import fr.isika.cda23.utilClass.Stagiaire;

public class GestionFichierBineaire {
	Stagiaire stagiaire;
	ArrayList<Stagiaire> listStagiaire;
	public static ArbreStagiaire arbre;

	public GestionFichierBineaire() {

	}

	static RandomAccessFile raf;

	static int total;
	static int nbCara = 63;
	static int indexS = 4;
	static int nom = 21;
	static int prenom = 20;
	static int dep = 3;
	static int form = 11;
	static int date = 4;
	static int posNom = indexS + nom;
	static int posPre = indexS + nom + prenom;
	static int posDep = indexS + nom + prenom + dep;
	static int posAnnee = indexS + nom + prenom + dep + date;
	static int j = 0;

	public void ajouterDansFichierLineaire(Stagiaire stagiaire) throws IOException {
		// permet de créer ou d'ouvrir le fichier bin si il existe deja
		// rw : mode lecture/ecriture
		stagiaire.setIndexStagiaire(Stagiaire.index);
		Stagiaire.index++;
		try {
			raf = new RandomAccessFile("src/main/java/ressource/STAGIAIRES.bin", "rw");
			// positionne le pointeur a la fin du document
			raf.seek(raf.length());

			raf.writeChars(stagiaire.indexStagiaireLong());
			raf.writeChars(stagiaire.nomLong());
			raf.writeChars(stagiaire.prenomLong());
			raf.writeChars(stagiaire.departementLong());
			raf.writeChars(stagiaire.anneePromoLong());
			raf.writeChars(stagiaire.formationLong());
//			raf.writeChars(stagiaire.filsGaucheLong());
//			raf.writeChars(stagiaire.filsDroitLong());
//			System.out.println(raf.length());
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void lireFichier() throws IOException {
		raf = new RandomAccessFile("src/main/java/ressource/STAGIAIRES.bin", "rw");
		arbre = new ArbreStagiaire();
		total = (int) ((raf.length()) / 2);

		while (j < total) {
			String index = "";
			String nom = "";
			String prenom = "";
			String departement = "";
			String formation = "";
			String anneePromo = "";

			for (int i = j; i < nbCara; i++) {
				if (i < indexS) {
					index += raf.readChar();

				}
				if (i >= indexS && i < posNom) {

					nom += raf.readChar();
				}

				if (i >= posNom && i < posPre) {

					prenom += raf.readChar();
				}
				if (i >= posPre && i < posDep) {

					departement += raf.readChar();
				}
				if (i >= posDep && i < posAnnee) {

					anneePromo += raf.readChar();
				}
				if (i >= posAnnee && i < nbCara) {

					formation += raf.readChar();
				}

			}

			stagiaire = new Stagiaire(Integer.parseInt(index.trim()), nom.trim(), prenom.trim(), departement.trim(),
					formation.trim(), anneePromo.trim());

//Extraire du fichier Bin et ajouter les stagiaires dans l'arbre
			arbre.ajouterDepuisFichierBineaire(stagiaire);

			j = j + 63;
			indexS = indexS + 63;
			posNom += 63;
			posPre += 63;
			posDep += 63;
			posAnnee += 63;
			nbCara += 63;
		}

		raf.seek(raf.length() - 126);
		String indexSta = "";

		long pos = raf.length() - 126;

		long posIndex = raf.length() - 122;

		for (int i = (int) (raf.length() - 126); i < raf.length() - 122; i++) {
			indexSta += raf.readChar();

		}

		while (indexSta.contains("-1")) {
			raf.seek(pos);
			indexSta = "";
			pos -= 126;
			posIndex -= 126;
			for (int i = (int) pos; i < posIndex; i++) {
				indexSta += raf.readChar();
			}
		}

		Stagiaire.index = Integer.parseInt(indexSta) + 1;


		raf.close();
	}

	public static void supprimerStagiaire(Stagiaire stagiaire) throws IOException {
		raf = new RandomAccessFile("src/main/java/ressource/STAGIAIRES.bin", "rw");

		int index = stagiaire.getIndexStagiaire();

		String indexSta = "";

		long pos = 0;

		long posIndex = 4;

		String indexToString = Integer.toString(index);

		while (!(indexToString.compareTo(indexSta) == 0)) {
			raf.seek(pos);
			indexSta = "";
			for (int i = (int) pos; i < posIndex; i++) {
				indexSta += raf.readChar();
			}
			pos += 126;
			posIndex += 126;
			indexSta = indexSta.trim();
		}

		if (indexToString.compareTo(indexSta) == 0) {

			raf.seek(pos-126);


			raf.writeChars("-1  ");
		}

		raf.close();
	}

}
