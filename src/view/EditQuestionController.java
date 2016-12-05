package view;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	private TableView<StringProperty> reponseTable;
	@FXML
	private TableColumn<ObservableList<StringProperty>,String> reponseColumn;

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
		setTab(question);
		this.nbLigneTextField.setText(Integer.toString(question.getNbLignesReponse().getValue()));
	}

	public void setTab(QuestionModel question)
	{
		if(question.getTypeReponse().getValue().equals("Cocher"))
		{
			this.reponseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			this.reponseColumn.setOnEditCommit(event -> {
				String s = event.getNewValue();
				this.question.getReponseData().getValue().get(event.getTablePosition().getRow()).set(s);;
			});

			this.reponseColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
			this.reponseTable.setItems(question.getReponseData().getValue());
			this.reponseTable.setVisible(true);
			this.reponseColumn.setEditable(true);
		}else
		{
			this.reponseTable.setVisible(false);
		}
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

	public void handleLigneChange()
	{
		try
		{
			int nb_ligne = Integer.parseInt(this.nbLigneTextField.getText());
			if(nb_ligne<this.reponseTable.getItems().size())
			{
				for(int i=this.reponseTable.getItems().size(); i>nb_ligne;i--)
				{
					this.reponseTable.getItems().remove(i-1);
				}
			}else
			{
				for(int i= this.reponseTable.getItems().size();i<nb_ligne;i++)
				{
					this.reponseTable.getItems().add(new SimpleStringProperty(""));
				}
			}
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
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
