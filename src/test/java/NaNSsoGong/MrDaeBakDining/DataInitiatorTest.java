package NaNSsoGong.MrDaeBakDining;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DataInitiatorTest {
    @Autowired
    DataInitiator dataInitiator;

    @Test
    void init() {
        dataInitiator.dataInit();
    }
}