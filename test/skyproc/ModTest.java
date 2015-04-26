package skyproc;

import static org.mockito.Mockito.mock;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

public class ModTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testModModListing() throws Exception {
		ModListing modListing = mock(ModListing.class);
		Mod mod = new Mod(modListing);
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(Mod.class).verify();
	}

}
