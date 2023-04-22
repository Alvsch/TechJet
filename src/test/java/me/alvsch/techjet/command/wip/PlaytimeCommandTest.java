package me.alvsch.techjet.command.wip;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class PlaytimeCommandTest {

    @Test
    public void durationTest() {
        Duration duration = Duration.ofSeconds(12*60*60 + 10);
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutesPart());
        System.out.println(duration.toSecondsPart());
    }
  
}