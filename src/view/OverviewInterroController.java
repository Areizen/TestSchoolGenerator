package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.QuestionModel;
import principale.Main;

public class OverviewInterroController {

	@FXML
	private TableView<QuestionModel> questionTable;
	@FXML
	private TableColumn<QuestionModel,String> questionColumn; 
	@FXML
	private Label questionLabel;
	@FXML
	private Label typeLabel;
	@FXML
	private Label nbLigneLabel;
	@FXML
	private TableView<StringProperty> reponseTable;
	@FXML
	private TableColumn<ObservableList<StringProperty>,String> reponseColumn;
	
	// Reference to the main application.
	private Main mainApp;

	@FXML
	private void initialize()
	{
		questionColumn.setCellValueFactory(cellData -> cellData.getValue().getQuestion());
		showQuestionDetails(null);

		questionTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldvalue,newValue)-> showQuestionDetails(newValue));
	}
	
	public void showReponseTable(QuestionModel question)
	{
		if(question.getTypeReponse().getValue().equals("Cocher"))
		{
			reponseTable.setItems(question.getReponseData().getValue());
			reponseColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
			reponseTable.setVisible(true);
		}else
		{
			this.reponseTable.setVisible(false);
		}
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
		questionTable.setItems(mainApp.getQuestionData());
	}
	
	
	public void handleImprimer()
	{
		mainApp.showImprimerDialog();
	}
	
	public void handleClose()
	{
		System.exit(0);
	}

	
	public void showQuestionDetails(QuestionModel question)
	{
		if(question != null)
		{
			questionLabel.setText(question.getQuestion().getValue());
			typeLabel.setText(question.getTypeReponse().getValue());
			nbLigneLabel.setText(Integer.toString(question.getNbLignesReponse().getValue()));
			reponseTable.setItems(FXCollections.emptyObservableList());
			showReponseTable(question);
		}else
		{
			questionLabel.setText("");
			typeLabel.setText("");
			nbLigneLabel.setText("0");
			reponseTable.setVisible(false);
		}
	}
	
	
	
	public void handleDelete()
	{
		int selectedIndex = questionTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex>=0)
		{
		questionTable.getItems().remove(selectedIndex);
		}else
		{
			Alert alert = new Alert(AlertType.ERROR);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Aucune question séléctionnée !");
	        alert.setContentText("Veuillez séléctionner une question valide !");

	        alert.showAndWait();
		}
	}
	
	public void handleNew()
	{
		QuestionModel tempQuestion = new QuestionModel("");
		tempQuestion.getReponseData().getValue().add(new SimpleStringProperty(""));
		boolean okClicked = mainApp.showPersonEditDialog(tempQuestion);
		if (okClicked) {
            mainApp.getQuestionData().add(tempQuestion);
        }
		
	}
	
	public void handleEdit()
	{
		QuestionModel selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
		if(selectedQuestion!=null)
		{
			boolean okClicked = mainApp.showPersonEditDialog(selectedQuestion);
			if (okClicked) {
	            showQuestionDetails(selectedQuestion);
	            this.reponseTable.refresh();
	        }
		}else
		{
			Alert alert = new Alert(AlertType.ERROR);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Aucune question séléctionnée !");
	        alert.setContentText("Veuillez séléctionner une question valide !");

	        alert.showAndWait();
		}
	}

}
