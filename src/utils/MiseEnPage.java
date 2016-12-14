package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.QuestionModel;

public class MiseEnPage {

	public static String template_path = "html/template.html";
	private ObservableList<QuestionModel> questionData;
	private String nom,date;
	private int nbCopies;
	private String header = "";
	boolean sort;
	private String titre;

	public MiseEnPage(ObservableList<QuestionModel> questionData,String nom,String date,String titre,int nbCopies,boolean sort)
	{
		this.titre = titre;
		this.questionData = questionData;
		this.nom = nom;
		this.date = date;
		this.nbCopies = nbCopies;
		this.sort = sort;
	}

	private String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader (file));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");

		try {
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	public String replace()
	{
		String html = "";
		try {
			html = readFile(template_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		html = html.replaceAll("#TITRE#", this.titre);
		html = html.replaceAll("#DATE#", this.date);
		html = html.replaceAll("#PROFESSEUR#", this.nom);

		return html;
	}

	private ObservableList<QuestionModel> shuffle()
	{
		ObservableList<QuestionModel> questionData = FXCollections.observableArrayList(this.questionData);
		FXCollections.shuffle(questionData);
		return questionData;

	}

	private String generateBody()
	{
		String result = "";
		ObservableList<QuestionModel> questions;
		if(this.sort)
		{
			questions = shuffle();
		}else
		{
			questions = this.questionData;
		}

		for(int i=0;i<questionData.size();i++)
		{
			result+="<div class='question'><h4>"+questions.get(i).getQuestion().get()+"</h4>";

			if(questions.get(i).getTypeReponse().get().equals("Cocher"))
			{
				ObservableList<StringProperty> r = questions.get(i).getReponseData().getValue();

				result+="<ul>";
				for(int j=0;j<r.size();j++)
				{
					result+="<li>"+r.get(j).get()+"</li>";
				}
				result+="</ul>";
			}else
			{
				for(int j=0;j<questions.get(i).getNbLignesReponse().get();j++)
				{
					result+="<p>............................................................................"
							+ "...............................................</p>";
				}
			}
			result+="</div>";
		}
		return result;
	}


	public String[] generate()
	{
		this.header = replace();
		String[] result = new String[nbCopies];
		for(int i=0;i<nbCopies;i++)
		{
			result[i]=header.replace("#QUESTIONS#", generateBody());
		}
		return result;
	}





}
