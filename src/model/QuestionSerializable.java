package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class QuestionSerializable implements Serializable {
	
	private final String question;
	private final String typeReponse;
	private final int nbLignesReponse;
	private final ArrayList<String> reponseData = new ArrayList<String>();
	
	
	public QuestionSerializable(QuestionModel model)
	{
		this.question = model.getQuestion().getValue();
		this.typeReponse = model.getTypeReponse().getValue();
		this.nbLignesReponse = model.getNbLignesReponse().getValue();
		for(StringProperty val : model.getReponseData().getValue())
		{
			reponseData.add(val.getValue());
		}
	}
	
	public QuestionModel convert()
	{
		QuestionModel qm = new QuestionModel(question);
		qm.getNbLignesReponse().setValue(this.nbLignesReponse);
		qm.getTypeReponse().setValue(typeReponse);
		ObjectProperty<ObservableList<StringProperty>> reponseData2 = qm.getReponseData();
		for(String val : this.reponseData)
		{
			reponseData2.getValue().add(new SimpleStringProperty(val));
		}
		return qm;
	}
}
