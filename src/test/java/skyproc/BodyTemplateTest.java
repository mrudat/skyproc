package skyproc;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BodyTemplateTest {

	private BodyTemplate body_template;

	@Before
	public void setUp() throws Exception {
		body_template = new BodyTemplate();
	}

	@Test
	public void testGetNew() {
		BodyTemplate actual = body_template.getNew("BODT");
		assertEquals(BodyTemplate.class, actual.getClass());
	}

}
