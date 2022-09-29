package NaNSsoGong.MrDaeBakDining.domain.style.service;

import NaNSsoGong.MrDaeBakDining.domain.item.domain.Item;
import NaNSsoGong.MrDaeBakDining.domain.item.repository.ItemRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleItem;
import NaNSsoGong.MrDaeBakDining.domain.style.dto.StyleDto;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleItemRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleRepository styleRepository;
    private final ItemRepository itemRepository;
    private final StyleItemRepository styleItemRepository;

    public Long makeStyle(StyleDto styleDto){
        Style style = new Style();
        styleRepository.save(style);
        style.setName(styleDto.getName());
        style.setStyleItemList(makeStyleItemList(style, styleDto));
        return style.getId();
    }

    public Map<Long, Integer> tablewareIdAndQuantity(Long styleId) {
        var ret = new HashMap<Long, Integer>();
        Optional<Style> foundStyle = styleRepository.findById(styleId);
        if (foundStyle.isEmpty())
            return ret;
        List<StyleItem> styleItemList = foundStyle.get().getStyleItemList();
        for (var dinnerItem : styleItemList) {
            Item item = dinnerItem.getItem();
            if (!(item instanceof Tableware))
                continue;
            ret.put(item.getId(), dinnerItem.getItemQuantity());
        }
        return ret;
    }

    private List<StyleItem> makeStyleItemList(Style style, StyleDto styleDto){
        var ret = new ArrayList<StyleItem>();
        Map<Long, Integer> itemIdAndQuantity = styleDto.getItemIdAndQuantity();
        for(var itemId : itemIdAndQuantity.keySet()){
            StyleItem styleItem = new StyleItem();
            styleItemRepository.save(styleItem);
            styleItem.setStyle(style);
            styleItem.setItem(itemRepository.findById(itemId).get());
            styleItem.setItemQuantity(itemIdAndQuantity.get(itemId));
            ret.add(styleItem);
        }
        return ret;
    }
}
