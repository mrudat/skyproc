package skyproc;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;


public class SubRecordTypedTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(SubRecordTyped.class)
				.verify();
	}
}
