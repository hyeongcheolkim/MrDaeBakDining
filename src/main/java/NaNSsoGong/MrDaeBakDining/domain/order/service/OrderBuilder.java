package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.item.repository.ItemRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderReservedTimeRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
public class OrderBuilder {
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final ItemRepository itemRepository;
    private final OrderReservedTimeRepository orderReservedTimeRepository;

    public void buildOrder(Order order, OrderDto orderDto) {
        order.setAddress(orderDto.getAddress());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setOrderTime(orderDto.getOrderTime());
        if (order.getOrderStatus().equals(OrderStatus.RESERVED))
            saveReservedTime(order, orderDto);
        order.setOrderSheetList(buildOrderSheetList(order, orderDto));
    }

    private void saveReservedTime(Order order, OrderDto orderDto) {
        OrderReservedTime orderReservedTime = new OrderReservedTime();
        orderReservedTime.setOrder(order);
        orderReservedTime.setReservedTime(orderDto.getReserveTime());
        orderReservedTimeRepository.save(orderReservedTime);
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
            buildOrderSheetItemList(orderSheet, orderDto);
            ret.add(orderSheet);
        }
        return ret;
    }

    private void buildOrderSheetItemList(OrderSheet orderSheet, OrderDto orderDto) {
        List<OrderSheetDto> orderSheetDtoList = orderDto.getOrderSheetDtoList();
        for (var orderSheetDto : orderSheetDtoList) {
            Map<Long, Integer> itemIdAndQuantity = orderSheetDto.getItemIdAndQuantity();
            for (var itemId : itemIdAndQuantity.keySet()) {
                OrderSheetItem orderSheetItem = new OrderSheetItem();
                orderSheetItem.setOrderSheet(orderSheet);
                orderSheetItem.setItem(itemRepository.findById(itemId).get());
                orderSheetItem.setItemQuantity(itemIdAndQuantity.get(itemId));
                orderSheet.getOrderSheetItemList().add(orderSheetItem);
            }
        }
    }
}
