package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final OrderBuilder orderBuilder;
    private final ClientRepository clientRepository;
    private final GuestRepository guestRepository;

    public Long makeClientOrder(Long clientId, OrderDto orderDto) {
        Optional<Client> foundClient = clientRepository.findById(clientId);
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(foundClient.get());
        clientOrderRepository.save(clientOrder);
        orderBuilder.buildOrder(clientOrder, orderDto);
        return clientOrder.getId();
    }

    public Long makeGuestOrder(Long guestId, OrderDto orderDto) {
        Optional<Guest> foundGuest = guestRepository.findById(guestId);
        GuestOrder guestOrder = new GuestOrder();
        guestOrder.setGuest(foundGuest.get());
        guestOrderRepository.save(guestOrder);
        orderBuilder.buildOrder(guestOrder, orderDto);
        return guestOrder.getId();
    }
}
