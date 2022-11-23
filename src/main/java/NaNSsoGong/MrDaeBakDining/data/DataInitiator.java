package NaNSsoGong.MrDaeBakDining.data;

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
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
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
import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
public class DataInitiator {
    private final FoodRepository foodRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final TablewareRepository tablewareRepository;
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final GuestRepository guestRepository;
    private final ChefRepository chefRepository;
    private final ClientRepository clientRepository;
    private final RiderRepository riderRepository;
    private final Random random = new Random();

    Client client1, client2, client3, client4, client5, client6, client7, client8;
    Rider rider1, rider2, rider3, rider4, rider5;
    Chef chef1, chef2, chef3, chef4, chef5;

    List<Rider> riderList = new ArrayList<>();
    List<Client> clientList = new ArrayList<>();

    List<Dinner> dinnerList = new ArrayList<>();
    List<Food> foodList = new ArrayList<>();
    List<Ingredient> ingredientList = new ArrayList<>();
    List<Recipe> recipeList = new ArrayList<>();
    List<Tableware> tablewareList = new ArrayList<>();
    List<Style> styleList = new ArrayList<>();
    List<StyleTableware> styleTablewareList = new ArrayList<>();

    public void dataInit() {
        clientInit();
        riderInit();
        chefInit();

        foodInit();
        ingredientInit();
        recipeInit();

        tablewareInit();
        styleInit();
        styleTablewareInit();

        dinnerInit();
        dinnerFoodInit();

        for (int i = 0; i < 15; ++i)
            for (var client : clientList)
                clientOrderScenario(client);
        for (int i = 0; i < 50; ++i)
            guestOrderScenario();
    }

    public void clientInit() {
        //개인정보수집에 동의
        client1 = new Client();
        clientRepository.save(client1);
        client1.setClientGrade(ClientGrade.BRONZE);
        client1.setAddress(new Address("서울특별시", "시립대로17", "302호"));
        client1.setCardNumber("4212331286423323");
        client1.setName("나고객");
        client1.setEnable(true);
        client1.setLoginId("client");
        client1.setPassword("test");
        client1.setPersonalInformationCollectionAgreement(true);

        client2 = new Client();
        clientRepository.save(client2);
        client2.setClientGrade(ClientGrade.BRONZE);
        client2.setAddress(new Address("순천시", "탐사로15", "102호"));
        client2.setCardNumber("4212336286413383");
        client2.setName("강원기");
        client2.setEnable(true);
        client2.setLoginId("client2753");
        client2.setPassword("password1234");
        client2.setPersonalInformationCollectionAgreement(true);

        client3 = new Client();
        clientRepository.save(client3);
        client3.setClientGrade(ClientGrade.BRONZE);
        client3.setAddress(new Address("포항시", "중앙로가길11", "502호"));
        client3.setCardNumber("1213336289421320");
        client3.setName("이현주");
        client3.setEnable(true);
        client3.setLoginId("client3753");
        client3.setPassword("password1234");
        client3.setPersonalInformationCollectionAgreement(true);

        client4 = new Client();
        clientRepository.save(client4);
        client4.setClientGrade(ClientGrade.BRONZE);
        client4.setAddress(new Address("부산시", "광안리로가길", "5021호"));
        client4.setCardNumber("4212381236420320");
        client4.setName("김채원");
        client4.setEnable(true);
        client4.setLoginId("client4753");
        client4.setPassword("password1234");
        client4.setPersonalInformationCollectionAgreement(true);

        //개인정보수집에 동의하지 않음, 카드번호와 주소 null
        client5 = new Client();
        clientRepository.save(client5);
        client5.setClientGrade(ClientGrade.BRONZE);
        client5.setName("김모자");
        client5.setEnable(true);
        client5.setLoginId("client5512");
        client5.setPassword("password4234");
        client5.setPersonalInformationCollectionAgreement(false);

        client6 = new Client();
        clientRepository.save(client6);
        client6.setClientGrade(ClientGrade.BRONZE);
        client6.setName("이기상");
        client6.setEnable(true);
        client6.setLoginId("client6512");
        client6.setPassword("password4234");
        client6.setPersonalInformationCollectionAgreement(false);

        client7 = new Client();
        clientRepository.save(client7);
        client7.setClientGrade(ClientGrade.BRONZE);
        client7.setName("현우진");
        client7.setEnable(true);
        client7.setLoginId("client7512");
        client7.setPassword("password4234");
        client7.setPersonalInformationCollectionAgreement(false);

        client8 = new Client();
        clientRepository.save(client8);
        client8.setClientGrade(ClientGrade.BRONZE);
        client8.setName("한석원");
        client8.setEnable(true);
        client8.setLoginId("client8512");
        client8.setPassword("password4234");
        client8.setPersonalInformationCollectionAgreement(false);

        clientList.addAll(List.of(client1, client2, client3, client4, client5, client6, client7, client8));
    }

