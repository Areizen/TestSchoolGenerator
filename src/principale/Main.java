package principale;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.QuestionModel;
import view.EditQuestionController;
import view.OverviewInterroController;

public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<QuestionModel> questionData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Contrôle");
		
		initSample();
		initRoot();
		initOverviewInterro();
		
	}
	
	public Stage getPrimaryStage()
	{
		return this.primaryStage;
	}
	
	public ObservableList<QuestionModel> getQuestionData()
	{
		return this.questionData;
	}
	
	public void initSample()
	{
		questionData.add(new QuestionModel("Comment allez vous ?"));
	}
	
	public void initRoot()
	{
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/RootView.fxml"));
			rootLayout  = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void initOverviewInterro()
	{
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/OverviewInterro.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			rootLayout.setCenter(pane);
			
			// Give the controller access to the main app.
	        OverviewInterroController controller = loader.getController();
	        controller.setMainApp(this);
		}catch(IOException e)
		{
			
		}
	}
	
	public boolean showPersonEditDialog(QuestionModel person) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("../view/EditQuestionView.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Question");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EditQuestionController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setQuestion(person);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public static void main(String[] args) {
		launch(args);
	}
}
