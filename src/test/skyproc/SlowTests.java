package skyproc;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
public class SlowTests {

}
