package NaNSsoGong.MrDaeBakDining.domain.style.service;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class StyleServiceTest {
    @Autowired
    DataInitiator dataInitiator;
    @Autowired
    StyleService styleService;

    @BeforeEach
    void init() {
        dataInitiator.dataInit();
    }

    @Test
    void 스타일로부터tablewareIdAndQuantity정상반환() {
        Style style = dataInitiator.style;
        Map<Long, Integer> tablewareIdAndQuantity = styleService.tablewareIdAndQuantity(style.getId());
        List<StyleItem> styleItemList = style.getStyleItemList();
        for (var styleItem : styleItemList) {
            if (!(styleItem.getItem() instanceof Food))
                continue;
            Long itemId = styleItem.getItem().getId();
            Integer itemQuantity = styleItem.getItemQuantity();
            assertThat(itemQuantity).isEqualTo(tablewareIdAndQuantity.get(itemId));
        }
    }
}