    public void riderInit() {
        rider1 = new Rider();
        riderRepository.save(rider1);
        rider1.setPassword("test");
        rider1.setEnable(true);
        rider1.setName("나배달");
        rider1.setLoginId("rider");

        rider2 = new Rider();
        riderRepository.save(rider2);
        rider2.setPassword("password1132");
        rider2.setEnable(true);
        rider2.setName("김배달");
        rider2.setLoginId("rider2523132");

        rider3 = new Rider();
        riderRepository.save(rider3);
        rider3.setPassword("password1132");
        rider3.setEnable(true);
        rider3.setName("이배달");
        rider3.setLoginId("rider3523132");

        rider4 = new Rider();
        riderRepository.save(rider4);
        rider4.setPassword("password1132");
        rider4.setEnable(true);
        rider4.setName("류배달");
        rider4.setLoginId("rider4523132");

        rider5 = new Rider();
        riderRepository.save(rider5);
        rider5.setPassword("password1132");
        rider5.setEnable(true);
        rider5.setName("나배달");
        rider5.setLoginId("rider5523132");

        riderList.addAll(List.of(rider1, rider2, rider3, rider4, rider5));
    }

    public void chefInit() {
        chef1 = new Chef();
        chefRepository.save(chef1);
        chef1.setPassword("test");
        chef1.setName("박쉐프");
        chef1.setEnable(true);
        chef1.setLoginId("chef");

        chef2 = new Chef();
        chefRepository.save(chef2);
        chef2.setPassword("password9932");
        chef2.setName("나쉐프");
        chef2.setEnable(true);
        chef2.setLoginId("chefloginid223");

        chef3 = new Chef();
        chefRepository.save(chef3);
        chef3.setPassword("password9932");
        chef3.setName("김쉐프");
        chef3.setEnable(true);
        chef3.setLoginId("chefloginid323");

        chef4 = new Chef();
        chefRepository.save(chef4);
        chef4.setPassword("password9932");
        chef4.setName("이쉐프");
        chef4.setEnable(true);
        chef4.setLoginId("chefloginid423");

        chef5 = new Chef();
        chefRepository.save(chef5);
        chef5.setPassword("password9932");
        chef5.setName("팽쉐프");
        chef5.setEnable(true);
        chef5.setLoginId("chefloginid523");
    }

    public void foodInit() {
        for (int i = 0; i < 30; ++i) {
            Food food = new Food();
            food.setOrderable(true);
            food.setEnable(true);
            foodList.add(food);
        }
        foodList.get(0).setFoodCategory(FoodCategory.MEAT);
        foodList.get(0).setName("안심스테이크");
        foodList.get(0).setSellPrice(30000);

        foodList.get(1).setFoodCategory(FoodCategory.MEAT);
        foodList.get(1).setName("치킨");
        foodList.get(1).setSellPrice(18000);

        foodList.get(2).setFoodCategory(FoodCategory.MEAT);
        foodList.get(2).setName("포크커틀릿");
        foodList.get(2).setSellPrice(15000);

        foodList.get(3).setFoodCategory(FoodCategory.MEAT);
        foodList.get(3).setName("연어회");
        foodList.get(3).setSellPrice(17000);

        foodList.get(4).setFoodCategory(FoodCategory.MEAT);
        foodList.get(4).setName("베이컨");
        foodList.get(4).setSellPrice(12000);

        foodList.get(5).setFoodCategory(FoodCategory.MEAT);
        foodList.get(5).setName("등심스테이크");
        foodList.get(5).setSellPrice(25000);

        foodList.get(6).setFoodCategory(FoodCategory.BREAD);
        foodList.get(6).setName("마늘빵");
        foodList.get(6).setSellPrice(3000);

        foodList.get(7).setFoodCategory(FoodCategory.BREAD);
        foodList.get(7).setName("식빵");
        foodList.get(7).setSellPrice(2000);

        foodList.get(8).setFoodCategory(FoodCategory.BREAD);
        foodList.get(8).setName("바게트");
        foodList.get(8).setSellPrice(5000);

        foodList.get(9).setFoodCategory(FoodCategory.BREAD);
        foodList.get(9).setName("카스테라");
        foodList.get(9).setSellPrice(4000);

        foodList.get(10).setFoodCategory(FoodCategory.COFFEE);
        foodList.get(10).setName("아메리카노");
        foodList.get(10).setSellPrice(6000);

        foodList.get(11).setFoodCategory(FoodCategory.COFFEE);
        foodList.get(11).setName("카라멜마끼아또");
        foodList.get(11).setSellPrice(7000);

        foodList.get(12).setFoodCategory(FoodCategory.COFFEE);
        foodList.get(12).setName("에스프레소");
        foodList.get(12).setSellPrice(5000);

        foodList.get(13).setFoodCategory(FoodCategory.ALCOHOL);
        foodList.get(13).setName("화이트와인");
        foodList.get(13).setSellPrice(25000);

        foodList.get(14).setFoodCategory(FoodCategory.ALCOHOL);
        foodList.get(14).setName("레드와인");
        foodList.get(14).setSellPrice(35000);

        foodList.get(15).setFoodCategory(FoodCategory.ALCOHOL);
        foodList.get(15).setName("샴페인");
        foodList.get(15).setSellPrice(45000);

        foodList.get(16).setFoodCategory(FoodCategory.ALCOHOL);
        foodList.get(16).setName("코냑");
        foodList.get(16).setSellPrice(55000);

        foodList.get(17).setFoodCategory(FoodCategory.ALCOHOL);
        foodList.get(17).setName("위스키");
        foodList.get(17).setSellPrice(55000);

        foodList.get(18).setFoodCategory(FoodCategory.SALAD);
        foodList.get(18).setName("그린샐러드");
        foodList.get(18).setSellPrice(10000);

        foodList.get(19).setFoodCategory(FoodCategory.SALAD);
        foodList.get(19).setName("리코타치즈샐러드");
        foodList.get(19).setSellPrice(15000);

        foodList.get(20).setFoodCategory(FoodCategory.SALAD);
        foodList.get(20).setName("단호박샐러드");
        foodList.get(20).setSellPrice(10000);

        foodList.get(21).setFoodCategory(FoodCategory.SALAD);
        foodList.get(21).setName("오리엔탈샐러드");
        foodList.get(21).setSellPrice(12000);

        foodList.get(22).setFoodCategory(FoodCategory.SIDE);
        foodList.get(22).setName("에그스크램블");
        foodList.get(22).setSellPrice(4000);

        foodList.get(23).setFoodCategory(FoodCategory.SIDE);
        foodList.get(23).setName("치즈");
        foodList.get(23).setSellPrice(3000);

        foodList.get(24).setFoodCategory(FoodCategory.SIDE);
        foodList.get(24).setName("감자튀김");
        foodList.get(24).setSellPrice(6000);

        foodList.get(25).setFoodCategory(FoodCategory.SIDE);
        foodList.get(25).setName("나쵸");
        foodList.get(25).setSellPrice(8000);

        foodList.get(26).setFoodCategory(FoodCategory.SIDE);
        foodList.get(26).setName("초콜릿");
        foodList.get(26).setSellPrice(7000);

        foodList.get(27).setFoodCategory(FoodCategory.COFFEE);
        foodList.get(27).setName("커피한포트");
        foodList.get(27).setSellPrice(10000);

        foodList.get(28).setFoodCategory(FoodCategory.COFFEE);
        foodList.get(28).setName("커피한잔");
        foodList.get(28).setSellPrice(3000);

        foodList.get(29).setFoodCategory(FoodCategory.ALCOHOL);
        foodList.get(29).setName("와인한잔");
        foodList.get(29).setSellPrice(4000);

        foodRepository.saveAll(foodList);
    }

