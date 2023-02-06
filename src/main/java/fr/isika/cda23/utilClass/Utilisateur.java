package fr.isika.cda23.utilClass;

import java.io.BufferedReader;
import java.io.FileReader;

public class Utilisateur {

	private String identifiant;
	private String motDePasse;
	private final String fichierLogin= "src/main/java/ressource/fichierLoginAdmin.txt";
	public static String role;
	
	
	public Utilisateur() {
		super();
	}

	public Utilisateur(String id, String mdp) {
		super();
		this.identifiant = id;
		this.motDePasse = mdp;
	}

	@Override
	public String toString() {
		return "Utilisateur [identifiant=" + identifiant + ", motDePasse="
				+ motDePasse + "]";
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String id) {
		this.identifiant = id;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String mdp) {
		this.motDePasse = mdp;
	}
	public void authentification() throws Exception {
	FileReader frAdmin = new FileReader(fichierLogin);
	BufferedReader brAdmin=new BufferedReader (frAdmin);
	String ligneAdmin=brAdmin.readLine();
	String[]tabAdmin=ligneAdmin.split(";");
	this.setIdentifiant(tabAdmin[0]);
	this.setMotDePasse(tabAdmin[1]);
	brAdmin.close();	frAdmin.close();
	
	}
}
