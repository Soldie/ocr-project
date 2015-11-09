/**
 * 
 */
package br.com.deployxtech.ocr;

import java.util.List;

/**
 * @author francisco
 *
 */
public class PixelsPositionsClassification implements Classification {

	private List<CharacterImage> characters;
	private double averageProximity = 100d;

	public PixelsPositionsClassification(List<CharacterImage> characters) {
		this.characters = characters;
	}

	@Override
	public CharacterImage analyze(CharacterImage character) {
		character.newImage();
		CharacterImage bestCandidate = null;
		for (CharacterImage charAnalyze: characters) {
			charAnalyze.analisarProximidade(character);
			if (bestCandidate == null || (bestCandidate.getProximidade() < charAnalyze.getProximidade())) {
				bestCandidate = charAnalyze;
			}
			
			/*if (bestCandidate.getProximidade() > averageProximity) {
				break;
			}*/
		}
		
		averageProximity = (averageProximity+bestCandidate.getProximidade())/2;
		
		/*if (this.characters.remove(bestCandidate)) {
			this.characters.add(0,bestCandidate);
		}*/

		return bestCandidate;
	}

	@Override
	public List<CharacterImage> getCharacters() {
		return characters;
	}
}