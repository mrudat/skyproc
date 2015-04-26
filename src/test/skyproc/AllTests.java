package skyproc;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// FIXME add tests from SkyProcTests to test suite
// Import.class
// TestCopy.class
// ValidateAll.class

@RunWith(Categories.class)
@ExcludeCategory(SlowTests.class)
public class AllTests {

}
