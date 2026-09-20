package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HomeControllerTest {

    @Test
    void shouldReturnExampleDataOnIndex() {
        HomeController controller = new HomeController();
        Map<String, String> response = controller.index();

        assertNotNull(response);
        assertEquals("Hello, DevOps!", response.get("message"));
        assertEquals("running", response.get("status"));
    }
}
