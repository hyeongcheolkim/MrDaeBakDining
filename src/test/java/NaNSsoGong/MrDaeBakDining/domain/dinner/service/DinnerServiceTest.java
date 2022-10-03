package NaNSsoGong.MrDaeBakDining.domain.dinner.service;

import NaNSsoGong.MrDaeBakDining.domain.DataInitiatorForTest;
import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerItem;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class DinnerServiceTest {
    @Autowired
    DataInitiatorForTest dataInitiator;
    @Autowired
    DinnerService dinnerService;

    @BeforeEach
    void init() {
        dataInitiator.dataInit();
    }

    @Test
    void 디너로부터foodIdAndQuantity정상반환() {
        Dinner dinner = dataInitiator.dinner;
        Map<Long, Integer> foodIdAndQuantity = dinnerService.toFoodIdAndQuantity(dinner.getId());
        List<DinnerItem> dinnerItemList = dinner.getDinnerItemList();
        for (var dinnerItem : dinnerItemList) {
            if (!(dinnerItem.getItem() instanceof Food))
                continue;
            Long itemId = dinnerItem.getItem().getId();
            Integer itemQuantity = dinnerItem.getItemQuantity();
            assertThat(itemQuantity).isEqualTo(foodIdAndQuantity.get(itemId));
        }
    }

    @Test
    void 디너로부터decorationIdAndQuantity정상반환() {
        Dinner dinner = dataInitiator.dinner;
        Map<Long, Integer> decorationIdAndQuantity = dinnerService.toDecorationIdAndQuantity(dinner.getId());
        List<DinnerItem> dinnerItemList = dinner.getDinnerItemList();
        for (var dinnerItem : dinnerItemList) {
            if (!(dinnerItem.getItem() instanceof Decoration))
                continue;
            Long itemId = dinnerItem.getItem().getId();
            Integer itemQuantity = dinnerItem.getItemQuantity();
            assertThat(itemQuantity).isEqualTo(decorationIdAndQuantity.get(itemId));
        }
    }
}