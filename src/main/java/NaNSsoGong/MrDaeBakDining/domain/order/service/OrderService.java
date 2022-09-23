package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final FoodService foodService;
    private final DecorationRepository decorationRepository;
    private final TablewareRepository tablewareRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final GuestRepository guestRepository;

    public Optional<ClientOrder> makeClientOrder(Client client, OrderDto orderDto) {
        ClientOrder clientOrder = buildOrder(client, orderDto);
        ClientOrder savedClientOrder = clientOrderRepository.save(clientOrder);
        client.getClientOrderList().add(savedClientOrder);
        return Optional.ofNullable(savedClientOrder);
    }

    public Optional<GuestOrder> makeGuestOrder(OrderDto orderDto) {
        Guest guest = new Guest();
        Guest savedGuest = guestRepository.save(guest);
        GuestOrder guestOrder = buildOrder(guest, orderDto);
        GuestOrder savedGuestOrder = guestOrderRepository.save(guestOrder);
        guest.setGuestOrder(savedGuestOrder);
        return Optional.ofNullable(savedGuestOrder);
    }

    public Optional<Order> cancelOrder(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            return Optional.empty();
        foundOrder.get().setOrderStatus(OrderStatus.CANCEL);
        return foundOrder;
    }

    public Boolean isMakeAbleOrder(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            return false;
        if (!isMakeAbleFoodList(foundOrder.get().getOrderFoodList()))
            return false;
        if (!isMakeAbleDecorationList(foundOrder.get().getOrderDecorationList()))
            return false;
        if (!isMakeAbleTablewareList(foundOrder.get().getOrderTablewareList()))
            return false;
        return true;
    }

    public void makeTableware(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            return;
        List<OrderTableware> orderTablewareList = foundOrder.get().getOrderTablewareList();
        for (var orderTableware : orderTablewareList) {
            Long tablewareId = orderTableware.getTableware().getId();
            Integer tablewareQuantity = orderTableware.getTablewareQuantity();
            Tableware tableware = tablewareRepository.findById(tablewareId).get();
            tableware.setStockQuantity(tableware.getStockQuantity() - tablewareQuantity);
        }
    }

    public void makeDecoration(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            return;
        List<OrderDecoration> orderDecorationList = foundOrder.get().getOrderDecorationList();
        for (var orderDecoration : orderDecorationList) {
            Long decorationId = orderDecoration.getDecoration().getId();
            Integer decorationQuantity = orderDecoration.getDecorationQuantity();
            Decoration decoration = decorationRepository.findById(decorationId).get();
            decoration.setStockQuantity(decoration.getStockQuantity() - decorationQuantity);
        }
    }

    public void makeFood(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            return;
        List<OrderFood> orderFoodList = foundOrder.get().getOrderFoodList();
        for (var orderFood : orderFoodList) {
            Long foodId = orderFood.getFood().getId();
            Integer foodQuantity = orderFood.getFoodQuantity();
            while (foodQuantity-- != 0)
                foodService.makeFood(foodId);
        }
    }

    private ClientOrder buildOrder(Client client, OrderDto orderDto) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrder.setAddress(new Address(
                orderDto.getAddress().getCity(),
                orderDto.getAddress().getStreet(),
                orderDto.getAddress().getZipcode()
        ));
        clientOrder.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        clientOrder.setOrderStatus(OrderStatus.ORDERED);
        clientOrder.setOrderFoodList(makeOrderFoodList(clientOrder, orderDto));
        clientOrder.setOrderDecorationList(makeOrderDecorationList(clientOrder, orderDto));
        clientOrder.setOrderTablewareList(makeOrderTablewareList(clientOrder, orderDto));
        return clientOrder;
    }

    private GuestOrder buildOrder(Guest guest, OrderDto orderDto) {
        GuestOrder guestOrder = new GuestOrder();
        guestOrder.setGuest(guest);
        guestOrder.setAddress(new Address(
                orderDto.getAddress().getCity(),
                orderDto.getAddress().getStreet(),
                orderDto.getAddress().getZipcode()
        ));
        guestOrder.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        guestOrder.setOrderStatus(OrderStatus.ORDERED);
        guestOrder.setOrderFoodList(makeOrderFoodList(guestOrder, orderDto));
        guestOrder.setOrderDecorationList(makeOrderDecorationList(guestOrder, orderDto));
        guestOrder.setOrderTablewareList(makeOrderTablewareList(guestOrder, orderDto));
        return guestOrder;
    }

    private List<OrderFood> makeOrderFoodList(Order order, OrderDto orderDto) {
        var ret = new ArrayList<OrderFood>();
        Map<Long, Integer> foodIdAndQuantity = orderDto.getFoodIdAndQuantity();
        for (var foodId : foodIdAndQuantity.keySet()) {
            Optional<Food> foundFood = foodRepository.findById(foodId);
            if (foundFood.isEmpty())
                continue;
            OrderFood orderFood = new OrderFood();
            orderFood.setOrder(order);
            orderFood.setFood(foundFood.get());
            orderFood.setFoodQuantity(foodIdAndQuantity.get(foodId));
            ret.add(orderFood);
        }
        return ret;
    }

    private List<OrderDecoration> makeOrderDecorationList(Order order, OrderDto orderDTO) {
        var ret = new ArrayList<OrderDecoration>();
        Map<Long, Integer> decorationIdAndQuantity = orderDTO.getDecorationIdAndQuantity();
        for (var decorationId : decorationIdAndQuantity.keySet()) {
            Optional<Decoration> foundDecoration = decorationRepository.findById(decorationId);
            if (foundDecoration.isEmpty())
                continue;
            OrderDecoration orderDecoration = new OrderDecoration();
            orderDecoration.setOrder(order);
            orderDecoration.setDecoration(foundDecoration.get());
            orderDecoration.setDecorationQuantity(decorationIdAndQuantity.get(decorationId));
            ret.add(orderDecoration);
        }
        return ret;
    }

    private List<OrderTableware> makeOrderTablewareList(Order order, OrderDto orderDTO) {
        var ret = new ArrayList<OrderTableware>();
        Map<Long, Integer> tablewareIdAndQuantity = orderDTO.getTablewareIdAndQuantity();
        for (var tablewareId : tablewareIdAndQuantity.keySet()) {
            Optional<Tableware> foundTableware = tablewareRepository.findById(tablewareId);
            if (foundTableware.isEmpty())
                continue;
            OrderTableware orderTableware = new OrderTableware();
            orderTableware.setOrder(order);
            orderTableware.setTableware(foundTableware.get());
            orderTableware.setTablewareQuantity(tablewareIdAndQuantity.get(tablewareId));
            ret.add(orderTableware);
        }
        return ret;
    }

    private Boolean isMakeAbleFoodList(List<OrderFood> orderFoodList) {
        for (var orderFood : orderFoodList) {
            Long foodId = orderFood.getFood().getId();
            if (!foodService.isMakeAble(foodId))
                return false;
        }
        return true;
    }

    private Boolean isMakeAbleDecorationList(List<OrderDecoration> orderDecorationList) {
        for (var orderDecoration : orderDecorationList) {
            Optional<Decoration> foundDecoration = decorationRepository.findById(orderDecoration.getDecoration().getId());
            if (foundDecoration.isEmpty())
                return false;
            Integer orderQuantity = orderDecoration.getDecorationQuantity();
            Integer stockQuantity = foundDecoration.get().getStockQuantity();
            if (stockQuantity < orderQuantity)
                return false;
        }
        return true;
    }

    private Boolean isMakeAbleTablewareList(List<OrderTableware> tablewareList) {
        for (var orderTableware : tablewareList) {
            Optional<Tableware> foundTableware = tablewareRepository.findById(orderTableware.getId());
            if (foundTableware.isEmpty())
                return false;
            Integer orderQuantity = orderTableware.getTablewareQuantity();
            Integer stockQuantity = foundTableware.get().getStockQuantity();
            if (stockQuantity < orderQuantity)
                return false;
        }
        return true;
    }
}
