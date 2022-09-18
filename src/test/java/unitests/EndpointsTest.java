package unitests;

import com.base.BaseTest;
import com.utils.BundleFile;
import com.utils.Endpoint;
import com.utils.PropertiesUtil;
import com.utils.ResponseMessage;
import org.testng.annotations.Test;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;

public class EndpointsTest extends BaseTest {
    @Test(description = "Verify that we are able to retrieve the endpoint")
    public void testResponseMessageRetrieve() {
        assertEquals(Endpoint.CREATE_USER.getEndpoint(), "/%s/user",
                "Incorrect endpoint is retrieved from properties file");
        assertEquals(Endpoint.TEST.getEndpoint(), "/testendpoint",
                "Incorrect endpoint is retrieved from properties file");
    }

    @Test(description = "Verify the count of message values and stored messages in the map")
    public void testResponseMessagesCount() {
        int valuesCount = Endpoint.values().length;
        assertEquals(valuesCount, Endpoint.getMapCount(),
                "Some response messages are missing from properties file.");
    }

    @Test(description = "Verify the count of messages in properties file and Response message values")
    public void testResponseMessagesPropertiesCount() {
        ResourceBundle bundle = PropertiesUtil.getBundle(BundleFile.API_RESOURCES);
        AtomicInteger propsCount = new AtomicInteger();
        bundle.getKeys().asIterator().forEachRemaining(s -> propsCount.getAndIncrement());

        int valuesCount = Endpoint.values().length;
        assertEquals(propsCount.get(), valuesCount,
                "Some response messages are missing from properties file / enums.");
    }
}
