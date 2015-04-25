package skyproc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// FIXME add tests from SkyProcTests to test suite
// Import.class
// TestCopy.class
// ValidateAll.class

@RunWith(Suite.class)
@SuiteClasses({ BodyTemplateTest.class, RecordTest.class, ModListingTest.class,
		ModTest.class, ModExporterTest.class })
public class AllTests {

}
