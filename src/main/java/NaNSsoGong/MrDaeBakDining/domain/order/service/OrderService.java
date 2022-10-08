package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.order.SalePolicy;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final OrderBuilder orderBuilder;
    private final OrderSheetService orderSheetService;

    public ClientOrder makeClientOrder(Client client, OrderDto orderDto) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrderRepository.save(clientOrder);
        orderBuilder.buildOrder(clientOrder, orderDto);
        return clientOrder;
    }

    public GuestOrder makeGuestOrder(Guest guest, OrderDto orderDto) {
        GuestOrder guestOrder = new GuestOrder();
        guestOrder.setGuest(guest);
        guestOrderRepository.save(guestOrder);
        orderBuilder.buildOrder(guestOrder, orderDto);
        return guestOrder;
    }

    public OrderSheet updateOrderSheet(OrderSheet orderSheet, OrderSheetDto orderSheetDto) {
        orderSheet.getFoodDifferenceList().clear();
        orderBuilder.buildFoodDifferenceList(orderSheet, orderSheetDto.getFoodIdAndDifference());
        return orderSheet;
    }

    public Integer orderPriceAfterSale(Order order) {
        Integer rate = 100;
        if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(order)))
            rate -= SalePolicy.saleRate(((ClientOrder) order).getClient().getClientGrade());
        return orderPriceBeforeSale(order) * rate / 100;
    }

    public Integer orderPriceBeforeSale(Order order) {
        return order.getOrderSheetList().stream()
                .map(orderSheetService::orderSheetPriceBeforeSale)
                .reduce(0, Integer::sum);
    }

}