    public void ingredientInit() {
        for (int i = 0; i < 55; ++i) {
            Ingredient ingredient = new Ingredient();
            ingredient.setEnable(true);
            ingredient.setStockQuantity(5);
            ingredientList.add(ingredient);
        }
        ingredientList.get(0).setName("후추");
        ingredientList.get(1).setName("소금");
        ingredientList.get(2).setName("올리브유");
        ingredientList.get(3).setName("설탕");
        ingredientList.get(4).setName("단호박");
        ingredientList.get(5).setName("양배추");
        ingredientList.get(6).setName("치즈");
        ingredientList.get(7).setName("술");
        ingredientList.get(8).setName("샴페인");
        ingredientList.get(9).setName("닭고기");
        ingredientList.get(10).setName("소고기");
        ingredientList.get(11).setName("돼지고기");
        ingredientList.get(12).setName("베이컨");
        ingredientList.get(13).setName("달걀");
        ingredientList.get(14).setName("허브");
        ingredientList.get(15).setName("월계수잎");
        ingredientList.get(16).setName("파슬리");
        ingredientList.get(17).setName("밀가루");
        ingredientList.get(18).setName("쌀");
        ingredientList.get(19).setName("감자");
        ingredientList.get(20).setName("고구마");
        ingredientList.get(21).setName("초콜릿");
        ingredientList.get(22).setName("빵");
        ingredientList.get(23).setName("커피콩");
        ingredientList.get(24).setName("생크림");
        ingredientList.get(25).setName("고추가루");
        ingredientList.get(26).setName("핫소스");
        ingredientList.get(27).setName("간장");
        ingredientList.get(28).setName("마늘");
        ingredientList.get(29).setName("와사비");
        ingredientList.get(30).setName("초고추장");
        ingredientList.get(31).setName("된장");
        ingredientList.get(32).setName("연어");
        ingredientList.get(33).setName("맛술");
        ingredientList.get(34).setName("식초");
        ingredientList.get(35).setName("치킨스톡");
        ingredientList.get(36).setName("청경채");
        ingredientList.get(37).setName("양파");
        ingredientList.get(38).setName("토마토");
        ingredientList.get(39).setName("버터");
        ingredientList.get(40).setName("생강");
        ingredientList.get(41).setName("강황");
        ingredientList.get(42).setName("바질");
        ingredientList.get(43).setName("고수");
        ingredientList.get(44).setName("민트");
        ingredientList.get(45).setName("계피");
        ingredientList.get(46).setName("바질");
        ingredientList.get(47).setName("파");
        ingredientList.get(48).setName("당근");
        ingredientList.get(49).setName("땅콩");
        ingredientList.get(50).setName("식용유");
        ingredientList.get(51).setName("파스타면");
        ingredientList.get(52).setName("케찹");
        ingredientList.get(53).setName("마요네즈");
        ingredientList.get(54).setName("나쵸");

        ingredientRepository.saveAll(ingredientList);
    }

