package unitests;

import com.base.BaseTest;
import com.utils.BundleFile;
import com.utils.PropertiesUtil;
import org.testng.annotations.Test;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.testng.Assert.assertEquals;

public class PropertiesUtilTest extends BaseTest {

    @Test(description = "Verify that properties util is able to read given bundle file")
    void assertPropertyUtils() {
        ResourceBundle bundle = PropertiesUtil.getBundle(BundleFile.TEST);
        assertEquals("test", bundle.getObject("string"), "PropertiesUtil functionality is broken");
        assertEquals("true", bundle.getObject("boolean"), "PropertiesUtil functionality is broken");
        assertEquals("1", bundle.getObject("number"), "PropertiesUtil functionality is broken");
    }

    @Test(description = "Verify that exception is thrown when properties file doesn't exist",
            expectedExceptions = RuntimeException.class)
    public void testFileNotExistInDirectory() {
        PropertiesUtil.getBundle(BundleFile.NOT_EXISTS);
    }

    @Test(description = "Verify that exception is thrown when given bundle file is null",
            expectedExceptions = RuntimeException.class)
    public void testWhenBundleIsNull() {
        PropertiesUtil.getBundle(null);
    }

    @Test(description = "Verify that exception is thrown when the given is not present in the file",
            expectedExceptions = MissingResourceException.class)
    public void testNoKeyExists() {
        ResourceBundle bundle = PropertiesUtil.getBundle(BundleFile.TEST);
        assertEquals("test", bundle.getObject("str"), "");
    }
}
