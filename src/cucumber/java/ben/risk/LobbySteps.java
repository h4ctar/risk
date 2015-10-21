package ben.risk;

import ben.risk.irs.game.GameRecord;
import ben.risk.irs.type.GameState;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Set;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Master Steps.
 */
public class LobbySteps {

    private ben.risk.momserver.Main momServer;

    private ben.risk.datastore.Main dataStore;

    private ben.risk.master.Main master;

    private Set<ben.risk.player.Main> players = new HashSet<>();

    @Before
    public void before() throws Exception {
        momServer = new ben.risk.momserver.Main();
        momServer.start(new String[]{ "-p", "1234" });

        dataStore = new ben.risk.datastore.Main();
        dataStore.start(new String[]{ "-p", "1234", "-a", "127.0.0.1" });

        master = new ben.risk.master.Main();
        master.start(new String[]{ "-p", "1234", "-a", "127.0.0.1" });

        master = new ben.risk.master.Main();
        master.start(new String[]{"-p", "1234", "-a", "127.0.0.1"});

        // TODO: Wait till the client has been added.
        Thread.sleep(1000);
    }

    @After
    public void after() {
        for (ben.risk.player.Main player : players) {
            player.stop();
        }
        master.stop();
        dataStore.stop();
        momServer.stop();
    }

    @Given("^the system has started$")
    public void the_system_has_started() throws Throwable {
    }

    @Then("^the system moves into the ([A-Z0-9_]+) state$")
    public void the_system_moves_into_the_state(String state) throws Throwable {
        Thread.sleep(1000);

        GameRecord gameRecord = dataStore.getDataStore().getRecords(GameRecord.class).get(0);
        assertThat(gameRecord.getGameState(), equalTo(GameState.valueOf(state)));
    }

    @And("^([0-9]+) players have joined the game$")
    public void players_have_joined_the_game(int numberOfPlayers) throws Throwable {
        Thread.sleep(1000);

        for (int i = 0; i < numberOfPlayers; i++) {
            ben.risk.player.Main player = new ben.risk.player.Main();
            player.start(new String[]{"-p", "1234", "-a", "127.0.0.1"});

            Thread.sleep(1000);

            PlayerUtil.typeIntoField(player.getPlayerWindow(), new String[] { "LOGIN_PANE", "PLAYER_NAME" }, "Ben " + i);

            PlayerUtil.clickWidget(player.getPlayerWindow(), new String[] { "LOGIN_PANE", "JOIN_BUTTON" });

            players.add(player);
        }
    }

    @When("^all players are ready to play$")
    public void all_players_are_ready_to_play() throws Throwable {
        Thread.sleep(1000);
        
        for (ben.risk.player.Main player : players) {
            PlayerUtil.clickWidget(player.getPlayerWindow(), new String[] { "LOBBY_PANE", "READY_BUTTON" });
        }
    }
}
