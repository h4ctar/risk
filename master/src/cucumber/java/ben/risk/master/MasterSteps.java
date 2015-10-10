package ben.risk.master;

import ben.mom.message.Message;
import ben.mom.server.MomServer;
import ben.risk.irs.JoinLobby;
import ben.risk.irs.Ready;
import ben.risk.irs.client.ClientNames;
import ben.risk.irs.game.GameRecord;
import ben.risk.irs.player.PlayerRecord;
import ben.risk.irs.record.IRecord;
import ben.risk.irs.record.WriteRecord;
import ben.risk.irs.type.GameState;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Master Steps.
 */
public class MasterSteps {

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

    @When("^Master starts$")
    public void master_starts() {
    }

    @When("^a join lobby message (?:is|has been) received$")
    public void a_join_lobby_message_is_received() {
        JoinLobby joinLobby = new JoinLobby("Ben");
        momServer.sendMessage(ClientNames.MASTER, joinLobby);
    }

    @Given("^(\\d+) players have joined the lobby$")
    public void players_have_joined_the_lobby(int numberOfPlayers) throws Throwable {
        for (int i = 0; i < numberOfPlayers; i++) {
            JoinLobby joinLobby = new JoinLobby("Ben " + i);
            momServer.sendMessage(ClientNames.MASTER, joinLobby);
        }
    }

    @Then("^the game state is set to ([A-Z0-9]+)$")
    public void the_game_state_is_set_to(String state) throws Throwable {
        GameRecord gameRecord = getWriteRecord(GameRecord.class);

        assertThat(gameRecord.getGameState(), equalTo(GameState.valueOf(state)));
    }

    @Then("^the player record is added$")
    public void the_player_record_is_added() throws Throwable {
        PlayerRecord playerRecord = getWriteRecord(PlayerRecord.class);

        assertThat(playerRecord.getPlayerName(), equalTo("Ben"));
        assertThat(playerRecord.isReady(), equalTo(false));
    }

    @When("^a player ready message is received$")
    public void a_player_ready_message_is_received() throws Throwable {
        Ready ready = new Ready("Ben");
        momServer.sendMessage(ClientNames.MASTER, ready);
    }

    @When("^(\\d+) player ready messages have been received$")
    public void player_ready_messages_have_been_received(int numberOfPlayers) throws Throwable {
        for (int i = 0; i < numberOfPlayers; i++) {
            Ready ready = new Ready("Ben " + i);
            momServer.sendMessage(ClientNames.MASTER, ready);
        }
    }

    @Then("^the player record is set to ready$")
    public void the_player_record_is_set_to_ready() throws Throwable {
        PlayerRecord playerRecord = getWriteRecord(PlayerRecord.class);

        assertThat(playerRecord.getPlayerName(), equalTo("Ben"));
        assertThat(playerRecord.isReady(), equalTo(true));
    }

    @Nullable
    private Message getLastReceivedMessage(@NotNull Class<?> type) throws InterruptedException {
        Thread.sleep(1000);

        Message message = null;

        long startTime = System.currentTimeMillis();
        while (message == null && System.currentTimeMillis() - startTime < 1000) {
            message = momServer.getLastReceivedMessage(type);
            Thread.sleep(10);
        }

        return message;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private <T extends IRecord> T getWriteRecord(Class<T> recordType) throws InterruptedException {
        Message message = getLastReceivedMessage(WriteRecord.class);

        assertThat(message, notNullValue()); assert message != null; // To fix bug with not null annotation checking.
        assertThat(message.getDestination(), equalTo(ClientNames.DATA_STORE));
        assertThat(message.getBody().getClass(), equalTo(WriteRecord.class));

        WriteRecord writeRecord = (WriteRecord) message.getBody();
        IRecord record = writeRecord.getRecord();

        assertThat(record.getClass(), equalTo(recordType));

        return (T) record;
    }
}
