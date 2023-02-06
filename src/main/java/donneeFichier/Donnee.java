package donneeFichier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.isika.cda23.utilClass.ArbreStagiaire;
import fr.isika.cda23.utilClass.Stagiaire;

public class Donnee {

	ArrayList<String> list;
	Stagiaire stagiaire;
	ArrayList<Stagiaire> listStagiaire;
	public static ArbreStagiaire arbre;

	public Donnee() {
		list = new ArrayList<>();
		listStagiaire = new ArrayList<>();
		arbre = new ArbreStagiaire();
	}

	public void extraireDonne() {
		try {
			FileReader fr = new FileReader("src/main/java/ressource/STAGIAIRES.DON");

			BufferedReader br = new BufferedReader(fr);

			String contenu = "";

//				insérer ligne par ligne du fichier DON dans list
			while ((contenu = br.readLine()) != null) {

				list.add(contenu);

			}

//				instancier tous les stagiaires et les insérer dans listStagiaire
			for (int i = 0; i < list.size(); i += 6) {

				Stagiaire stagiaire = new Stagiaire(list.get(i), list.get(i + 1), list.get(i + 2), list.get(i + 3),
						list.get(i + 4));
				listStagiaire.add(stagiaire);
			}

			for (Stagiaire i : listStagiaire) {
				arbre.ajouterNoeud(i);
			}


			br.close();
			fr.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public ArrayList<Stagiaire> getListStagiaire() {
		return listStagiaire;
	}

	public void setListStagiaire(ArrayList<Stagiaire> listStagiaire) {
		this.listStagiaire = listStagiaire;
	}
}
