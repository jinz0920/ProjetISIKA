package fr.isika.cda23.Project_Annuaire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import donneeFichier.Donnee;
import donneeFichier.GestionFichierBineaire;
import fr.isika.cda23.utilClass.ArbreStagiaire;
import fr.isika.cda23.utilClass.Noeud;
import fr.isika.cda23.utilClass.Stagiaire;
import fr.isika.cda23.utilClass.Utilisateur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

public class EcranGestionStagiaires extends GridPane {
	ArrayList<Stagiaire> list = new ArrayList<>();
	ArbreStagiaire arbre;
	String nom;
	String prenom;
	String departement;
	String formation;
	String anneePromo;

	public EcranGestionStagiaires() {
		super();
		this.getStylesheets().addAll(this.getClass().getResource("/ressource/style.css").toExternalForm());
		arbre = GestionFichierBineaire.arbre;
		if (arbre == null) {
			arbre = Donnee.arbre;
		}
		arbre.afficherList();
		this.getStylesheets().addAll(this.getClass().getResource("/ressource/style.css").toExternalForm());

//		Creation d'une Hbox situé en haut de la page avec un label et deux images
		HBox hbBoxTop = new HBox();
		Label labelGestion = new Label("Gestion des stagiaires");
		labelGestion.getStyleClass().add("auth");

		// Insertion de la première image
		Image imageGestion;
		try {
			imageGestion = new Image(new FileInputStream("src/main/java/ressource/Gestion.png"));
			ImageView imageView = new ImageView(imageGestion);
			// modification de la taille de l'image
			imageView.setFitHeight(80);
			imageView.setFitWidth(80);
			// ajout de l'image dans la HBox
			hbBoxTop.getChildren().add(imageView);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Insertion du label dans la Hbox
		hbBoxTop.getChildren().add(labelGestion);

		// Insertion d'une deuxième image
		Image imageStagiaires;
		try {
			imageStagiaires = new Image(new FileInputStream("src/main/java/ressource/Stagiaires.png"));
			ImageView imageView = new ImageView(imageStagiaires);
			// modification de la taille de l'image
			imageView.setFitHeight(80);
			imageView.setFitWidth(80);
			// ajout de l'image dans la HBox
			hbBoxTop.getChildren().add(imageView);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Alignement de la Hbox au centre de la page
		hbBoxTop.setAlignment(Pos.CENTER);
		hbBoxTop.setPadding(new Insets(30));
		hbBoxTop.setPrefWidth(800);
		// Ajout d'un espace entre les enfants de la HBox
		hbBoxTop.setSpacing(50);

		ObservableList<Stagiaire> observablePersonnes = FXCollections.observableArrayList(Noeud.list);

		TableView<Stagiaire> tableView = new TableView<>(observablePersonnes);
		TableColumn<Stagiaire, String> colNom = new TableColumn<>("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("nom"));
		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>("Prenom");
		colPrenom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("prenom"));
		TableColumn<Stagiaire, String> colDepartement = new TableColumn<>("Departement");
		colDepartement.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("departement"));
		TableColumn<Stagiaire, String> colFormation = new TableColumn<>("Formation");
		colFormation.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("formation"));
		TableColumn<Stagiaire, String> colPromotion = new TableColumn<>("Promotion");
		colPromotion.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("anneePromo"));

		tableView.getColumns().addAll(colNom, colPrenom, colDepartement, colFormation, colPromotion);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setMinWidth(600);

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(tableView);
		anchorPane.setPrefSize(300, 200);
		AnchorPane.setTopAnchor(tableView, 5.);
		AnchorPane.setLeftAnchor(tableView, 5.);
		AnchorPane.setRightAnchor(tableView, 5.);
		AnchorPane.setBottomAnchor(tableView, 5.);

		VBox vbView = new VBox();
		Button btnReset = new Button("RESET");

		Button btnImprimerFiche = new Button("Imprimer fiche");

