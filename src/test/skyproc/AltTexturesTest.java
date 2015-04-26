package skyproc;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import skyproc.AltTextures.AltTexture;


public class AltTexturesTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(AltTextures.class)
				.verify();
	}

	@Test
	public void equalsContract2() {
		EqualsVerifier.forClass(AltTexture.class)
				.verify();
	}

}
