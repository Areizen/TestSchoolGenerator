import java.io.File;
import java.io.FileWriter;

import utils.MiseEnPage;

public class Test {


	public static void main(String[] args) {
		MiseEnPage mp =new MiseEnPage(null, "Je suis le Professeur", "1er Décembre", "Test Interro", 2, false);
		MiseEnPage.template_path="html/template.html";
		String[] test = mp.generate();
	}
}