    public void recipeInit() {
        for (int i = 0; i < 62; ++i) {
            Recipe recipe = new Recipe();
            recipeList.add(recipe);
        }

        recipeList.get(0).setFood(foodList.get(0));
        recipeList.get(0).setIngredient(ingredientList.get(0));
        recipeList.get(0).setIngredientQuantity(1);

        recipeList.get(1).setFood(foodList.get(0));
        recipeList.get(1).setIngredient(ingredientList.get(1));
        recipeList.get(1).setIngredientQuantity(2);

        recipeList.get(2).setFood(foodList.get(0));
        recipeList.get(2).setIngredient(ingredientList.get(2));
        recipeList.get(2).setIngredientQuantity(1);

        recipeList.get(3).setFood(foodList.get(0));
        recipeList.get(3).setIngredient(ingredientList.get(10));
        recipeList.get(3).setIngredientQuantity(2);

        recipeList.get(4).setFood(foodList.get(0));
        recipeList.get(4).setIngredient(ingredientList.get(14));
        recipeList.get(4).setIngredientQuantity(1);

        recipeList.get(5).setFood(foodList.get(0));
        recipeList.get(5).setIngredient(ingredientList.get(15));
        recipeList.get(5).setIngredientQuantity(1);

        recipeList.get(6).setFood(foodList.get(1));
        recipeList.get(6).setIngredient(ingredientList.get(9));
        recipeList.get(6).setIngredientQuantity(2);

        recipeList.get(7).setFood(foodList.get(1));
        recipeList.get(7).setIngredient(ingredientList.get(0));
        recipeList.get(7).setIngredientQuantity(2);

        recipeList.get(8).setFood(foodList.get(1));
        recipeList.get(8).setIngredient(ingredientList.get(1));
        recipeList.get(8).setIngredientQuantity(3);

        recipeList.get(9).setFood(foodList.get(1));
        recipeList.get(9).setIngredient(ingredientList.get(17));
        recipeList.get(9).setIngredientQuantity(2);

        recipeList.get(10).setFood(foodList.get(2));
        recipeList.get(10).setIngredient(ingredientList.get(11));
        recipeList.get(10).setIngredientQuantity(3);

        recipeList.get(11).setFood(foodList.get(2));
        recipeList.get(11).setIngredient(ingredientList.get(3));
        recipeList.get(11).setIngredientQuantity(1);

        recipeList.get(12).setFood(foodList.get(2));
        recipeList.get(12).setIngredient(ingredientList.get(17));
        recipeList.get(12).setIngredientQuantity(4);

        recipeList.get(13).setFood(foodList.get(2));
        recipeList.get(13).setIngredient(ingredientList.get(13));
        recipeList.get(13).setIngredientQuantity(2);

        recipeList.get(14).setFood(foodList.get(2));
        recipeList.get(14).setIngredient(ingredientList.get(37));
        recipeList.get(14).setIngredientQuantity(1);

        recipeList.get(15).setFood(foodList.get(3));
        recipeList.get(15).setIngredient(ingredientList.get(32));
        recipeList.get(15).setIngredientQuantity(2);

        recipeList.get(16).setFood(foodList.get(3));
        recipeList.get(16).setIngredient(ingredientList.get(29));
        recipeList.get(16).setIngredientQuantity(1);

        recipeList.get(17).setFood(foodList.get(3));
        recipeList.get(17).setIngredient(ingredientList.get(27));
        recipeList.get(17).setIngredientQuantity(1);

        recipeList.get(18).setFood(foodList.get(4));
        recipeList.get(18).setIngredient(ingredientList.get(12));
        recipeList.get(18).setIngredientQuantity(3);

        recipeList.get(19).setFood(foodList.get(4));
        recipeList.get(19).setIngredient(ingredientList.get(1));
        recipeList.get(19).setIngredientQuantity(1);

        recipeList.get(20).setFood(foodList.get(5));
        recipeList.get(20).setIngredient(ingredientList.get(10));
        recipeList.get(20).setIngredientQuantity(2);

        recipeList.get(21).setFood(foodList.get(5));
        recipeList.get(21).setIngredient(ingredientList.get(0));
        recipeList.get(21).setIngredientQuantity(2);

        recipeList.get(22).setFood(foodList.get(5));
        recipeList.get(22).setIngredient(ingredientList.get(1));
        recipeList.get(22).setIngredientQuantity(1);

        recipeList.get(23).setFood(foodList.get(5));
        recipeList.get(23).setIngredient(ingredientList.get(2));
        recipeList.get(23).setIngredientQuantity(1);

        recipeList.get(24).setFood(foodList.get(5));
        recipeList.get(24).setIngredient(ingredientList.get(14));
        recipeList.get(24).setIngredientQuantity(1);

        recipeList.get(25).setFood(foodList.get(6));
        recipeList.get(25).setIngredient(ingredientList.get(28));
        recipeList.get(25).setIngredientQuantity(3);

        recipeList.get(26).setFood(foodList.get(6));
        recipeList.get(26).setIngredient(ingredientList.get(22));
        recipeList.get(26).setIngredientQuantity(3);

        recipeList.get(27).setFood(foodList.get(6));
        recipeList.get(27).setIngredient(ingredientList.get(39));
        recipeList.get(27).setIngredientQuantity(4);

        recipeList.get(28).setFood(foodList.get(7));
        recipeList.get(28).setIngredient(ingredientList.get(22));
        recipeList.get(28).setIngredientQuantity(3);

        recipeList.get(29).setFood(foodList.get(8));
        recipeList.get(29).setIngredient(ingredientList.get(22));
        recipeList.get(29).setIngredientQuantity(2);

        recipeList.get(30).setFood(foodList.get(9));
        recipeList.get(30).setIngredient(ingredientList.get(22));
        recipeList.get(30).setIngredientQuantity(1);

        recipeList.get(31).setFood(foodList.get(9));
        recipeList.get(31).setIngredient(ingredientList.get(24));
        recipeList.get(31).setIngredientQuantity(1);

        recipeList.get(32).setFood(foodList.get(9));
        recipeList.get(32).setIngredient(ingredientList.get(13));
        recipeList.get(32).setIngredientQuantity(2);

        recipeList.get(33).setFood(foodList.get(9));
        recipeList.get(33).setIngredient(ingredientList.get(17));
        recipeList.get(33).setIngredientQuantity(3);

        recipeList.get(34).setFood(foodList.get(10));
        recipeList.get(34).setIngredient(ingredientList.get(23));
        recipeList.get(34).setIngredientQuantity(2);

        recipeList.get(35).setFood(foodList.get(11));
        recipeList.get(35).setIngredient(ingredientList.get(23));
        recipeList.get(35).setIngredientQuantity(1);

        recipeList.get(36).setFood(foodList.get(11));
        recipeList.get(36).setIngredient(ingredientList.get(3));
        recipeList.get(36).setIngredientQuantity(3);

        recipeList.get(37).setFood(foodList.get(12));
        recipeList.get(37).setIngredient(ingredientList.get(23));
        recipeList.get(37).setIngredientQuantity(4);

        recipeList.get(38).setFood(foodList.get(13));
        recipeList.get(38).setIngredient(ingredientList.get(7));
        recipeList.get(38).setIngredientQuantity(1);

        recipeList.get(39).setFood(foodList.get(14));
        recipeList.get(39).setIngredient(ingredientList.get(7));
        recipeList.get(39).setIngredientQuantity(1);

        recipeList.get(40).setFood(foodList.get(15));
        recipeList.get(40).setIngredient(ingredientList.get(7));
        recipeList.get(40).setIngredientQuantity(1);

        recipeList.get(41).setFood(foodList.get(16));
        recipeList.get(41).setIngredient(ingredientList.get(7));
        recipeList.get(41).setIngredientQuantity(1);

        recipeList.get(42).setFood(foodList.get(17));
        recipeList.get(42).setIngredient(ingredientList.get(7));
        recipeList.get(42).setIngredientQuantity(1);

        recipeList.get(43).setFood(foodList.get(18));
        recipeList.get(43).setIngredient(ingredientList.get(5));
        recipeList.get(43).setIngredientQuantity(3);

        recipeList.get(44).setFood(foodList.get(18));
        recipeList.get(44).setIngredient(ingredientList.get(46));
        recipeList.get(44).setIngredientQuantity(1);

        recipeList.get(45).setFood(foodList.get(18));
        recipeList.get(45).setIngredient(ingredientList.get(48));
        recipeList.get(45).setIngredientQuantity(3);

        recipeList.get(46).setFood(foodList.get(19));
        recipeList.get(46).setIngredient(ingredientList.get(48));
        recipeList.get(46).setIngredientQuantity(1);

        recipeList.get(47).setFood(foodList.get(19));
        recipeList.get(47).setIngredient(ingredientList.get(6));
        recipeList.get(47).setIngredientQuantity(3);

        recipeList.get(48).setFood(foodList.get(19));
        recipeList.get(48).setIngredient(ingredientList.get(5));
        recipeList.get(48).setIngredientQuantity(3);

        recipeList.get(49).setFood(foodList.get(20));
        recipeList.get(49).setIngredient(ingredientList.get(5));
        recipeList.get(49).setIngredientQuantity(3);

        recipeList.get(50).setFood(foodList.get(20));
        recipeList.get(50).setIngredient(ingredientList.get(4));
        recipeList.get(50).setIngredientQuantity(2);

        recipeList.get(51).setFood(foodList.get(21));
        recipeList.get(51).setIngredient(ingredientList.get(5));
        recipeList.get(51).setIngredientQuantity(3);

        recipeList.get(52).setFood(foodList.get(21));
        recipeList.get(52).setIngredient(ingredientList.get(27));
        recipeList.get(52).setIngredientQuantity(2);

        recipeList.get(53).setFood(foodList.get(22));
        recipeList.get(53).setIngredient(ingredientList.get(13));
        recipeList.get(53).setIngredientQuantity(2);

        recipeList.get(54).setFood(foodList.get(22));
        recipeList.get(54).setIngredient(ingredientList.get(1));
        recipeList.get(54).setIngredientQuantity(1);

        recipeList.get(55).setFood(foodList.get(23));
        recipeList.get(55).setIngredient(ingredientList.get(6));
        recipeList.get(55).setIngredientQuantity(3);

        recipeList.get(56).setFood(foodList.get(24));
        recipeList.get(56).setIngredient(ingredientList.get(19));
        recipeList.get(56).setIngredientQuantity(3);

        recipeList.get(57).setFood(foodList.get(24));
        recipeList.get(57).setIngredient(ingredientList.get(1));
        recipeList.get(57).setIngredientQuantity(1);

        recipeList.get(58).setFood(foodList.get(24));
        recipeList.get(58).setIngredient(ingredientList.get(52));
        recipeList.get(58).setIngredientQuantity(1);

        recipeList.get(59).setFood(foodList.get(25));
        recipeList.get(59).setIngredient(ingredientList.get(52));
        recipeList.get(59).setIngredientQuantity(1);

        recipeList.get(60).setFood(foodList.get(25));
        recipeList.get(60).setIngredient(ingredientList.get(53));
        recipeList.get(60).setIngredientQuantity(1);

        recipeList.get(61).setFood(foodList.get(25));
        recipeList.get(61).setIngredient(ingredientList.get(54));
        recipeList.get(61).setIngredientQuantity(3);

        recipeRepository.saveAll(recipeList);
    }

