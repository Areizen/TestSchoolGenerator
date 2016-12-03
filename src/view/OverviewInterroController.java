package view;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import model.QuestionModel;
import model.ReponseModel;
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
	private TableView<ReponseModel> reponseTable;
	@FXML
	private TableColumn<ObservableList<ReponseModel>,String> reponseColumn;
	
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
	
	void refreshTable() {
	    final List<QuestionModel> items = questionTable.getItems();
	    if( items == null || items.size() == 0) return;

	    final QuestionModel item = questionTable.getItems().get(0);
	    items.remove(0);
	    Platform.runLater(new Runnable(){
	        @Override
	        public void run() {
	            items.add(0, item);
	        }
	    });
	 }
	
	private void showReponseTable(QuestionModel question)
	{
		if(question.getTypeReponse().getValue().equals("Cocher"))
		{
			reponseColumn.setCellValueFactory(new PropertyValueFactory<>("reponse"));
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

	public static <T,U> void refreshTableView(TableView<T> tableView, List<TableColumn<T,U>> columns, List<T> rows) {        
	    tableView.getColumns().clear();
	    tableView.getColumns().addAll(columns);

	    ObservableList<T> list = FXCollections.observableArrayList(rows);
	    tableView.setItems(list);
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
