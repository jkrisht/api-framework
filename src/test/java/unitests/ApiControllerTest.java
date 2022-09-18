package unitests;

import com.base.BaseTest;
import com.controller.UsersApiController;
import io.restassured.http.ContentType;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static com.commons.TestConstants.API_BASE_URI;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ApiControllerTest extends BaseTest {
    @Test(description = "Verify that Request Specification details")
    public void verifyRequestSpecDetails() {
        UsersApiController controller = new UsersApiController();
        RequestSpecification specification = controller.getRequestSpec();
        QueryableRequestSpecification query = controller.getQueryRequest(specification);

        assertNotNull(specification, "Returned specification is null");
        assertEquals(API_BASE_URI, query.getURI(), "Request spec doesn't have the correct Base URI.");
        assertEquals(ContentType.JSON.toString(), query.getContentType(),
                "Request spec doesn't have the correct Content Type.");
    }
}