    public void tablewareInit() {
        for (int i = 0; i < 33; ++i) {
            Tableware tableware = new Tableware();
            tableware.setEnable(true);
            tablewareList.add(tableware);
        }
        tablewareList.get(0).setName("하트모양장식");
        tablewareList.get(1).setName("큐피드접시");
        tablewareList.get(2).setName("하트컵");
        tablewareList.get(3).setName("플라스틱쟁반");
        tablewareList.get(4).setName("유리쟁반");
        tablewareList.get(5).setName("나무쟁반");
        tablewareList.get(6).setName("사기그릇");
        tablewareList.get(7).setName("달모양장식");
        tablewareList.get(8).setName("플라스틱잔");
        tablewareList.get(9).setName("종이그릇");
        tablewareList.get(10).setName("유리컵");
        tablewareList.get(11).setName("강철컵");
        tablewareList.get(12).setName("강철쟁반");
        tablewareList.get(13).setName("강철접시");
        tablewareList.get(14).setName("도자기접시");
        tablewareList.get(15).setName("꽃병");
        tablewareList.get(16).setName("꽃바구니");
        tablewareList.get(17).setName("린넨냅킨");
        tablewareList.get(18).setName("종이냅킨");
        tablewareList.get(19).setName("은쟁반");
        tablewareList.get(20).setName("금쟁반");
        tablewareList.get(21).setName("은그릇");
        tablewareList.get(22).setName("금그릇");
        tablewareList.get(23).setName("냅킨");
        tablewareList.get(24).setName("금컵");
        tablewareList.get(25).setName("천냅킨");
        tablewareList.get(26).setName("실크냅킨");
        tablewareList.get(27).setName("금물병");
        tablewareList.get(28).setName("은물병");
        tablewareList.get(29).setName("도자기컵");
        tablewareList.get(30).setName("흰색면냅킨");
        tablewareList.get(31).setName("유리물병");
        tablewareList.get(32).setName("플라스틱물병");

        tablewareRepository.saveAll(tablewareList);
    }

