package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.time.TimeConst;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.order.SalePolicy;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final OrderBuilder orderBuilder;
    private final OrderSheetService orderSheetService;

    public ClientOrder makeClientOrder(Client client, OrderDto orderDto) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrderRepository.save(clientOrder);
        clientOrder.setClient(client);
        orderBuilder.buildOrder(clientOrder, orderDto);

        client.getClientOrderList().add(clientOrder);
        return clientOrder;
    }

    public GuestOrder makeGuestOrder(Guest guest, OrderDto orderDto) {
        GuestOrder guestOrder = new GuestOrder();
        guestOrderRepository.save(guestOrder);
        guestOrder.setGuest(guest);
        orderBuilder.buildOrder(guestOrder, orderDto);

        guest.setGuestOrder(guestOrder);
        return guestOrder;
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

    public Boolean isOpenTime(LocalDateTime orderTime){
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime openTime = LocalDateTime.of(
                nowTime.getYear(),
                nowTime.getMonth(),
                nowTime.getDayOfMonth(),
                TimeConst.getOpenHour(),
                TimeConst.getOpenMinute()
        );
        LocalDateTime closeTime = LocalDateTime.of(
                nowTime.getYear(),
                nowTime.getMonth(),
                nowTime.getDayOfMonth(),
                TimeConst.getCloseHour(),
                TimeConst.getCloseMinute()
        );
        return orderTime.isAfter(openTime) && orderTime.isBefore(closeTime);
    }
}
