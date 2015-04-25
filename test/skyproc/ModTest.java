package skyproc;

import static org.mockito.Mockito.mock;

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

}
