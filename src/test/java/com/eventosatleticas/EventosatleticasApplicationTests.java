package com.eventosatleticas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class EventosatleticasApplicationTests {
    @Test
    void contextLoads() {
    }
}
