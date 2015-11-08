/**
 * 
 */
package br.com.deployxtech.ocr;

import java.util.List;

/**
 * @author francisco
 *
 */
public interface Classification {
	CharacterImage analyze(CharacterImage character);
	List<CharacterImage> getCharacters();
}