/**
 * 
 */
package br.com.deployxtech.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author francisco
 *
 */
public class OCRProcessing {

	private static final String BASE_DIR = "test-resource";
	
	private JTextArea txtConsole;
	
	public OCRProcessing() {}
	public OCRProcessing(JTextArea txtConsole) {
		this.txtConsole = txtConsole;
	}

	public void learm(String letras, BufferedImage image) throws IOException, ClassNotFoundException {
		letras = letras.replaceAll(" ", "");

		CharacterProcessing processing = new CharacterProcessing();
		List<CharacterImage> characters = processing.discover(image);

		for (int index = 0; index < characters.size(); index++) {
			characters.get(index).setCharacter(letras.charAt(index));;
		}

		save(characters);
	}

	public String recogner(BufferedImage image) throws FileNotFoundException, ClassNotFoundException, IOException {

		List<CharacterImage> charactersLearn = loadData();

		CharacterProcessing processing = new CharacterProcessing();
		List<CharacterImage> characters = processing.discover(image);
		StringBuilder txt = new StringBuilder();

		for (CharacterImage character: characters) {
			//ImageIO.write(character.newImage(), "png", new File(BASE_DIR+"/result-processing/"+new Date().getTime()+".png"));
			character.newImage();
			CharacterImage prox = null;
			for (CharacterImage charLearn: charactersLearn) {
				charLearn.analisarProximidade(character);
				if (prox == null || (prox.getProximidade() < charLearn.getProximidade())) {
					prox = charLearn;
				}
			}
			txt.append(prox.getCharacter());
			print(String.format("Char: %s, Prox: %s\n", prox.getCharacter(), prox.getProximidade()));
		}

		print(String.format("O resultado é: %s\n", txt.toString()));

		return txt.toString();
	}

	private void save(List<CharacterImage> characters) throws IOException, ClassNotFoundException {
		File fileData = new File(BASE_DIR+"/data.ser");
		
		if (!fileData.getParentFile().exists()) {
			fileData.getParentFile().mkdirs();
		}
		
		List<CharacterImage> charactersLearn = loadData();
		charactersLearn.addAll(characters);
		FileOutputStream fos = new FileOutputStream(fileData);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(charactersLearn);
		oos.close();
		fos.close();
	}

	private List<CharacterImage> loadData() throws FileNotFoundException, IOException, ClassNotFoundException {
		File fileData = new File(BASE_DIR+"/data.ser");
		List<CharacterImage> charactersLearn = null;
		if (fileData.exists()) {
			FileInputStream fis = new FileInputStream(fileData);
			ObjectInputStream ois = new ObjectInputStream(fis);
			charactersLearn = (List<CharacterImage>) ois.readObject();
			fis.close();
			ois.close();
		}
		else {
			charactersLearn = new ArrayList<CharacterImage>();
		}
		
		return charactersLearn;
	}
	
	private void print(String text) {
		if (txtConsole == null) {
			System.out.print(text);
		}
		else {
			txtConsole.append(text);
		}
	}
}