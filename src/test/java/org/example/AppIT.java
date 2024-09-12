package test.java.org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AppIT {
    @Test
    void itTest() {
        assertThat(false).isFalse();
    }
}
