package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final OrderBuilder orderBuilder;
    private final ClientRepository clientRepository;
    private final GuestRepository guestRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final DinnerService dinnerService;
    private final FoodRepository foodRepository;

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

    public Long updateOrderSheet(Long orderSheetId, OrderSheetDto orderSheetDto) {
        Optional<OrderSheet> foundOrderSheet = orderSheetRepository.findById(orderSheetId);
        foundOrderSheet.get().getFoodDifferenceList().clear();
        orderBuilder.buildFoodDifferenceList(foundOrderSheet.get(), orderSheetDto.getFoodIdAndDifference());
        return orderSheetId;
    }
}
