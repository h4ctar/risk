package ben.datastore;

import ben.irs.client.ClientNames;
import ben.irs.mock.MockDeleted;
import ben.irs.mock.MockRecord;
import ben.irs.mock.MockUpdated;
import ben.irs.record.DeleteAllRecords;
import ben.irs.record.DeleteRecord;
import ben.irs.record.RequestAllRecords;
import ben.irs.record.WriteRecord;
import ben.mom.message.Message;
import ben.mom.server.MomServer;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DataStoreSteps {

    private static final long TIMEOUT = 10000;

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

    @Given("^there is an existing record$")
    public void there_is_an_existing_record() {
        MockRecord mockRecord = new MockRecord();
        WriteRecord writeRecord = new WriteRecord(mockRecord);
        momServer.sendMessage(ClientNames.DATA_STORE, writeRecord);
    }

    @When("^a write record message is received$")
    public void a_write_record_message_is_received() {
        MockRecord mockRecord = new MockRecord();
        WriteRecord writeRecord = new WriteRecord(mockRecord);
        momServer.sendMessage(ClientNames.DATA_STORE, writeRecord);
    }

    @When("^a delete record message is received$")
    public void a_delete_record_message_is_received() {
        DeleteRecord deleteRecord = new DeleteRecord(MockRecord.class, 0);
        momServer.sendMessage(ClientNames.DATA_STORE, deleteRecord);
    }

    @When("^a request all records message is received$")
    public void a_request_all_records_message_is_received() {
        RequestAllRecords requestAllRecords = new RequestAllRecords(MockRecord.class);
        momServer.sendMessage(ClientNames.DATA_STORE, requestAllRecords);
    }

    @When("^a delete all records message is received$")
    public void a_delete_all_records_message_is_received() {
        DeleteAllRecords deleteAllRecords = new DeleteAllRecords(MockRecord.class);
        momServer.sendMessage(ClientNames.DATA_STORE, deleteAllRecords);
    }

    @Then("^a record updated message is broadcasted$")
    public void a_record_updated_message_is_broadcasted() throws Throwable {
        Message message = null;
        long startTime = System.currentTimeMillis();
        while (message == null && System.currentTimeMillis() - startTime < TIMEOUT) {
            message = momServer.getLastReceivedMessage(MockUpdated.class);
            Thread.sleep(10);
        }
        assertThat(message, notNullValue()); assert message != null; // To fix bug with not null annotation checking.
        assertThat(message.getDestination(), equalTo(ClientNames.ALL));
        assertThat(message.getBody().getClass(), equalTo(MockUpdated.class));
    }

    @Then("^a record deleted message is broadcasted$")
    public void a_record_deleted_message_is_broadcasted() throws Throwable {
        Message message = null;
        long startTime = System.currentTimeMillis();
        while (message == null && System.currentTimeMillis() - startTime < TIMEOUT) {
            message = momServer.getLastReceivedMessage(MockDeleted.class);
            Thread.sleep(10);
        }
        assertThat(message, notNullValue()); assert message != null; // To fix bug with not null annotation checking.
        assertThat(message.getDestination(), equalTo(ClientNames.ALL));
        assertThat(message.getBody().getClass(), equalTo(MockDeleted.class));
    }
}
