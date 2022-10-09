package NaNSsoGong.MrDaeBakDining.domain.order;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;

import static NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade.*;

public abstract class SalePolicy {
    private static final Integer BRONZE_SALE_RATE = 0;
    private static final Integer GOLD_SALE_RATE = 3;
    private static final Integer DIAMOND_SALE_RATE = 5;
    private static final Integer CHALLENGER_SALE_RATE = 10;

    static public Integer saleRate(ClientGrade clientGrade) {
        if (clientGrade.equals(BRONZE))
            return BRONZE_SALE_RATE;
        if (clientGrade.equals(GOLD))
            return GOLD_SALE_RATE;
        if (clientGrade.equals(DIAMOND))
            return DIAMOND_SALE_RATE;
        if (clientGrade.equals(CHALLENGER))
            return CHALLENGER_SALE_RATE;
        return BRONZE_SALE_RATE;
    }
}
