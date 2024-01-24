package com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CoachNoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCoachNoteByUserIdTest() throws Exception {
        String userId = "testUserId";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coachnotes/users/" + userId))
                .andExpect(status().isOk());
    }
}
