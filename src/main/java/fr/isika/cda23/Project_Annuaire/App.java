package fr.isika.cda23.Project_Annuaire;

import java.io.File;
import java.io.IOException;

import donneeFichier.Donnee;
import donneeFichier.GestionFichierBineaire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	@Override
	public void init() throws IOException { // pré-traitements
		File file = new File("src/main/java/ressource/STAGIAIRES.bin");
		if (file.exists()) {
			GestionFichierBineaire bin = new GestionFichierBineaire();
			bin.lireFichier();
		} else {
			Donnee donneeFicher = new Donnee();
			donneeFicher.extraireDonne();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {

		EcranAuthentification root = new EcranAuthentification();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Annuaire");
		stage.setWidth(800);
		stage.setHeight(800);
		stage.show();

	}

	public static void main(String[] args) {
		launch();

	}

}