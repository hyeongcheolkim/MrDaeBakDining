package NaNSsoGong.MrDaeBakDining;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DataInitiatorTest {
    @Autowired
    DataInitiator dataInitiator;

    @Test
    void init() {
        dataInitiator.init();
    }
}