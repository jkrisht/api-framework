package unitests;

import com.base.BaseTest;
import com.utils.BundleFile;
import com.utils.PropertiesUtil;
import com.utils.ResponseMessage;
import org.testng.annotations.Test;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;

public class ResponseMessageTests extends BaseTest {
    @Test(description = "Verify that we are able to retrieve the response message")
    public void testResponseMessageRetrieve() {
        assertEquals(ResponseMessage.OK.getResponseMessage(), "ok",
                "Incorrect response messages are retrieved");
        assertEquals(ResponseMessage.TEST.getResponseMessage(), "test message",
                "Incorrect response messages are retrieved");
    }

    @Test(description = "Verify the count of message values and stored messages in the map")
    public void testResponseMessagesCount() {
        int valuesCount = ResponseMessage.values().length;
        assertEquals(valuesCount, ResponseMessage.getMapCount(),
                "Some response messages are missing from properties file.");
    }

    @Test(description = "Verify the count of messages in properties file and Response message values")
    public void testResponseMessagesPropertiesCount() {
        ResourceBundle bundle = PropertiesUtil.getBundle(BundleFile.RESPONSE_MESSAGES);
        AtomicInteger propsCount = new AtomicInteger();
        bundle.getKeys().asIterator().forEachRemaining(s -> propsCount.getAndIncrement());

        int valuesCount = ResponseMessage.values().length;
        assertEquals(propsCount.get(), valuesCount,
                "Some response messages are missing from properties file / enums.");
    }
}
