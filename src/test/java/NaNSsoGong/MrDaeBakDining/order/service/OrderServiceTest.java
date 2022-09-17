package NaNSsoGong.MrDaeBakDining.order.service;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.item.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.item.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.item.decoration.service.DecorationService;
import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.item.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.item.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.item.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.item.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.item.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.item.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.item.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.item.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.item.recipe.service.RecipeService;
import NaNSsoGong.MrDaeBakDining.item.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.item.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.item.tableware.service.TablewareService;
import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.member.domain.MemberGrade;
import NaNSsoGong.MrDaeBakDining.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.order.dto.OrderDTO;
import NaNSsoGong.MrDaeBakDining.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    Member member1;
    Member member2;

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


    @BeforeEach
    void init() {
        member1 = new Member();
        member1.setName("memberA");
        member1.setAddress(new Address("seoul", "mangu", "12345"));
        member1.setCardNumber("1234123412341234");
        member1.setLoginId("memberAId");
        member1.setPassword("meberApassword");
        member1.setEnable(true);
        member1.setMemberGrade(MemberGrade.DIAMOND);

        member2 = new Member();
        member2.setName("memberB");
        member2.setAddress(new Address("suncheon", "shindae", "33321"));
        member2.setCardNumber("1111222233334444");
        member2.setLoginId("memberBId");
        member2.setPassword("meberBpassword");
        member2.setEnable(true);
        member2.setMemberGrade(MemberGrade.BRONZE);

        decoration1 = new Decoration();
        decoration1.setName("하트장식");
        decoration1.setStockQuantity(2);
        decorationService.register(decoration1);

        decoration2 = new Decoration();
        decoration2.setName("달빛장식");
        decoration2.setStockQuantity(1);
        decorationService.register(decoration2);

        tableware1 = new Tableware();
        tableware1.setName("와인잔");
        tableware1.setStockQuantity(4);
        tablewareService.register(tableware1);

        tableware2 = new Tableware();
        tableware2.setName("냅킨");
        tableware2.setStockQuantity(2);
        tablewareService.register(tableware2);

        food1 = new Food();
        food1.setName("스테이크");
        food1.setSellPrice(30000);
        food1.setFoodCategory(FoodCategory.MEAT);
        foodService.register(food1);

        food2 = new Food();
        food2.setName("치킨");
        food2.setSellPrice(25000);
        food2.setFoodCategory(FoodCategory.MEAT);
        foodService.register(food2);

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
        ingredientService.register(ingredient1);
        ingredientService.register(ingredient2);
        ingredientService.register(ingredient3);
        ingredientService.register(ingredient4);
        ingredientService.register(ingredient5);

        recipe1 = recipeService.makeRecipe(food1.getId(), ingredient1.getId(), 1).get();
        recipe2 = recipeService.makeRecipe(food1.getId(), ingredient2.getId(), 1).get();
        recipe3 = recipeService.makeRecipe(food1.getId(), ingredient3.getId(), 2).get();
        recipe4 = recipeService.makeRecipe(food2.getId(), ingredient4.getId(), 1).get();
        recipe5 = recipeService.makeRecipe(food2.getId(), ingredient5.getId(), 1).get();
        recipe6 = recipeService.makeRecipe(food2.getId(), ingredient2.getId(), 1).get();
    }

    @Test
    void makeOrder() {
        OrderDTO orderDTO = new OrderDTO();
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

        Optional<Order> order = orderService.makeOrder(member1, orderDTO);
        assertThat(order).isPresent();
    }

    @Test
    void 데코레이션부족할때주문불가판정() {
        OrderDTO orderDTO = new OrderDTO();
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

        Optional<Order> order = orderService.makeOrder(member1, orderDTO);

        decoration1.setStockQuantity(0);
        Boolean orderAble = orderService.isOderAble(order.get().getId());
        assertThat(orderAble).isFalse();
    }

    @Test
    void 테이블웨어부족할때주문불가판정() {
        OrderDTO orderDTO = new OrderDTO();
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

        Optional<Order> order = orderService.makeOrder(member1, orderDTO);

        tableware1.setStockQuantity(0);
        Boolean orderAble = orderService.isOderAble(order.get().getId());
        assertThat(orderAble).isFalse();
    }

    @Test
    void 음식재료부족할때주문불가판정() {
        OrderDTO orderDTO = new OrderDTO();
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

        Optional<Order> order = orderService.makeOrder(member1, orderDTO);

        ingredientService.minusStockQuantity(ingredient1.getId(), ingredient1.getStockQuantity());
        Boolean orderAble = orderService.isOderAble(order.get().getId());
        assertThat(orderAble).isFalse();
    }
}