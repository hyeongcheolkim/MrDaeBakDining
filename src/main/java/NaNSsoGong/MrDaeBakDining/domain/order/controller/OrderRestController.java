package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.*;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.*;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderBuilder;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.exception.exception.*;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.ORDERED;
import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.RESERVED;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_RIDER;

@Tag(name = "order")
@RestController
@Transactional
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class OrderRestController {
    private final OrderSheetRepository orderSheetRepository;
    private final ClientRepository clientRepository;
    private final DinnerRepository dinnerRepository;
    private final OrderRepository orderRepository;
    private final GuestRepository guestRepository;
    private final RiderRepository riderRepository;
    private final StyleRepository styleRepository;
    private final OrderService orderService;
    private final FoodService foodService;
    private final RecipeService recipeService;
    private final OrderBuilder orderBuilder;

    @Operation(summary = "???????????? ?????????????????? ??? ?????????????????? ??????", description = "????????? ????????? ?????? ???????????? ?????? ???????????? ?????? ???????????? ???????????? ???????????????. ???????????? ???????????? ???????????? ????????????????????? ???????????????.")
    @GetMapping("/make/{orderId}")
    public ResponseEntity<IsMakeableOrderResponse> isMakeableOrder(@PathVariable(name = "orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        Map<Food, Integer> totalFoodAndQuantity = foodService.calculateTotalFoodQuantity(order);
        Map<Ingredient, Integer> totalDemandIngredient = recipeService.calculateTotalDemandIngredient(totalFoodAndQuantity);

        List<IngredientDemandAndStockInfoResponse> ingredientDemandAndStockInfoResponseList =
                totalDemandIngredient.entrySet()
                        .stream()
                        .map(e -> new IngredientDemandAndStockInfoResponse(
                                e.getKey().getId(),
                                e.getKey().getName(),
                                e.getKey().getStockQuantity(),
                                e.getValue()))
                        .collect(Collectors.toList());
        boolean allMakeable = ingredientDemandAndStockInfoResponseList.stream()
                .allMatch(e -> e.getStockQuantity() >= e.getDemandQuantity());

        return ResponseEntity.ok().body(IsMakeableOrderResponse.builder()
                .makeable(allMakeable)
                .ingredientDemandAndStockInfoList(ingredientDemandAndStockInfoResponseList)
                .build());
    }

    @Operation(summary = "????????????", description = "????????? ????????? ?????????????????? ??? ?????? ??????????????? ?????????????????? ??????????????????.")
    @PostMapping("/make/{orderId}")
    public ResponseEntity<String> makeOrderFood(@PathVariable(name = "orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        Map<Food, Integer> totalFoodAndQuantity = foodService.calculateTotalFoodQuantity(order);
        Map<Ingredient, Integer> totalDemandIngredient = recipeService.calculateTotalDemandIngredient(totalFoodAndQuantity);

        for (var e : totalDemandIngredient.entrySet()) {
            Ingredient ingredient = e.getKey();
            Integer demandQuantity = e.getValue();
            Integer nextQuantity = ingredient.getStockQuantity() - demandQuantity;
            if (nextQuantity < 0)
                throw new MinusQuantityException("?????????????????? ????????????????????? ????????????");
            ingredient.setStockQuantity(nextQuantity);
        }

        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "??????????????? ??????", description = "??????????????? ????????? ???????????????")
    @PostMapping("/client")
    public ResponseEntity<ClientOrderInfoResponse> clientOrderCreate(
            @Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CLIENT) Long clientId,
            @RequestBody @Validated OrderCreateRequest orderCreateRequest) {
        orderValid(orderCreateRequest);

        Client client = clientRepository.findById(clientId).orElseThrow(() -> {
            throw new NoExistInstanceException(Client.class);
        });

        OrderDto orderDto = orderCreateRequest.toOrderDto();
        ClientOrder clientOrder = orderService.makeClientOrder(client, orderDto);

        Integer actualPrice = orderService.orderPriceAfterSale(clientOrder);
        if (!actualPrice.equals(orderDto.getTotalPriceAfterSale()))
            throw new PriceNotSameException(orderDto.getTotalPriceAfterSale(), actualPrice);

        return ResponseEntity.ok().body(new ClientOrderInfoResponse(clientOrder));
    }


    @Operation(summary = "????????? ??????", description = "????????? ?????? ????????????")
    @PostMapping("/guest")
    public ResponseEntity<GuestOrderInfoResponse> guestOrderCreate(@RequestBody @Validated GuestOrderCreateRequest guestOrderRequest) {
        orderValid(guestOrderRequest);

        OrderDto orderDto = guestOrderRequest.toOrderDto();
        Guest guest = new Guest(guestOrderRequest.getName(), guestOrderRequest.getCardNumber());
        guestRepository.save(guest);
        GuestOrder guestOrder = orderService.makeGuestOrder(guest, orderDto);
        Integer actualPrice = orderService.orderPriceAfterSale(guestOrder);
        if (!actualPrice.equals(orderDto.getTotalPriceAfterSale()))
            throw new PriceNotSameException(orderDto.getTotalPriceAfterSale(), actualPrice);

        return ResponseEntity.ok().body(new GuestOrderInfoResponse(guestOrder));
    }

    @Operation(summary = "???????????? ??????")
    @PatchMapping("/status")
    public ResponseEntity<String> changeOrderStatus(@RequestBody @Validated ChangeOrderStatusRequest changeOrderStatusRequest) {
        Long orderId = changeOrderStatusRequest.getOrderId();
        OrderStatus orderStatus = changeOrderStatusRequest.getOrderStatus();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        order.setOrderStatus(orderStatus);
        return ResponseEntity.ok().body("????????????????????????");
    }

    @Operation(summary = "????????? ?????? by riderId", description = "?????????????????????, ?????? ???????????? ????????? ??? ????????????")
    @PatchMapping("/rider/{riderId}")
    public ResponseEntity<String> changeRiderByRiderId(@PathVariable(value = "riderId") Long riderId,
                                                       @RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        Order order = orderRepository.findById(changeRiderRequest.getOrderId()).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        order.setRider(rider);
        return ResponseEntity.ok().body("?????????????????????");
    }

    @Operation(summary = "????????? ?????? by Session", description = "?????????????????????, ?????? ???????????? ????????? ??? ????????????")
    @PatchMapping("/rider")
    public ResponseEntity<String> changeRiderBySession(@Parameter(name = "riderId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_RIDER) Long riderId,
                                                       @RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        Order order = orderRepository.findById(changeRiderRequest.getOrderId()).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        order.setRider(rider);
        return ResponseEntity.ok().body("?????????????????????");
    }

    @Operation(summary = "????????????", description = "????????? ????????? ??? ??????????????? ???????????????")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderUpdateResponse> orderUpdate(
            @PathVariable(name = "orderId") Long orderId,
            @RequestBody OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        if (!(order.getOrderStatus().equals(ORDERED) || order.getOrderStatus().equals(RESERVED)))
            throw new NoProperOrderStatusException("?????? ????????? ??????????????? ORDERED????????? RESERVED??? ????????? ???????????????");

        Integer previousTotalPriceAfterSale = order.getTotalPriceAfterSale();

        List<OrderSheetUpdateRequest> orderSheetUpdateRequestList = orderUpdateRequest.getOrderSheetUpdateRequestList();
        for (var orderSheetUpdateRequest : orderSheetUpdateRequestList) {
            OrderSheet orderSheet = orderSheetRepository.findById(orderSheetUpdateRequest.getOrderSheetId()).orElseThrow(() -> {
                throw new NoExistInstanceException(OrderSheet.class);
            });
            Style style = styleRepository.findById(orderSheetUpdateRequest.getStyleId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Style.class);
            });
            Dinner dinner = dinnerRepository.findById(orderSheetUpdateRequest.getDinnerId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Dinner.class);
            });

            if (!order.getOrderSheetList().contains(orderSheet))
                throw new BusinessException("????????? ??????????????? ???????????? ????????? ?????? ????????????");
            if (!dinner.getOrderable())
                throw new NoOderableInstanceException(Dinner.class, dinner.getId());
            if (!style.getOrderable())
                throw new NoOderableInstanceException(Style.class, style.getId());

            orderSheet.setStyle(style);
            orderSheet.setDinner(dinner);
            orderSheet.getFoodDifferenceList().clear();
            orderBuilder.addToFoodDifferenceList(orderSheet, orderSheetUpdateRequest.getFoodIdAndDifference());
        }

        Integer nextTotalPriceAfterSale = orderService.orderPriceAfterSale(order);
        order.setTotalPriceAfterSale(nextTotalPriceAfterSale);
        return ResponseEntity.ok().body(new OrderUpdateResponse(order, previousTotalPriceAfterSale, nextTotalPriceAfterSale));
    }

    private void orderValid(OrderCreateRequest orderCreateRequest) {
        if (!(orderCreateRequest.getOrderStatus().equals(ORDERED) || orderCreateRequest.getOrderStatus().equals(RESERVED)))
            throw new NoProperOrderStatusException("OrderStatus??? ??????????????? ??????????????? ?????????");
        if (orderCreateRequest.getOrderStatus().equals(RESERVED) && orderCreateRequest.getReservedTime() == null)
            throw new NoProperOrderStatusException("OrderStatus??? RESERVED??????, reservedTime??? null??? ??? ????????????");
        if (orderCreateRequest.getOrderStatus().equals(ORDERED) && !orderService.isOpenTime(orderCreateRequest.getOrderTime()))
            throw new NoOpenTimeException(
                    orderCreateRequest.getOrderTime().getHour(),
                    orderCreateRequest.getOrderTime().getMinute());

        for (var orderSheet : orderCreateRequest.getOrderSheetCreateRequestList()) {
            Dinner dinner = dinnerRepository.findById(orderSheet.getDinnerId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Dinner.class);
            });
            Style style = styleRepository.findById(orderSheet.getStyleId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Style.class);
            });
            if (dinner.getExcludedStyleList().stream()
                    .map(ExcludedStyle::getStyle)
                    .anyMatch(e -> e == style))
                throw new BusinessException(String.format("dinnerId:%d?????? styleId:%d??? ?????? ??? ????????????", dinner.getId(), style.getId()));
        }
    }
}
