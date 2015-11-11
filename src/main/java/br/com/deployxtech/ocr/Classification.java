/**
 * 
 */
package br.com.deployxtech.ocr;

import java.util.List;

/**
 * @author Francisco Silva
 *
 */
public interface Classification {
	CharacterImage analyze(CharacterImage character);
	List<CharacterImage> getCharacters();
}