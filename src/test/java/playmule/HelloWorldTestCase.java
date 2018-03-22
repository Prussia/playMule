package playmule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;
import static com.jayway.restassured.RestAssured.*;

public class HelloWorldTestCase extends FunctionalTestCase {
	private static String MESSAGE = "Hello world!";

	@Override
	protected String getConfigFile() {
		return "playmule_test.xml";
	}

	@Test
	public void clientTestCase() throws Exception {
		MuleClient client = muleContext.getClient();
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("http.method", "GET");
		MuleMessage result = client.send("http://localhost:8081", "", props);
		assertNotNull(result);
		assertFalse(result.getPayload() instanceof NullPayload);
		assertEquals(MESSAGE, result.getPayloadAsString());
	}

	@Test
	public void dependencyClientTestCase() throws Exception {
		String response = get("http://localhost:8081").body().asString();
		assertEquals(MESSAGE, response);
	}
}
