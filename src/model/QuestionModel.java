package model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QuestionModel{

	private final StringProperty question;
	private final StringProperty typeReponse = new SimpleStringProperty("Remplir Ligne");
	private final IntegerProperty nbLignesReponse = new SimpleIntegerProperty(1);
	private ObjectProperty<ObservableList<StringProperty>> reponseData = new SimpleObjectProperty<ObservableList<StringProperty>>(FXCollections.observableArrayList());

	

	/**
	 * @return the reponseData
	 */
	public ObjectProperty<ObservableList<StringProperty>> getReponseData() {
		return reponseData;
	}
	
	public void setReponseData(ObjectProperty<ObservableList<StringProperty>> reponseData)
	{
		this.reponseData = reponseData;
	}

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
