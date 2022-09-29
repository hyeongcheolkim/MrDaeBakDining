package NaNSsoGong.MrDaeBakDining;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerItem;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerItemRepository;
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
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleItem;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleItemRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
    private final DecorationRepository decorationRepository;
    private final TablewareRepository tablewareRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final GuestRepository guestRepository;
    private final ChefRepository chefRepository;
    private final ClientRepository clientRepository;
    private final MemberRepository memberRepository;
    private final DinnerItemRepository dinnerItemRepository;
    private final StyleItemRepository styleItemRepository;

    public Client client1;
    public Client client2;

    public Food food1;
    public Food food2;

    public Decoration decoration1;
    public Decoration decoration2;

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
    public Style style;

    public OrderSheetDto orderSheetDto;

    public OrderDto orderDto;

    public ClientOrder clientOrder;
    public GuestOrder guestOrder;

//    @PostConstruct
    void postConstructInit(){
        this.init();
    }

    public void init() {
        client1 = new Client();
        client1.setName("memberA");
        client1.setAddress(new Address("seoul", "mangu", "12345"));
        client1.setCardNumber("1234123412341234");
        client1.setLoginId("memberAId");
        client1.setPassword("!meberApassword12");
        client1.setEnable(true);
        client1.setClientGrade(ClientGrade.DIAMOND);
        memberRepository.save(client1);

        client2 = new Client();
        client2.setName("memberB");
        client2.setAddress(new Address("suncheon", "shindae", "33321"));
        client2.setCardNumber("1111222233334444");
        client2.setLoginId("memberBId");
        client2.setPassword("!meberBpassword12");
        client2.setEnable(true);
        client2.setClientGrade(ClientGrade.BRONZE);
        memberRepository.save(client2);

        guest = new Guest();
        guest.setCardNumber("2134123412341234");
        guestRepository.save(guest);

        chef = new Chef();
        chef.setEnable(true);
        chef.setName("나쉐프");
        chef.setLoginId("CHEFLOGIN");
        chef.setPassword("!chefchef123");
        chefRepository.save(chef);

        decoration1 = new Decoration();
        decoration1.setName("하트장식");
        decoration1.setStockQuantity(2);
        decorationRepository.save(decoration1);

        decoration2 = new Decoration();
        decoration2.setName("달빛장식");
        decoration2.setStockQuantity(1);
        decorationRepository.save(decoration2);

        tableware1 = new Tableware();
        tableware1.setName("와인잔");
        tableware1.setStockQuantity(4);
        tablewareRepository.save(tableware1);

        tableware2 = new Tableware();
        tableware2.setName("냅킨");
        tableware2.setStockQuantity(2);
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
        ingredient1.setStockQuantity(2);
        ingredient2 = new Ingredient();
        ingredient2.setName("후추");
        ingredient2.setStockQuantity(3);
        ingredient3 = new Ingredient();
        ingredient3.setName("버터");
        ingredient3.setStockQuantity(3);
        ingredient4 = new Ingredient();
        ingredient4.setName("닭");
        ingredient4.setStockQuantity(2);
        ingredient5 = new Ingredient();
        ingredient5.setName("튀김가루");
        ingredient5.setStockQuantity(2);
        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);
        ingredientRepository.save(ingredient4);
        ingredientRepository.save(ingredient5);

        recipe1 = recipeRepository.findById(recipeService.makeRecipe(food1.getId(), ingredient1.getId(), 1)).get();
        recipe1 = recipeRepository.findById(recipeService.makeRecipe(food1.getId(), ingredient2.getId(), 2)).get();
        recipe1 = recipeRepository.findById(recipeService.makeRecipe(food1.getId(), ingredient3.getId(), 1)).get();
        recipe1 = recipeRepository.findById(recipeService.makeRecipe(food2.getId(), ingredient4.getId(), 1)).get();
        recipe1 = recipeRepository.findById(recipeService.makeRecipe(food2.getId(), ingredient5.getId(), 1)).get();
        recipe1 = recipeRepository.findById(recipeService.makeRecipe(food2.getId(), ingredient2.getId(), 1)).get();

        rider = new Rider();
        rider.setName("나배달원");
        rider.setEnable(true);
        rider.setLoginId("deliver");
        rider.setPassword("!rider121212");
        memberRepository.save(rider);

        style = new Style();
        styleRepository.save(style);
        style.setName("스타일");
        StyleItem styleItem = new StyleItem();
        styleItemRepository.save(styleItem);
        styleItem.setItem(tableware1);
        styleItem.setStyle(style);
        styleItem.setItemQuantity(3);
        style.setStyleItemList(List.of(styleItem));

        dinner = new Dinner();
        dinnerRepository.save(dinner);
        dinner.setName("디너");
        DinnerItem dinnerItem1 = new DinnerItem();
        dinnerItemRepository.save(dinnerItem1);
        dinnerItem1.setDinner(dinner);
        dinnerItem1.setItem(food1);
        dinnerItem1.setItemQuantity(3);

        DinnerItem dinnerItem2 = new DinnerItem();
        dinnerItemRepository.save(dinnerItem2);
        dinnerItem2.setDinner(dinner);
        dinnerItem2.setItem(decoration1);
        dinnerItem2.setItemQuantity(3);

        dinner.setDinnerItemList(List.of(dinnerItem1, dinnerItem2));

        orderSheetDto = new OrderSheetDto();
        Map<Long, Integer> itemIdAndQuantity = new HashMap<>();

        itemIdAndQuantity.put(food1.getId(), 1);
        itemIdAndQuantity.put(food2.getId(), 1);

        itemIdAndQuantity.put(decoration1.getId(), 1);
        itemIdAndQuantity.put(decoration2.getId(), 1);

        itemIdAndQuantity.put(tableware1.getId(), 1);
        itemIdAndQuantity.put(tableware2.getId(), 1);

        orderSheetDto.setDinnerId(dinner.getId());
        orderSheetDto.setStyleId(style.getId());

        orderSheetDto.setItemIdAndQuantity(itemIdAndQuantity);

        orderDto = new OrderDto();
        orderDto.setAddress(client1.getAddress());
        orderDto.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        orderDto.setOrderSheetDtoList(List.of(orderSheetDto));
        orderDto.setOrderStatus(OrderStatus.ORDERED);

        clientOrder = (ClientOrder) orderRepository.findById(orderService.makeClientOrder(client1, orderDto)).get();
        guestOrder = (GuestOrder) orderRepository.findById(orderService.makeGuestOrder(guest, orderDto)).get();
    }


}