package skyproc;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;


public class SubRecordTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(SubRecord.class)
				.verify();
	}

}
