package NaNSsoGong.MrDaeBakDining.domain.dinner.service;

import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerItem;
import NaNSsoGong.MrDaeBakDining.domain.dinner.dto.DinnerDto;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerItemRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.item.domain.Item;
import NaNSsoGong.MrDaeBakDining.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DinnerService {
    private final DinnerRepository dinnerRepository;
    private final ItemRepository itemRepository;
    private final DinnerItemRepository dinnerItemRepository;

    public Long makeDinner(DinnerDto dinnerDto) {
        Dinner dinner = new Dinner();
        dinnerRepository.save(dinner);
        dinner.setName(dinnerDto.getName());
        dinner.setDinnerItemList(makeDinnerItemList(dinner, dinnerDto));
        return dinner.getId();
    }

    public Map<Long, Integer> FoodIdAndQuantity(Long dinnerId) {
        var ret = new HashMap<Long, Integer>();
        Optional<Dinner> foundDinner = dinnerRepository.findById(dinnerId);
        if (foundDinner.isEmpty())
            return ret;
        List<DinnerItem> dinnerItemList = foundDinner.get().getDinnerItemList();
        for (var dinnerItem : dinnerItemList) {
            Item item = dinnerItem.getItem();
            if (!(item instanceof Food))
                continue;
            ret.put(item.getId(), dinnerItem.getItemQuantity());
        }
        return ret;
    }

    public Map<Long, Integer> DecorationIdAndQuantity(Long dinnerId) {
        var ret = new HashMap<Long, Integer>();
        Optional<Dinner> foundDinner = dinnerRepository.findById(dinnerId);
        if (foundDinner.isEmpty())
            return ret;
        List<DinnerItem> dinnerItemList = foundDinner.get().getDinnerItemList();
        for (var dinnerItem : dinnerItemList) {
            Item item = dinnerItem.getItem();
            if (!(item instanceof Decoration))
                continue;
            ret.put(item.getId(), dinnerItem.getItemQuantity());
        }
        return ret;
    }

    private List<DinnerItem> makeDinnerItemList(Dinner dinner, DinnerDto dinnerDto) {
        var ret = new ArrayList<DinnerItem>();
        Map<Long, Integer> itemIdAndQuantity = dinnerDto.getItemIdAndQuantity();
        for (var itemId : itemIdAndQuantity.keySet()) {
            DinnerItem dinnerItem = new DinnerItem();
            dinnerItemRepository.save(dinnerItem);
            dinnerItem.setDinner(dinner);
            dinnerItem.setItem(itemRepository.findById(itemId).get());
            dinnerItem.setItemQuantity(itemIdAndQuantity.get(itemId));
            ret.add(dinnerItem);
        }
        return ret;
    }
}
