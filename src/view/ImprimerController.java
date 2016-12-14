package view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.QuestionModel;
import utils.MiseEnPage;

public class ImprimerController {

	@FXML
	private TextField nomTextFied;
	@FXML
	private TextField intituleTextField;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField nbCopiesTextfield;
	@FXML
	private CheckBox sortCheckBox;

	private boolean isPrintClicked = false;
	private Stage dialogStage;
	private ObservableList<QuestionModel> questionData;

	@FXML
	private void initialize()
	{
	}

	public void setQuestionData(ObservableList<QuestionModel> questionData)
	{
		this.questionData = questionData;
	}

	public void setDialogStage(Stage dialogStage)
	{
		this.dialogStage = dialogStage;
	}

	public boolean isPrintClicked()
	{
		return isPrintClicked;
	}

	public void handleImprimer()
	{
		try{
			MiseEnPage mp = new MiseEnPage(questionData, this.nomTextFied.getText(), this.dateTextField.getText(),
					this.intituleTextField.getText(),Integer.parseInt(this.nbCopiesTextfield.getText()), this.sortCheckBox.isSelected());
			DirectoryChooser dc = new DirectoryChooser();
			dc.setTitle("Dossier de destination");
			File dir = dc.showDialog(dialogStage);
			if(dir==null)
			{
				return;
			}			
			String[] htmls = mp.generate();
			for(int i=0;i<htmls.length;i++)
			{

				OutputStream file = new FileOutputStream(new File(dir.getPath()+"/pdf"+i+".pdf"));
				Document document = new Document();
				PdfWriter writer = PdfWriter.getInstance(document, file);
				document.open();
				InputStream is = new ByteArrayInputStream(htmls[i].getBytes());
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
				document.close();
				file.close();

				System.out.println(htmls[i]);
			}

			this.isPrintClicked = true;
			this.dialogStage.close();
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleCancel()
	{
		this.dialogStage.close();
	}



}
