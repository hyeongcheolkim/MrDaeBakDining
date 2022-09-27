package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final OrderBuilder orderBuilder;

    public Long makeClientOrder(Client client, OrderDto orderDto) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrderRepository.save(clientOrder);
        orderBuilder.buildOrder(clientOrder, orderDto);
        return clientOrder.getId();
    }

    public Long makeGuestOrder(Guest guest, OrderDto orderDto) {
        GuestOrder guestOrder = new GuestOrder();
        guestOrder.setGuest(guest);
        guestOrderRepository.save(guestOrder);
        orderBuilder.buildOrder(guestOrder, orderDto);
        return guestOrder.getId();
    }
}
