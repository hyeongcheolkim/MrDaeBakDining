package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.TimeConst;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.item.repository.ItemRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheetItem;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderBuilder {
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final ItemRepository itemRepository;

    public void buildOrder(Order order, OrderDto orderDto) {
        order.setAddress(orderDto.getAddress());
        order.setOrderStatus(decideOrderedOrReserved(orderDto.getOrderTime()));
        order.setOrderSheetList(buildOrderSheetList(order, orderDto));
    }

    private OrderStatus decideOrderedOrReserved(LocalDateTime orderTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime openTime = LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                TimeConst.OPEN_HOUR,
                TimeConst.OPEN_MINUTE,
                0
        );
        LocalDateTime closeTime = LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                TimeConst.CLOSE_HOUR,
                TimeConst.CLOSE_MINUTE,
                0
        );
        if (orderTime.isAfter(openTime) && orderTime.isBefore(closeTime))
            return OrderStatus.ORDERED;
        else
            return OrderStatus.RESERVED;
    }

    private List<OrderSheet> buildOrderSheetList(Order order, OrderDto orderDto) {
        var ret = new ArrayList<OrderSheet>();
        List<OrderSheetDto> orderSheetDtoList = orderDto.getOrderSheetDtoList();
        for (var orderSheetDto : orderSheetDtoList) {
            OrderSheet orderSheet = new OrderSheet();
            orderSheetRepository.save(orderSheet);
            orderSheet.setOrder(order);
            orderSheet.setDinner(dinnerRepository.findById(orderSheetDto.getDinnerId()).get());
            orderSheet.setStyle(styleRepository.findById(orderSheetDto.getStyleId()).get());
            orderSheet.setOrderItemList(buildOrderSheetItemList(orderSheet, orderDto));
            ret.add(orderSheet);
        }
        return ret;
    }

    private List<OrderSheetItem> buildOrderSheetItemList(OrderSheet orderSheet, OrderDto orderDto) {
        var ret = new ArrayList<OrderSheetItem>();
        List<OrderSheetDto> orderSheetDtoList = orderDto.getOrderSheetDtoList();
        for (var orderSheetDto : orderSheetDtoList) {
            OrderSheetItem orderSheetItem = new OrderSheetItem();
            orderSheetItem.setOrderSheet(orderSheet);
            Map<Long, Integer> itemIdAndQuantity = orderSheetDto.getItemIdAndQuantity();
            for (var itemId : itemIdAndQuantity.keySet()) {
                orderSheetItem.setItem(itemRepository.findById(itemId).get());
                orderSheetItem.setItemQuantity(itemIdAndQuantity.get(itemId));
            }
            ret.add(orderSheetItem);
        }
        return ret;
    }
}
