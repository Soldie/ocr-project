/**
 * 
 */
package br.com.deployxtech.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * @author francisco
 *
 */
public class CharaterProcessingTest {
	
	private static final String BASE_DIR = "/home/francisco/Desenvolvimento_de_software/plataformas/java/workspaces/api/ocr-project/test-resource";

	public static void processImages() throws IOException {
		CharacterProcessing processing = new CharacterProcessing();
		List<CharacterImage> characters = processing.discover(ImageIO.read(new File(BASE_DIR+"/test/teste.png")));
		File dir = new File(BASE_DIR+"/result-processing");

		for (File file: dir.listFiles()) {
			file.delete();
		}

		for (CharacterImage character: characters) {
			BufferedImage image = character.newImage();
			ImageIO.write(image, "png", dir.toPath().resolve(String.format("%s.png", dir.listFiles().length)).toFile());
		}
	}

	public static void learm() throws IOException {
		List<CharacterImage> charactersLearn = new ArrayList<CharacterImage>(); 
		Pattern pattern = Pattern.compile("(\\()(.*?)(\\))");
		File dir = new File(BASE_DIR+"/learm");
		for (File file: dir.listFiles()) {
			Matcher matcher = pattern.matcher(file.getName());
			if (matcher.find()) {
				String letras = matcher.group(2);
				letras = letras.replaceAll(" ", "");

				CharacterProcessing processing = new CharacterProcessing();
				List<CharacterImage> characters = processing.discover(ImageIO.read(file));

				for (int index = 0; index < characters.size(); index++) {
					characters.get(index).setCharacter(letras.charAt(index));;
				}

				charactersLearn.addAll(characters);
			}
		}
		FileOutputStream fos = new FileOutputStream(BASE_DIR+"/data.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(charactersLearn);
		oos.close();
		fos.close();		
	}

	public static void recogner() throws Exception {
		FileInputStream fileIn = new FileInputStream(BASE_DIR+"/data.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		List<CharacterImage> charactersLearn = (List<CharacterImage>) in.readObject();
		in.close();
		fileIn.close();

		CharacterProcessing processing = new CharacterProcessing();
		List<CharacterImage> characters = processing.discover(ImageIO.read(new File(BASE_DIR+"/test/t3.png")));
		StringBuilder txt = new StringBuilder();

		for (CharacterImage character: characters) {
			ImageIO.write(character.newImage(), "png", new File(BASE_DIR+"/result-processing/"+new Date().getTime()+".png"));
			CharacterImage prox = null;
			for (CharacterImage charLearn: charactersLearn) {
				charLearn.analisarProximidade(character);
				if (prox == null || (prox.getProximidade() < charLearn.getProximidade())) {
					prox = charLearn;
				}
			}
			txt.append(prox.getCharacter());
			System.out.printf("Char: %s, Prox: %s\n", prox.getCharacter(), prox.getProximidade());
		}

		System.out.printf("O resultado é: %s\n", txt.toString());
	}

	public static void main(String[] args) {
		try {	
			//learm();
			recogner();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}