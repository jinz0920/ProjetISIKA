package fr.isika.cda23.Project_Annuaire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.isika.cda23.utilClass.Utilisateur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EcranAuthentification extends GridPane {

	private Label lblMessage = new Label();
	private String checkUser, checkPw;
	// Création du profil administrateur
	private Utilisateur admin = new Utilisateur("admin", "mdp");
	// création du profil utilisateur
	private Utilisateur util = new Utilisateur("util", "mdp");

	public EcranAuthentification() {
		super();

		// insertion de plusisuers images
		Image imageLogin;
		try {
			imageLogin = new Image(new FileInputStream("src/main/java/ressource/login.png"));
			ImageView imageView = new ImageView(imageLogin);
			// modification de la taille de l'image
			imageView.setFitHeight(300);
			imageView.setFitWidth(300);
			this.add(imageView, 2, 0);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Image imagePetitLogin;
		try {
			imagePetitLogin = new Image(new FileInputStream("src/main/java/ressource/petitLogin.png"));
			ImageView imageView = new ImageView(imagePetitLogin);
			// modification de la taille de l'image
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			this.add(imageView, 0, 4);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Image imageCadenas;
		try {
			imageCadenas = new Image(new FileInputStream("src/main/java/ressource/Cadenas.png"));
			ImageView imageView = new ImageView(imageCadenas);
			// modification de la taille de l'image
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			this.add(imageView, 0, 5);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Labels, TextFields et Button du formulaire:
		Label labelAuthentification = new Label("Authentification");
		// En tete fenetre

		HBox hbTop = new HBox();
		hbTop.getChildren().add(labelAuthentification);
		labelAuthentification.getStyleClass().add("auth");
		Label labelIdentifiant = new Label("Identifiant:");
		TextField txtIdentifiant = new TextField();
		// txtIdentifiant.setId(null);
		Label labelMdp = new Label("Mot de passe:");
		PasswordField txtMdp = new PasswordField();
		Button btnValider = new Button("Valider");
		Button btnQuitter = new Button("Quitter");

		// Ajout d'une image dans le btnQuitter
		Image imageExit;
		try {
			imageExit = new Image(new FileInputStream("src/main/java/ressource/Exit.png"));
			ImageView quitter = new ImageView(imageExit);
			// modification de la taille de l'image
			quitter.setFitHeight(20);
			quitter.setFitWidth(20);

			btnQuitter.setGraphic(quitter);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Ajout d'une image dans le btnValider
		Image imageValider;
		try {
			imageValider = new Image(new FileInputStream("src/main/java/ressource/Entry.png"));
			ImageView valider = new ImageView(imageValider);
			// modification de la taille de l'image
			valider.setFitHeight(20);
			valider.setFitWidth(20);

			btnValider.setGraphic(valider);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Ajout des Rows
		this.add(hbTop, 2, 1);
		this.addRow(4, labelIdentifiant, txtIdentifiant);
		this.addRow(5, labelMdp, txtMdp);
		this.add(btnQuitter, 3, 9);
		this.add(btnValider, 4, 9);
		this.add(lblMessage, 2, 5);

		// Padding et couleur :
		// this.setPadding(new Insets(10, 10, 10, 10));
		// this.setStyle("-fx-background-color:steelblue");
		// nouveau css externe de la fenetre
		this.getStylesheets().addAll(this.getClass().getResource("/ressource/style.css").toExternalForm());

		// Espacement vertical et horizontal
		this.setVgap(10);
		this.setHgap(10);

		btnValider.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				checkUser = txtIdentifiant.getText().toString();
				checkPw = txtMdp.getText().toString();

				if (checkUser.equals(admin.getIdentifiant()) && checkPw.equals(admin.getMotDePasse())) {
					Utilisateur.role = "admin";
					// instanciation de la fenetre de recherche administrateur
					EcranGestionStagiaires root = new EcranGestionStagiaires();
					// la nouvelle scene devient notre fenetre de recherche
					Scene scene = new Scene(root);
					// css de la fenetre
					scene.getStylesheets().addAll(this.getClass().getResource("/ressource/style.css").toExternalForm());
					// recuperation du Stage courant
					Stage stage = (Stage) EcranAuthentification.this.getScene().getWindow();
					// on donne à notre stage la nouvelle scene
					stage.setScene(scene);
				}

				// instanciaition de la fenetre utilisateur
				else if (checkUser.equals(util.getIdentifiant()) && checkPw.equals(util.getMotDePasse())) {
					Utilisateur.role = "util";
					// instanciation de la fenetre de recherche administrateur
					EcranGestionStagiaires root = new EcranGestionStagiaires();
					// la nouvelle scene devient notre fenetre de recherche
					Scene scene = new Scene(root);
					// recuperation du Stage courant
					Stage stage = (Stage) EcranAuthentification.this.getScene().getWindow();
					// on donne à notre stage la nouvelle scene
					stage.setScene(scene);
				} else {

					lblMessage.setText("Identifiant ou mot de passe erroné");
					lblMessage.getStyleClass().add("error");

				}

			}
		});

		btnQuitter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Platform.exit();
			}
		});

	}

}
