package fr.isika.cda23.utilClass;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class Utility {
	
	public static String regExp = "[' ''-]";
	
	public static String removeAccents(String text) {
		return text == null ? null
				: Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
}
