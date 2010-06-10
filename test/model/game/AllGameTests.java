package model.game;

import model.game.player.PlayerTest;
import model.game.time.UpdateSchedulerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        UpdateSchedulerTest.class,
        PlayerTest.class
})
public class AllGameTests {

}
