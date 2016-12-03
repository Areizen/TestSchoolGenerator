package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.QuestionModel;

public class EditQuestionController {

	@FXML
	private TextField questionTextField;
	@FXML
	private ComboBox<String>   typeComboBox;
	@FXML
	private TextField nbLigneTextField;
	@FXML
	private TableView reponseTable;
	@FXML
	private TableColumn reponseColumn;

	public static final ObservableList<String> OPTIONS = 
		    FXCollections.observableArrayList(
		            "Remplir Ligne",
		            "Cocher"
		        );
	
	private Stage dialogStage;
	private QuestionModel question;
	private boolean okClicked = false;
	
	@FXML
	private void initialize()
	{	
	}
	
	 /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public void setQuestion(QuestionModel question)
	{
		this.question = question;
		
		this.questionTextField.setText(question.getQuestion().getValue());
		this.typeComboBox.setItems(OPTIONS);
		this.typeComboBox.getSelectionModel().select(this.typeComboBox.getItems().indexOf(question.getTypeReponse().getValue()));
		handleComboBox();
		this.nbLigneTextField.setText(Integer.toString(question.getNbLignesReponse().getValue()));
	}
	
	/**
     * Called when the user clicks ok.
     */
    @FXML
	public void handleOk()
	{
	        if (isInputValid()) {
	        	this.question.getQuestion().set(questionTextField.getText());
	        	this.question.getNbLignesReponse().set(Integer.parseInt(nbLigneTextField.getText()));
	        	this.question.getTypeReponse().set(typeComboBox.getValue());
	        	
	            okClicked = true;
	            dialogStage.close();
	        }
	}
    
    public void handleComboBox()
    {
    	if((this.typeComboBox.getSelectionModel().getSelectedItem()).equals("Cocher"))
    	{
    		this.reponseTable.setVisible(true);
    	}else
    	{
    		this.reponseTable.setVisible(false);
    	}
    }
	
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
	private boolean isInputValid() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
}
