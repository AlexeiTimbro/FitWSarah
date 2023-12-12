package com.fitwsarah.fitwsarah.adminpanelsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminPanelServiceUnitTest {

    @MockBean
    AdminPanelRepository adminPanelRepository;
}