    public void styleInit() {
        for (int i = 0; i < 3; ++i) {
            Style style = new Style();
            style.setEnable(true);
            style.setOrderable(true);
            styleList.add(style);
        }

        styleList.get(0).setName("심플");
        styleList.get(0).setSellPrice(3000);

        styleList.get(1).setName("그랜드");
        styleList.get(1).setSellPrice(5000);

        styleList.get(2).setName("디럭스");
        styleList.get(2).setSellPrice(7000);

        styleRepository.saveAll(styleList);
    }

    public void styleTablewareInit() {
        for (int i = 0; i < 11; ++i) {
            StyleTableware styleTableware = new StyleTableware();
            styleTablewareList.add(styleTableware);
        }
        styleTablewareList.get(0).setStyle(styleList.get(0));
        styleTablewareList.get(0).setTableware(tablewareList.get(23));
        styleList.get(0).getStyleTablewareList().add(styleTablewareList.get(0));

        styleTablewareList.get(1).setStyle(styleList.get(0));
        styleTablewareList.get(1).setTableware(tablewareList.get(3));
        styleList.get(0).getStyleTablewareList().add(styleTablewareList.get(1));

        styleTablewareList.get(2).setStyle(styleList.get(0));
        styleTablewareList.get(2).setTableware(tablewareList.get(8));
        styleList.get(0).getStyleTablewareList().add(styleTablewareList.get(2));

        styleTablewareList.get(3).setStyle(styleList.get(1));
        styleTablewareList.get(3).setTableware(tablewareList.get(14));
        styleList.get(1).getStyleTablewareList().add(styleTablewareList.get(3));

        styleTablewareList.get(4).setStyle(styleList.get(1));
        styleTablewareList.get(4).setTableware(tablewareList.get(29));
        styleList.get(1).getStyleTablewareList().add(styleTablewareList.get(4));

        styleTablewareList.get(5).setStyle(styleList.get(1));
        styleTablewareList.get(5).setTableware(tablewareList.get(30));
        styleList.get(1).getStyleTablewareList().add(styleTablewareList.get(5));

        styleTablewareList.get(6).setStyle(styleList.get(1));
        styleTablewareList.get(6).setTableware(tablewareList.get(5));
        styleList.get(1).getStyleTablewareList().add(styleTablewareList.get(6));

        styleTablewareList.get(7).setStyle(styleList.get(2));
        styleTablewareList.get(7).setTableware(tablewareList.get(26));
        styleList.get(2).getStyleTablewareList().add(styleTablewareList.get(7));

        styleTablewareList.get(8).setStyle(styleList.get(2));
        styleTablewareList.get(8).setTableware(tablewareList.get(27));
        styleList.get(2).getStyleTablewareList().add(styleTablewareList.get(8));

        styleTablewareList.get(9).setStyle(styleList.get(2));
        styleTablewareList.get(9).setTableware(tablewareList.get(6));
        styleList.get(2).getStyleTablewareList().add(styleTablewareList.get(9));

        styleTablewareList.get(10).setStyle(styleList.get(2));
        styleTablewareList.get(10).setTableware(tablewareList.get(4));
        styleList.get(2).getStyleTablewareList().add(styleTablewareList.get(10));
    }

