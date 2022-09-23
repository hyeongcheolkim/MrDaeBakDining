package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.client.service.ClientService;
import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.decoration.service.DecorationService;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.service.RiderService;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.service.TablewareService;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    FoodService foodService;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    IngredientService ingredientService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DecorationService decorationService;
    @Autowired
    TablewareService tablewareService;
    @Autowired
    DecorationRepository decorationRepository;
    @Autowired
    TablewareRepository tablewareRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    RiderService riderService;
    @Autowired
    GuestOrderRepository guestOrderRepository;

    Client client1;
    Client client2;

    Food food1;
    Food food2;

    Decoration decoration1;
    Decoration decoration2;

    Tableware tableware1;
    Tableware tableware2;

    Ingredient ingredient1;
    Ingredient ingredient2;
    Ingredient ingredient3;
    Ingredient ingredient4;
    Ingredient ingredient5;

    Recipe recipe1;
    Recipe recipe2;
    Recipe recipe3;
    Recipe recipe4;
    Recipe recipe5;
    Recipe recipe6;

    Rider rider;


    @BeforeEach
    void init() {
        client1 = new Client();
        client1.setName("memberA");
        client1.setAddress(new Address("seoul", "mangu", "12345"));
        client1.setCardNumber("1234123412341234");
        client1.setLoginId("memberAId");
        client1.setPassword("meberApassword");
        client1.setEnable(true);
        client1.setClientGrade(ClientGrade.DIAMOND);
        clientService.sign(client1);

        client2 = new Client();
        client2.setName("memberB");
        client2.setAddress(new Address("suncheon", "shindae", "33321"));
        client2.setCardNumber("1111222233334444");
        client2.setLoginId("memberBId");
        client2.setPassword("meberBpassword");
        client2.setEnable(true);
        client2.setClientGrade(ClientGrade.BRONZE);
        clientService.sign(client2);

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

        recipe1 = recipeService.makeRecipe(food1.getId(), ingredient1.getId(), 1).get();
        recipe2 = recipeService.makeRecipe(food1.getId(), ingredient2.getId(), 1).get();
        recipe3 = recipeService.makeRecipe(food1.getId(), ingredient3.getId(), 2).get();
        recipe4 = recipeService.makeRecipe(food2.getId(), ingredient4.getId(), 1).get();
        recipe5 = recipeService.makeRecipe(food2.getId(), ingredient5.getId(), 1).get();
        recipe6 = recipeService.makeRecipe(food2.getId(), ingredient2.getId(), 1).get();

        rider = new Rider();
        rider.setName("나배달원");
        rider.setEnable(true);
        rider.setLoginId("deliver");
        rider.setPassword("121212");
        riderService.sign(rider);
    }

    @Test
    void makeOrder() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setAddress(client1.getAddress());
        Map<Long, Integer> foodIdAndQuantity = new HashMap<>();
        Map<Long, Integer> decorationIdAndQuantity = new HashMap<>();
        Map<Long, Integer> tablewareIdAndQuantity = new HashMap<>();

        foodIdAndQuantity.put(food1.getId(), 1);
        foodIdAndQuantity.put(food2.getId(), 1);

        decorationIdAndQuantity.put(decoration1.getId(), 1);
        decorationIdAndQuantity.put(decoration2.getId(), 1);

        tablewareIdAndQuantity.put(tableware1.getId(), 1);
        tablewareIdAndQuantity.put(tableware2.getId(), 1);

        orderDTO.setFoodIdAndQuantity(foodIdAndQuantity);
        orderDTO.setDecorationIdAndQuantity(decorationIdAndQuantity);
        orderDTO.setTablewareIdAndQuantity(tablewareIdAndQuantity);

        Optional<ClientOrder> order = orderService.makeClientOrder(client1, orderDTO);
        assertThat(order).isPresent();
    }

    @Test
    void 데코레이션부족할때주문불가판정() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setAddress(client1.getAddress());
        Map<Long, Integer> foodIdAndQuantity = new HashMap<>();
        Map<Long, Integer> decorationIdAndQuantity = new HashMap<>();
        Map<Long, Integer> tablewareIdAndQuantity = new HashMap<>();

        foodIdAndQuantity.put(food1.getId(), 1);
        foodIdAndQuantity.put(food2.getId(), 1);

        decorationIdAndQuantity.put(decoration1.getId(), 1);
        decorationIdAndQuantity.put(decoration2.getId(), 1);

        tablewareIdAndQuantity.put(tableware1.getId(), 1);
        tablewareIdAndQuantity.put(tableware2.getId(), 1);

        orderDTO.setFoodIdAndQuantity(foodIdAndQuantity);
        orderDTO.setDecorationIdAndQuantity(decorationIdAndQuantity);
        orderDTO.setTablewareIdAndQuantity(tablewareIdAndQuantity);

        Optional<ClientOrder> order = orderService.makeClientOrder(client1, orderDTO);

        decoration1.setStockQuantity(0);
        Boolean orderAble = orderService.isMakeAbleOrder(order.get().getId());
        assertThat(orderAble).isFalse();
    }

    @Test
    void 테이블웨어부족할때주문불가판정() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setAddress(client1.getAddress());
        Map<Long, Integer> foodIdAndQuantity = new HashMap<>();
        Map<Long, Integer> decorationIdAndQuantity = new HashMap<>();
        Map<Long, Integer> tablewareIdAndQuantity = new HashMap<>();

        foodIdAndQuantity.put(food1.getId(), 1);
        foodIdAndQuantity.put(food2.getId(), 1);

        decorationIdAndQuantity.put(decoration1.getId(), 1);
        decorationIdAndQuantity.put(decoration2.getId(), 1);

        tablewareIdAndQuantity.put(tableware1.getId(), 1);
        tablewareIdAndQuantity.put(tableware2.getId(), 1);

        orderDTO.setFoodIdAndQuantity(foodIdAndQuantity);
        orderDTO.setDecorationIdAndQuantity(decorationIdAndQuantity);
        orderDTO.setTablewareIdAndQuantity(tablewareIdAndQuantity);

        Optional<ClientOrder> order = orderService.makeClientOrder(client1, orderDTO);

        tableware1.setStockQuantity(0);
        Boolean orderAble = orderService.isMakeAbleOrder(order.get().getId());
        assertThat(orderAble).isFalse();
    }

    @Test
    void 음식재료부족할때주문불가판정() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setAddress(client1.getAddress());
        Map<Long, Integer> foodIdAndQuantity = new HashMap<>();
        Map<Long, Integer> decorationIdAndQuantity = new HashMap<>();
        Map<Long, Integer> tablewareIdAndQuantity = new HashMap<>();

        foodIdAndQuantity.put(food1.getId(), 1);
        foodIdAndQuantity.put(food2.getId(), 1);

        decorationIdAndQuantity.put(decoration1.getId(), 1);
        decorationIdAndQuantity.put(decoration2.getId(), 1);

        tablewareIdAndQuantity.put(tableware1.getId(), 1);
        tablewareIdAndQuantity.put(tableware2.getId(), 1);

        orderDTO.setFoodIdAndQuantity(foodIdAndQuantity);
        orderDTO.setDecorationIdAndQuantity(decorationIdAndQuantity);
        orderDTO.setTablewareIdAndQuantity(tablewareIdAndQuantity);

        Optional<ClientOrder> order = orderService.makeClientOrder(client1, orderDTO);

        ingredientService.minusStockQuantity(ingredient1.getId(), ingredient1.getStockQuantity());
        Boolean orderAble = orderService.isMakeAbleOrder(order.get().getId());
        assertThat(orderAble).isFalse();
    }


    @Test
    void 배달원정상할당(){
        OrderDto orderDTO = new OrderDto();
        orderDTO.setAddress(client1.getAddress());
        Map<Long, Integer> foodIdAndQuantity = new HashMap<>();
        Map<Long, Integer> decorationIdAndQuantity = new HashMap<>();
        Map<Long, Integer> tablewareIdAndQuantity = new HashMap<>();

        foodIdAndQuantity.put(food1.getId(), 1);
        foodIdAndQuantity.put(food2.getId(), 1);

        decorationIdAndQuantity.put(decoration1.getId(), 1);
        decorationIdAndQuantity.put(decoration2.getId(), 1);

        tablewareIdAndQuantity.put(tableware1.getId(), 1);
        tablewareIdAndQuantity.put(tableware2.getId(), 1);

        orderDTO.setFoodIdAndQuantity(foodIdAndQuantity);
        orderDTO.setDecorationIdAndQuantity(decorationIdAndQuantity);
        orderDTO.setTablewareIdAndQuantity(tablewareIdAndQuantity);

        Optional<ClientOrder> order = orderService.makeClientOrder(client1, orderDTO);
        order.get().setRider(rider);
        assertThat(order.get().getRider().getId()).isEqualTo(rider.getId());
    }

    @Test
    void GUEST로정상주문(){
        OrderDto orderDTO = new OrderDto();
        orderDTO.setAddress(client1.getAddress());
        Map<Long, Integer> foodIdAndQuantity = new HashMap<>();
        Map<Long, Integer> decorationIdAndQuantity = new HashMap<>();
        Map<Long, Integer> tablewareIdAndQuantity = new HashMap<>();

        foodIdAndQuantity.put(food1.getId(), 1);
        foodIdAndQuantity.put(food2.getId(), 1);

        decorationIdAndQuantity.put(decoration1.getId(), 1);
        decorationIdAndQuantity.put(decoration2.getId(), 1);

        tablewareIdAndQuantity.put(tableware1.getId(), 1);
        tablewareIdAndQuantity.put(tableware2.getId(), 1);

        orderDTO.setFoodIdAndQuantity(foodIdAndQuantity);
        orderDTO.setDecorationIdAndQuantity(decorationIdAndQuantity);
        orderDTO.setTablewareIdAndQuantity(tablewareIdAndQuantity);

        Optional<GuestOrder> guestOrder = orderService.makeGuestOrder(orderDTO);
        long count = guestOrderRepository.count();
        assertThat(count).isEqualTo(1);
    }
}