package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.personaltrainerpanelsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer.TrainerPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrainerPanelServiceUnitTest {
    //@Autowired
    //TrainerPanelService trainerPanelService;
    @MockBean
    TrainerPanelRepository trainerPanelRepository;
}