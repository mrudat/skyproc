package skyproc;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

public class FormIDTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(FormID.class)
				.withPrefabValues(ModListing.class, new ModListing("Mod", false), new ModListing("Mod", true))
				.usingGetClass()
				.suppress(Warning.NONFINAL_FIELDS)
				.verify();
	}

	// TODO test more
}