    public void dinnerInit() {
        for (int i = 0; i < 15; ++i) {
            Dinner dinner = new Dinner();
            dinner.setOrderable(true);
            dinner.setEnable(true);
            dinnerList.add(dinner);
        }

        dinnerList.get(0).setName("샴페인축제디너");
        dinnerList.get(0).setDescription("2인용 식사입니다");
        dinnerList.get(0).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(0), styleList.get(0)));

        dinnerList.get(1).setName("발렌타인디너");
        dinnerList.get(1).setDescription("작은 하트모양과 큐피드로 장식되어 있습니다.");

        dinnerList.get(2).setName("프렌치디너");
        dinnerList.get(2).setDescription("Frech느낌의 식사입니다.");

        dinnerList.get(3).setName("잉글리시디너");
        dinnerList.get(3).setDescription("English느낌의 식사입니다.");

        dinnerList.get(4).setName("코리아디너");
        dinnerList.get(4).setDescription("Korea느낌의 식사입니다.");
        dinnerList.get(4).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(4), styleList.get(2)));
        dinnerList.get(4).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(4), styleList.get(1)));

        dinnerList.get(5).setName("재팬디너");
        dinnerList.get(5).setDescription("Japan느낌의 식사입니다.");
        dinnerList.get(5).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(5), styleList.get(0)));
        dinnerList.get(5).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(5), styleList.get(1)));

        dinnerList.get(6).setName("차이나디너");
        dinnerList.get(6).setDescription("China느낌의 식사입니다.");
        dinnerList.get(6).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(6), styleList.get(0)));

        dinnerList.get(7).setName("와인축제디너");
        dinnerList.get(7).setDescription("1인용 식사입니다.");
        dinnerList.get(7).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(7), styleList.get(0)));

        dinnerList.get(8).setName("청년열정디너");
        dinnerList.get(8).setDescription("열정이넘치는 청년을위한 식사입니다.");
        dinnerList.get(8).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(8), styleList.get(0)));
        dinnerList.get(8).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(8), styleList.get(2)));

        dinnerList.get(9).setName("시립대디너");
        dinnerList.get(9).setDescription("시립대생이 좋아하는 식사입니다.");
        dinnerList.get(9).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(9), styleList.get(0)));

        dinnerList.get(10).setName("컴과생디너");
        dinnerList.get(10).setDescription("컴과생들이 사랑하는 식사입니다.");
        dinnerList.get(10).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(10), styleList.get(0)));

        dinnerList.get(11).setName("돈가스디너");
        dinnerList.get(11).setDescription("돈가스 식사입니다.");
        dinnerList.get(11).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(11), styleList.get(0)));

        dinnerList.get(12).setName("코딩테스트디너");
        dinnerList.get(12).setDescription("먹으면 코딩테스트를 무조건 붙을 수 있습니다.");
        dinnerList.get(12).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(12), styleList.get(0)));

        dinnerList.get(13).setName("학점만점디너");
        dinnerList.get(13).setDescription("먹으면 학점이 만점이나옵니다.");
        dinnerList.get(13).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(13), styleList.get(0)));

        dinnerList.get(14).setName("모닝디너");
        dinnerList.get(14).setDescription("아침에 먹기 좋은 식사입니다.");
        dinnerList.get(14).getExcludedStyleList().add(
                new ExcludedStyle(dinnerList.get(14), styleList.get(0)));

        dinnerRepository.saveAll(dinnerList);
    }

    public void dinnerFoodInit() {
        dinnerList.get(0).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(0), foodList.get(15), 1));
        dinnerList.get(0).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(0), foodList.get(8), 4));
        dinnerList.get(0).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(0), foodList.get(27), 1));

        dinnerList.get(1).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(1), foodList.get(26), 2));
        dinnerList.get(1).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(1), foodList.get(14), 1));
        dinnerList.get(1).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(1), foodList.get(12), 2));
        dinnerList.get(1).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(1), foodList.get(9), 2));

        dinnerList.get(2).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(2), foodList.get(28), 1));
        dinnerList.get(2).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(2), foodList.get(29), 1));
        dinnerList.get(2).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(2), foodList.get(18), 1));
        dinnerList.get(2).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(2), foodList.get(2), 1));

        dinnerList.get(3).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(3), foodList.get(5), 1));
        dinnerList.get(3).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(3), foodList.get(22), 1));
        dinnerList.get(3).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(3), foodList.get(4), 1));
        dinnerList.get(3).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(3), foodList.get(7), 1));

        dinnerList.get(4).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(4), foodList.get(0), 1));
        dinnerList.get(4).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(4), foodList.get(1), 1));
        dinnerList.get(4).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(4), foodList.get(2), 1));
        dinnerList.get(4).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(4), foodList.get(10), 4));

        dinnerList.get(5).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(5), foodList.get(3), 3));
        dinnerList.get(5).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(5), foodList.get(6), 2));
        dinnerList.get(5).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(5), foodList.get(15), 1));
        dinnerList.get(5).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(5), foodList.get(19), 1));

        dinnerList.get(6).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(6), foodList.get(16), 1));
        dinnerList.get(6).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(6), foodList.get(17), 1));
        dinnerList.get(6).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(6), foodList.get(19), 1));
        dinnerList.get(6).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(6), foodList.get(23), 2));

        dinnerList.get(7).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(7), foodList.get(13), 1));
        dinnerList.get(7).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(7), foodList.get(14), 1));
        dinnerList.get(7).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(7), foodList.get(15), 1));
        dinnerList.get(7).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(7), foodList.get(23), 1));
        dinnerList.get(7).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(7), foodList.get(25), 1));
        dinnerList.get(7).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(7), foodList.get(26), 1));

        dinnerList.get(8).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(8), foodList.get(7), 2));
        dinnerList.get(8).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(8), foodList.get(8), 2));
        dinnerList.get(8).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(8), foodList.get(9), 2));
        dinnerList.get(8).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(8), foodList.get(10), 4));

        dinnerList.get(9).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(9), foodList.get(0), 2));
        dinnerList.get(9).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(9), foodList.get(5), 2));
        dinnerList.get(9).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(9), foodList.get(14), 1));
        dinnerList.get(9).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(9), foodList.get(15), 1));

        dinnerList.get(10).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(10), foodList.get(4), 2));
        dinnerList.get(10).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(10), foodList.get(7), 1));
        dinnerList.get(10).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(10), foodList.get(10), 2));
        dinnerList.get(10).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(10), foodList.get(11), 1));

        dinnerList.get(11).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(11), foodList.get(2), 2));
        dinnerList.get(11).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(11), foodList.get(3), 1));
        dinnerList.get(11).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(11), foodList.get(12), 1));
        dinnerList.get(11).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(11), foodList.get(13), 1));

        dinnerList.get(12).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(12), foodList.get(13), 1));
        dinnerList.get(12).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(12), foodList.get(16), 1));
        dinnerList.get(12).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(12), foodList.get(17), 1));
        dinnerList.get(12).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(12), foodList.get(21), 2));

        dinnerList.get(13).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(13), foodList.get(14), 2));
        dinnerList.get(13).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(13), foodList.get(17), 3));
        dinnerList.get(13).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(13), foodList.get(24), 1));
        dinnerList.get(13).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(13), foodList.get(25), 1));

        dinnerList.get(14).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(14), foodList.get(9), 1));
        dinnerList.get(14).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(14), foodList.get(8), 1));
        dinnerList.get(14).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(14), foodList.get(5), 1));
        dinnerList.get(14).getDinnerFoodList().add(
                new DinnerFood(dinnerList.get(14), foodList.get(20), 1));
    }


    public void clientOrderScenario(Client client) {
        Integer seed = random.nextInt(100000000);
        List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
        for (int i = 0; i < Integer.max(1, seed % 5); ++i) {
            Map<Long, Integer> foodIdAndDifferenceMap = new HashMap<>();
            for (int j = 0; j < seed % 5; ++j)
                foodIdAndDifferenceMap.put(foodList.get(random.nextInt(foodList.size())).getId(),
                        (random.nextInt(100) % 2 == 0 ? 1 : -1) * Integer.max(1, random.nextInt(3)));
            OrderSheetDto orderSheetDto = OrderSheetDto.builder()
                    .dinnerId(dinnerList.get(random.nextInt(100000000) % dinnerList.size()).getId())
                    .styleId(styleList.get(random.nextInt(100000000) % styleList.size()).getId())
                    .foodIdAndDifference(foodIdAndDifferenceMap)
                    .build();
            orderSheetDtoList.add(orderSheetDto);
        }


        OrderDto orderDto = OrderDto.builder()
                .orderStatus(OrderStatus.DELIVERED)
                .orderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .reserveTime(null)
                .address(client.getAddress() != null ? client.getAddress() : new Address("서울시", "망우로가길5", "101호"))
                .orderSheetDtoList(orderSheetDtoList)
                .build();

        ClientOrder clientOrder = orderService.makeClientOrder(client, orderDto);
        Integer orderPriceAfterSale = orderService.orderPriceAfterSale(clientOrder);
        clientOrder.setRider(riderList.get(random.nextInt(riderList.size())));
        clientOrder.setTotalPriceAfterSale(orderPriceAfterSale);
        orderRepository.save(clientOrder);
    }

    public void guestOrderScenario() {
        Integer seed = random.nextInt(100000000);
        Guest guest = new Guest();
        guestRepository.save(guest);
        guest.setName("손님" + String.valueOf(seed).substring(0, 1));
        guest.setCardNumber("1111111111111111");

        List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
        for (int i = 0; i < Integer.max(1, seed % 5); ++i) {
            Map<Long, Integer> foodIdAndDifferenceMap = new HashMap<>();
            for (int j = 0; j < seed % 5; ++j)
                foodIdAndDifferenceMap.put(foodList.get(random.nextInt(foodList.size())).getId(),
                        (random.nextInt(100) % 2 == 0 ? 1 : -1) * Integer.max(1, random.nextInt(3)));
            OrderSheetDto orderSheetDto = OrderSheetDto.builder()
                    .dinnerId(dinnerList.get(random.nextInt(100000000) % dinnerList.size()).getId())
                    .styleId(styleList.get(random.nextInt(100000000) % styleList.size()).getId())
                    .foodIdAndDifference(foodIdAndDifferenceMap)
                    .build();
            orderSheetDtoList.add(orderSheetDto);
        }


        OrderDto orderDto = OrderDto.builder()
                .orderStatus(OrderStatus.DELIVERED)
                .orderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .reserveTime(null)
                .address(new Address("제주시", "땅끝길56", "게스트하우스"))
                .orderSheetDtoList(orderSheetDtoList)
                .build();

        GuestOrder guestOrder = orderService.makeGuestOrder(guest, orderDto);
        Integer orderPriceAfterSale = orderService.orderPriceAfterSale(guestOrder);
        guestOrder.setRider(riderList.get(random.nextInt(riderList.size())));
        guestOrder.setTotalPriceAfterSale(orderPriceAfterSale);
        orderRepository.save(guestOrder);
    }
}