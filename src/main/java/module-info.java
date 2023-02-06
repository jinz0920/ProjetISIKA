module fr.isika.cda23.Project_Annuaire {
    requires javafx.controls;
    exports fr.isika.cda23.Project_Annuaire;
    
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.base;
	opens fr.isika.cda23.Project_Annuaire to javafx.controls,javafx.graphics,fr.isika.cda23.annuaire.modele, javafx.fxml;
    exports fr.isika.cda23.utilClass;
}
