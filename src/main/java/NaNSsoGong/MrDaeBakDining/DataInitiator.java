package NaNSsoGong.MrDaeBakDining;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitiator {
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final TablewareRepository tablewareRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final GuestRepository guestRepository;
    private final ChefRepository chefRepository;
    private final ClientRepository clientRepository;
    private final MemberRepository memberRepository;

    public Client client1;
    public Client client2;

    public Food food1;
    public Food food2;

    public Tableware tableware1;
    public Tableware tableware2;

    public Ingredient ingredient1;
    public Ingredient ingredient2;
    public Ingredient ingredient3;
    public Ingredient ingredient4;
    public Ingredient ingredient5;

    public Recipe recipe1;
    public Recipe recipe2;
    public Recipe recipe3;
    public Recipe recipe4;
    public Recipe recipe5;
    public Recipe recipe6;

    public Rider rider;
    public Guest guest;
    public Chef chef;

    public Dinner dinner;

    public Style style1;
    public Style style2;

    public OrderSheetDto orderSheetDto;

    public OrderDto orderDto;

    public ClientOrder clientOrder;
    public GuestOrder guestOrder;

    @Transactional
    public void factoryInit() {
        for (int i = 0; i < 100; ++i) {
            var client = new Client();
            client.setName("Client" + i);
            client.setAddress(new Address("seoul" + i, "mangu" + i, "12345" + i));
            client.setCardNumber("123412341234124" + i);
            client.setLoginId("ClientAId" + i);
            client.setPassword("!ClientA12" + i);
            client.setEnable(true);
            client.setClientGrade(ClientGrade.DIAMOND);
            memberRepository.save(client);

            var guest = new Guest();
            guest.setName("나손님"+i);
            guest.setCardNumber("213412341234123" + i);
            guestRepository.save(guest);

            var chef = new Chef();
            chef.setEnable(true);
            chef.setName("나쉐프" + i);
            chef.setLoginId("ChefId1234" + i);
            chef.setPassword("!Chef12341234" + i);
            chefRepository.save(chef);

            var tableware1 = new Tableware();
            tableware1.setName("와인잔" + i);
            tablewareRepository.save(tableware1);

            var tableware2 = new Tableware();
            tableware2.setName("냅킨" + i);
            tablewareRepository.save(tableware2);

            var food1 = new Food();
            food1.setName("스테이크" + i);
            food1.setSellPrice(30000);
            food1.setFoodCategory(FoodCategory.MEAT);
            foodRepository.save(food1);

            var food2 = new Food();
            food2.setName("치킨" + i);
            food2.setSellPrice(25000);
            food2.setFoodCategory(FoodCategory.MEAT);
            foodRepository.save(food2);

            var ingredient1 = new Ingredient();
            ingredient1.setName("소고기" + i);
            ingredient1.setStockQuantity(5);
            var ingredient2 = new Ingredient();
            ingredient2.setName("후추" + i);
            ingredient2.setStockQuantity(5);
            var ingredient3 = new Ingredient();
            ingredient3.setName("버터" + i);
            ingredient3.setStockQuantity(5);
            var ingredient4 = new Ingredient();
            ingredient4.setName("닭" + i);
            ingredient4.setStockQuantity(5);
            var ingredient5 = new Ingredient();
            ingredient5.setName("튀김가루" + i);
            ingredient5.setStockQuantity(5);
            ingredientRepository.save(ingredient1);
            ingredientRepository.save(ingredient2);
            ingredientRepository.save(ingredient3);
            ingredientRepository.save(ingredient4);
            ingredientRepository.save(ingredient5);

            recipeService.makeRecipe(food1, ingredient1, 1);
            recipeService.makeRecipe(food1, ingredient2, 2);
            recipeService.makeRecipe(food1, ingredient3, 1);
            recipeService.makeRecipe(food2, ingredient4, 1);
            recipeService.makeRecipe(food2, ingredient5, 1);
            recipeService.makeRecipe(food2, ingredient2, 1);

            var rider = new Rider();
            rider.setName("나배달원" + i);
            rider.setEnable(true);
            rider.setLoginId("riderId1234");
            rider.setPassword("!Rider12341234");
            memberRepository.save(rider);

            var style = new Style();
            styleRepository.save(style);
            style.setName("스타일" + i);
            var styleItem = new StyleTableware();
            styleItem.setTableware(tableware1);
            styleItem.setStyle(style);
            style.getStyleTablewareList().add(styleItem);

            var dinner = new Dinner();
            dinnerRepository.save(dinner);
            dinner.setName("디너" + i);

            var dinnerItem1 = new DinnerFood();
            dinnerItem1.setDinner(dinner);
            dinnerItem1.setFood(food1);
            dinnerItem1.setFoodQuantity(3);


            dinner.getDinnerFoodList().add(dinnerItem1);

            Map<Long, Integer> itemIdAndQuantity = new HashMap<>();

            itemIdAndQuantity.put(food1.getId(), 1);
            itemIdAndQuantity.put(food2.getId(), 1);


            for (int j = 0; j < 10; ++j) {
                var orderSheetDto = OrderSheetDto.builder()
                        .dinnerId(dinner.getId())
                        .styleId(style.getId())
                        .foodIdAndDifference(itemIdAndQuantity)
                        .build();

                var orderDto = OrderDto.builder()
                        .address(client.getAddress())
                        .orderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                        .orderSheetDtoList(List.of(orderSheetDto))
                        .orderStatus(OrderStatus.ORDERED)
                        .build();
                clientOrder = orderService.makeClientOrder(client, orderDto);
                guestOrder = orderService.makeGuestOrder(guest, orderDto);
            }
        }
    }

    @Transactional
    public void dataInit() {
        client1 = new Client();
        client1.setName("ClientA");
        client1.setAddress(new Address("seoul", "mangu", "12345"));
        client1.setCardNumber("1234123412341234");
        client1.setLoginId("ClientAId");
        client1.setPassword("!ClientA12");
        client1.setEnable(true);
        client1.setClientGrade(ClientGrade.DIAMOND);
        memberRepository.save(client1);

        client2 = new Client();
        client2.setName("ClientB");
        client2.setAddress(new Address("suncheon", "shindae", "33321"));
        client2.setCardNumber("1111222233334444");
        client2.setLoginId("ClientBId");
        client2.setPassword("!ClientB12");
        client2.setEnable(true);
        client2.setClientGrade(ClientGrade.BRONZE);
        memberRepository.save(client2);

        guest = new Guest();
        guest.setName("나손님");
        guest.setCardNumber("2134123412341234");
        guestRepository.save(guest);

        chef = new Chef();
        chef.setEnable(true);
        chef.setName("나쉐프");
        chef.setLoginId("ChefId1234");
        chef.setPassword("!Chef12341234");
        chefRepository.save(chef);

        tableware1 = new Tableware();
        tableware1.setName("와인잔");
        tablewareRepository.save(tableware1);

        tableware2 = new Tableware();
        tableware2.setName("냅킨");
        tablewareRepository.save(tableware2);

        food1 = new Food();
        food1.setName("스테이크");
        food1.setSellPrice(30000);
        food1.setFoodCategory(FoodCategory.MEAT);
        foodRepository.save(food1);

        food2 = new Food();
        food2.setName("치킨");
        food2.setSellPrice(25000);
        food2.setFoodCategory(FoodCategory.MEAT);
        foodRepository.save(food2);

        ingredient1 = new Ingredient();
        ingredient1.setName("소고기");
        ingredient1.setStockQuantity(5);
        ingredient2 = new Ingredient();
        ingredient2.setName("후추");
        ingredient2.setStockQuantity(5);
        ingredient3 = new Ingredient();
        ingredient3.setName("버터");
        ingredient3.setStockQuantity(5);
        ingredient4 = new Ingredient();
        ingredient4.setName("닭");
        ingredient4.setStockQuantity(5);
        ingredient5 = new Ingredient();
        ingredient5.setName("튀김가루");
        ingredient5.setStockQuantity(5);
        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);
        ingredientRepository.save(ingredient4);
        ingredientRepository.save(ingredient5);

        recipeService.makeRecipe(food1, ingredient1, 1);
        recipeService.makeRecipe(food1, ingredient2, 2);
        recipeService.makeRecipe(food1, ingredient3, 1);
        recipeService.makeRecipe(food2, ingredient4, 1);
        recipeService.makeRecipe(food2, ingredient5, 1);
        recipeService.makeRecipe(food2, ingredient2, 1);

        rider = new Rider();
        rider.setName("나배달원");
        rider.setEnable(true);
        rider.setLoginId("riderId1234");
        rider.setPassword("!Rider12341234");
        memberRepository.save(rider);

        style1 = new Style();
        styleRepository.save(style1);
        style1.setName("디럭스");
        style1.setEnable(true);
        style1.setOrderable(true);
        style1.setSellPrice(5000);

        style2 = new Style();
        styleRepository.save(style2);
        style2.setName("그랜드");
        style2.setEnable(true);
        style2.setOrderable(true);
        style2.setSellPrice(15000);

        StyleTableware styleTableware = new StyleTableware();
        styleTableware.setTableware(tableware1);
        styleTableware.setStyle(style1);
        style1.getStyleTablewareList().add(styleTableware);

        dinner = new Dinner();
        dinnerRepository.save(dinner);
        dinner.setName("발렌타인디너");
        dinner.setDescription("발렌타인 장식과 같이 제공됩니다");
        dinner.setEnable(true);
        dinner.setOrderable(true);

        ExcludedStyle excludedStyle = new ExcludedStyle();
        excludedStyle.setStyle(style2);
        excludedStyle.setDinner(dinner);

        dinner.getExcludedStyleList().add(excludedStyle);

        DinnerFood dinnerFood1 = new DinnerFood();
        dinnerFood1.setDinner(dinner);
        dinnerFood1.setFood(food1);
        dinnerFood1.setFoodQuantity(3);

        dinner.getDinnerFoodList().add(dinnerFood1);
//
        Map<Long, Integer> itemIdAndQuantity = new HashMap<>();

        itemIdAndQuantity.put(food1.getId(), 1);
        itemIdAndQuantity.put(food2.getId(), 1);

        dinnerRepository.save(dinner);

        orderSheetDto = OrderSheetDto.builder()
                .dinnerId(dinner.getId())
                .styleId(style1.getId())
                .foodIdAndDifference(itemIdAndQuantity)
                .build();

        orderDto = OrderDto.builder()
                .address(client1.getAddress())
                .orderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .orderSheetDtoList(List.of(orderSheetDto))
                .orderStatus(OrderStatus.ORDERED)
                .build();


        clientOrder = (ClientOrder) orderService.makeClientOrder(client1, orderDto);
        guestOrder = (GuestOrder) orderService.makeGuestOrder(guest, orderDto);
    }


}