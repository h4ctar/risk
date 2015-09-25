package ben.datastore;

import ben.risk.irs.IrsTypeHelper;
import ben.risk.irs.client.ClientNames;
import ben.mom.message.Message;
import ben.mom.server.MomServer;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DataStoreSteps {

    private static final long TIMEOUT = 10000;

    private final IrsTypeHelper irsTypeHelper = new IrsTypeHelper();

    private MomServer momServer;

    private Main main;

    @Before
    public void before() throws Exception {
        momServer = new MomServer(1234, true);
        main = new Main();
        main.start(new String[]{"-p", "1234", "-a", "127.0.0.1"});
        // TODO: Wait till the client has been added.
        Thread.sleep(1000);
    }

    @After
    public void after() {
        main.stop();
        momServer.stop();
    }

    @Given("^a ([a-zA-Z_\\.]+) message (?:has been|is) received(?: with:|)$")
    public void a_message_is_received(String messageName, Map<String, String> value) throws Throwable {
        Serializable message = irsTypeHelper.createIrsType(messageName, value);
        momServer.sendMessage(ClientNames.DATA_STORE, message);
    }

    @Then("^a ([a-zA-Z_\\.]+) message is sent to ([A-Z_]+)$")
    public void a_message_is_sent(String messageName, String destination) throws Throwable {
        Class<?> messageType = Class.forName(messageName);

        Message message = null;
        long startTime = System.currentTimeMillis();
        while (message == null && System.currentTimeMillis() - startTime < TIMEOUT) {
            message = momServer.getLastReceivedMessage(messageType);
            Thread.sleep(10);
        }

        assertThat(message, notNullValue()); assert message != null; // To fix bug with not null annotation checking.
        assertThat(message.getDestination(), equalTo(destination));
    }
}
