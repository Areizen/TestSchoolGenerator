package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QuestionModel {

	private final StringProperty question;
	private final StringProperty typeReponse = new SimpleStringProperty("Remplir Ligne");
	private final IntegerProperty nbLignesReponse = new SimpleIntegerProperty(4);
	
	public QuestionModel(String question)
	{
		this.question = new SimpleStringProperty(question);
	}

	/**
	 * @return the nbLignesReponse
	 */
	public IntegerProperty getNbLignesReponse() {
		return nbLignesReponse;
	}

	/**
	 * @return the question
	 */
	public StringProperty getQuestion() {
		return question;
	}

	/**
	 * @return the typeReponse
	 */
	public StringProperty getTypeReponse() {
		return typeReponse;
	}
	
	
}