		// ajout d'une image dans les btnImprimer
		Image imageImprimer;
		try {
			imageImprimer = new Image(new FileInputStream("src/main/java/ressource/Imprimer.png"));
			ImageView imprimer = new ImageView(imageImprimer);
			// modification de la taille de l'image
			imprimer.setFitHeight(20);
			imprimer.setFitWidth(20);

			btnImprimerFiche.setGraphic(imprimer);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Image imageImprimer2;
		try {
			imageImprimer2 = new Image(new FileInputStream("src/main/java/ressource/Imprimer.png"));
			ImageView imprimer = new ImageView(imageImprimer2);
			// modification de la taille de l'image
			imprimer.setFitHeight(20);
			imprimer.setFitWidth(20);
			btnReset.setGraphic(imprimer);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Creation d'un flowPane pour afficher les boutons en bas à droite de la
		// tableView
		FlowPane flowPaneImpression = new FlowPane();
		// on ajoute les boutons au flowpane
		flowPaneImpression.getChildren().addAll(btnImprimerFiche, btnReset);
		// on définit la taille de notre flowPane
		flowPaneImpression.setPrefSize(100, 100);
		flowPaneImpression.setAlignment(Pos.BOTTOM_RIGHT);
		flowPaneImpression.setHgap(10);
		flowPaneImpression.setVgap(0);

		vbView.getChildren().addAll(anchorPane, flowPaneImpression);

		TableView<Stagiaire> tableView2 = new TableView<>();
		TableColumn<Stagiaire, String> colNom2 = new TableColumn<>("Nom");
		colNom2.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("nom"));
		TableColumn<Stagiaire, String> colPrenom2 = new TableColumn<>("Prenom");
		colPrenom2.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("prenom"));
		TableColumn<Stagiaire, String> colDepartement2 = new TableColumn<>("Departement");
		colDepartement2.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("departement"));
		TableColumn<Stagiaire, String> colFormation2 = new TableColumn<>("Formation");
		colFormation2.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("formation"));
		TableColumn<Stagiaire, String> colPromotion2 = new TableColumn<>("Promotion");
		colPromotion2.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("anneePromo"));

		tableView2.getColumns().addAll(colNom2, colPrenom2, colDepartement2, colFormation2, colPromotion2);
		tableView2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView2.setMinWidth(600);
		// GridPane de recherche multicritere
		Label labelNom = new Label("Nom:");
		TextField txtNom = new TextField();
		Label labelPrenom = new Label("Prenom:");
		TextField txtPrenom = new TextField();
		Label labelDepartement = new Label("Departement");
		ChoiceBox<String> cbDepartement = new ChoiceBox<>();
		cbDepartement.getItems().addAll("", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "2A", "2B", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
				"46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62",
				"63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79",
				"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95");

		cbDepartement.getSelectionModel().select(0);
		Label labelFormation = new Label("Formation:");
		TextField cbFormation = new TextField();
		Label labelAnneePromo = new Label("Promotion:");
		ChoiceBox<String> cbAnneePromo = new ChoiceBox<>();
		cbAnneePromo.getItems().addAll("", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
				"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021");
		cbAnneePromo.getSelectionModel().select(0);

		btnReset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtNom.setText("");
				txtPrenom.setText("");
				cbDepartement.setValue("");
				cbFormation.setText("");
				cbAnneePromo.setValue("");
			}
		});

		Button btnRechercher = new Button("Rechercher");

		// Ajout d'une image dans le bouton rechercher
		Image imageRechercher;
		try {
			imageRechercher = new Image(new FileInputStream("src/main/java/ressource/Rechercher.png"));
			ImageView rechercher = new ImageView(imageRechercher);
			// modification de la taille de l'image
			rechercher.setFitHeight(20);
			rechercher.setFitWidth(20);

			btnRechercher.setGraphic(rechercher);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				list.clear();
				arbre.rechercheMulticritere(new Stagiaire(txtNom.getText(), txtPrenom.getText(),
						cbDepartement.getValue(), cbFormation.getText(), cbAnneePromo.getValue()));
				for (Stagiaire i : Noeud.list) {

					list.add(i);
				}
				Noeud.list.clear();
				arbre.afficherList();
				ObservableList<Stagiaire> observablePersonne = FXCollections.observableArrayList(list);
				tableView2.setItems(observablePersonne);

				txtNom.setText("");
				txtPrenom.setText("");
				cbDepartement.setValue("");
				cbFormation.setText("");
				cbAnneePromo.setValue("");
			}
		});

		// On ajoute un listener qui recupere la personne selectionnée dans le
		// tableauView1:
		tableView.getSelectionModel().selectedItemProperty()
				.addListener((ChangeListener<? super Stagiaire>) new ChangeListener<Stagiaire>() {
					@Override
					public void changed(ObservableValue<? extends Stagiaire> observableValue, Stagiaire oldValue,
							Stagiaire newValue) {
						if (newValue != null) {
							txtNom.setText(newValue.getNom());
							txtPrenom.setText(newValue.getPrenom());
							cbDepartement.setValue(newValue.getDepartement());
							cbFormation.setText(newValue.getFormation());
							cbAnneePromo.setValue(newValue.getAnneePromo());

							nom = newValue.getNom();
							prenom = newValue.getPrenom();
							departement = newValue.getDepartement();
							formation = newValue.getFormation();
							anneePromo = newValue.getAnneePromo();
						}
					}
				});

		// On ajoute un listener qui recupere la personne selectionnée dans le
		// tableauView2:
		tableView2.getSelectionModel().selectedItemProperty()
				.addListener((ChangeListener<? super Stagiaire>) new ChangeListener<Stagiaire>() {
					@Override
					public void changed(ObservableValue<? extends Stagiaire> observableValue, Stagiaire oldValue,
							Stagiaire newValue) {
						if (newValue != null) {
							txtNom.setText(newValue.getNom());
							txtPrenom.setText(newValue.getPrenom());
							cbDepartement.setValue(newValue.getDepartement());
							cbFormation.setText(newValue.getFormation());
							cbAnneePromo.setValue(newValue.getAnneePromo());

							nom = newValue.getNom();
							prenom = newValue.getPrenom();
							departement = newValue.getDepartement();
							formation = newValue.getFormation();
							anneePromo = newValue.getAnneePromo();
						}
					}
				});
		Button btnAjouter = null;
		if (Utilisateur.role == "admin") {
			btnAjouter = new Button("Ajouter");

			// Ajout d'une image dans le bouton Ajouter
			Image imageAjouter;
			try {
				imageAjouter = new Image(new FileInputStream("src/main/java/ressource/Ajouter.png"));
				ImageView ajouter = new ImageView(imageAjouter);
				// modification de la taille de l'image
				ajouter.setFitHeight(20);
				ajouter.setFitWidth(20);

				btnAjouter.setGraphic(ajouter);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (txtNom.getText().equals("") || txtPrenom.getText().equals("")
							|| cbFormation.getText().equals("") || cbAnneePromo.getValue().equals("")) {
						JOptionPane.showMessageDialog(null,
								"Veuillez enregistrer les informations complètes de stagiaire", "WARNING",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						try {
							arbre.ajouterNoeud(new Stagiaire(txtNom.getText(), txtPrenom.getText(),
									cbDepartement.getValue(), cbFormation.getText(), cbAnneePromo.getValue()));
							Noeud.list.clear();
							arbre.afficherList();
							ObservableList<Stagiaire> observablePersonnesCopy = FXCollections
									.observableArrayList(Noeud.list);
							tableView.setItems(observablePersonnesCopy);
							txtNom.setText("");
							txtPrenom.setText("");
							cbDepartement.setValue("");
							cbFormation.setText("");
							cbAnneePromo.setValue("");

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});
		}

		GridPane gpRecherche = new GridPane();
		gpRecherche.add(labelNom, 0, 0);
		gpRecherche.add(txtNom, 0, 1);
		gpRecherche.add(labelPrenom, 1, 0);
		gpRecherche.add(txtPrenom, 1, 1);
		gpRecherche.add(labelDepartement, 2, 0);
		gpRecherche.add(cbDepartement, 2, 1);
		gpRecherche.add(labelFormation, 0, 2);
		gpRecherche.add(cbFormation, 0, 3);
		gpRecherche.add(labelAnneePromo, 1, 2);
		gpRecherche.add(cbAnneePromo, 1, 3);
		gpRecherche.add(btnRechercher, 3, 3);
		if (Utilisateur.role == "admin") {

			gpRecherche.add(btnAjouter, 2, 3);
		}
		gpRecherche.setPadding(new Insets(10));
		gpRecherche.setVgap(10);
		gpRecherche.setHgap(10);
		gpRecherche.setAlignment(Pos.CENTER);

		AnchorPane anchorPane2 = new AnchorPane();
		anchorPane2.getChildren().add(tableView2);
		anchorPane2.setPrefSize(800, 300);
		AnchorPane.setTopAnchor(tableView2, 5.);
		AnchorPane.setLeftAnchor(tableView2, 5.);
		AnchorPane.setRightAnchor(tableView2, 5.);
		AnchorPane.setBottomAnchor(tableView2, 5.);
		VBox Fiche = new VBox();

//		Button btnImprimerFiche = new Button("Imprimer fiche");
// btnImprimerFiche.setAlignment(Pos.CENTER_LEFT);btnImprimerFiche.setAlignment(Pos.CENTER_LEFT);

		Fiche.getChildren().add(anchorPane2);

// Hbox =>flowpane et button
		Button btnSupprimer = null;
		if (Utilisateur.role == "admin") {
			btnSupprimer = new Button("Supprimer");

			// Ajout d'une image dans le btnSupprimer
			Image imageSupprimer;
			try {
				imageSupprimer = new Image(new FileInputStream("src/main/java/ressource/Supprimer.png"));
				ImageView supprimer = new ImageView(imageSupprimer);
				// modification de la taille de l'image
				supprimer.setFitHeight(20);
				supprimer.setFitWidth(20);

				btnSupprimer.setGraphic(supprimer);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (txtNom.getText().equals("") || txtPrenom.getText().equals("")
							|| cbFormation.getText().equals("") || cbAnneePromo.getValue().equals("")) {
						JOptionPane.showMessageDialog(null,
								"Veuillez enregistrer les informations complètes de stagiaire à supprimer", "WARNING",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						int userOption = JOptionPane.showConfirmDialog(null,
								"Voulez vous supprimer le stagiaire " + txtNom.getText(), "WARNING", JOptionPane.OK_OPTION,
								JOptionPane.QUESTION_MESSAGE);

						if (userOption == JOptionPane.OK_OPTION) {
							arbre.rechercheMulticritere(new Stagiaire(txtNom.getText(), txtPrenom.getText(),
									cbDepartement.getValue(), cbFormation.getText(), cbAnneePromo.getValue()));

							arbre.supprimer(Noeud.list.get(0));

							try {
								GestionFichierBineaire.supprimerStagiaire(Noeud.list.get(0));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							list.clear();
							Noeud.list.clear();
							arbre.afficherList();
							ObservableList<Stagiaire> observablePersonnesCopy = FXCollections
									.observableArrayList(Noeud.list);
							ObservableList<Stagiaire> observablePersonnesCopy2 = FXCollections
									.observableArrayList(list);
							tableView.setItems(observablePersonnesCopy);
							tableView2.setItems(observablePersonnesCopy2);
							txtNom.setText("");
							txtPrenom.setText("");
							cbDepartement.setValue("");
							cbFormation.setText("");
							cbAnneePromo.setValue("");
						} else {
							return;
						}
					}

				}
			});
		}

		Button btnModifier = null;

		if (Utilisateur.role == "admin") {
			btnModifier = new Button("Modifier");

			// Ajout d'une image dans le btnModifier
			Image imageModifier;
			try {
				imageModifier = new Image(new FileInputStream("src/main/java/ressource/Modifier.png"));
				ImageView modifier = new ImageView(imageModifier);
				// modification de la taille de l'image
				modifier.setFitHeight(20);
				modifier.setFitWidth(20);

				btnModifier.setGraphic(modifier);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			btnModifier.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (txtNom.getText().equals("") || txtPrenom.getText().equals("")
							|| cbFormation.getText().equals("") || cbAnneePromo.getValue().equals("")) {
						JOptionPane.showMessageDialog(null,
								"Veuillez enregistrer les informations complètes de stagiaire à modifier", "WARNING",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						int userOption = JOptionPane.showConfirmDialog(null,
								"Voulez vous modifier le stagiaire " + txtNom.getText(), "WARNING",
								JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (userOption == JOptionPane.OK_OPTION) {
							arbre.rechercheMulticritere(new Stagiaire(nom, prenom, departement, formation, anneePromo));

							try {
								arbre.modifier(Noeud.list.get(0), txtNom.getText(), txtPrenom.getText(),
										cbDepartement.getValue(), cbFormation.getText(), cbAnneePromo.getValue());

								GestionFichierBineaire.supprimerStagiaire(Noeud.list.get(0));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							list.clear();
							Noeud.list.clear();
							arbre.afficherList();
							ObservableList<Stagiaire> observablePersonnesCopy = FXCollections
									.observableArrayList(Noeud.list);
							ObservableList<Stagiaire> observablePersonnesCopy2 = FXCollections
									.observableArrayList(list);
							tableView.setItems(observablePersonnesCopy);
							tableView2.setItems(observablePersonnesCopy2);
							txtNom.setText("");
							txtPrenom.setText("");
							cbDepartement.setValue("");
							cbFormation.setText("");
							cbAnneePromo.setValue("");
						} else {
							return;
						}
					}

				}
			});

		}

		Button btnRetour = new Button("Retour");

		// Ajout d'une image au btnRetour

		Image imageRetour;
		try {
			imageRetour = new Image(new FileInputStream("src/main/java/ressource/Return.png"));
			ImageView retour = new ImageView(imageRetour);
			// modification de la taille de l'image
			retour.setFitHeight(20);
			retour.setFitWidth(20);

			btnRetour.setGraphic(retour);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// fonctionnalité du bouton retour
		btnRetour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// instanciation de la fenetre de recherche administrateur
				EcranAuthentification root = new EcranAuthentification();
				// la nouvelle scene devient notre fenetre de recherche
				Scene scene = new Scene(root);
				// recuperation du Stage courant
				Stage stage = (Stage) EcranGestionStagiaires.this.getScene().getWindow();
				// on donne à notre stage la nouvelle scene
				stage.setScene(scene);

			}
		});

		Button btnExit = new Button("Quitter");

		// Ajout d'une image au bouton quitter
		Image imageExit;
		try {
			imageExit = new Image(new FileInputStream("src/main/java/ressource/Exit2.png"));
			ImageView exit = new ImageView(imageExit);
			// modification de la taille de l'image
			exit.setFitHeight(20);
			exit.setFitWidth(20);

			btnExit.setGraphic(exit);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Fonctionnalité du bouton quitter
		btnExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Platform.exit();
			}
		});

		FlowPane flowPane = new FlowPane();
		// on ajoute les boutons au flowpane
		if (Utilisateur.role == "admin") {

			flowPane.getChildren().addAll(btnSupprimer, btnModifier, btnRetour, btnExit);
		} else {
			flowPane.getChildren().addAll(btnRetour, btnExit);
		}
		// on définit la taille de notre flowPane
		flowPane.setPrefSize(300, 200);
		flowPane.setAlignment(Pos.BOTTOM_RIGHT);
		flowPane.setHgap(10);
		flowPane.setVgap(10);
		// On place les élément dans le gridPane root
		this.addRow(0, hbBoxTop);
		this.addRow(1, vbView);
		this.addRow(2, gpRecherche);
		this.addRow(3, Fiche);
		this.addRow(4, flowPane);
//				hbBox.setPadding(new Insets(50));
		this.setVgap(10);
		this.setHgap(10);

	}
}