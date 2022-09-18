package unitests;

import com.base.BaseTest;
import com.utils.BundleFile;
import com.commons.TestConstants;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.assertTrue;

public class BundleFileTest extends BaseTest {
    @Test(description = "Verify that we are able to retrieve the endpoint")
    public void testFilesAreAvailableInDirectory() {
        File[] files = new File(TestConstants.PAGE_CONTENT_DIR).listFiles();
        BundleFile[] bundles = BundleFile.values();
        List<String> missingFiles = new ArrayList<>();
        AtomicBoolean isFound = new AtomicBoolean(false);

        Arrays.stream(bundles).forEach(bundleFile -> {
            Arrays.stream(files).forEach(file -> {
                if (file.getName().contains(bundleFile.getName())) {
                    isFound.set(true);
                }
            });

            if (isFound.get()) {
                isFound.set(false);
            } else {
                missingFiles.add(bundleFile.getName());
            }
        });

        // We have one non_exist file. So we are checking whether the value greater than 1 or not
        assertTrue(missingFiles.size() <= 1,
                "Found following missing files in BundleFile: " + missingFiles);
    }
}
