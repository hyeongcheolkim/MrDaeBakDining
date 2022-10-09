package NaNSsoGong.MrDaeBakDining.domain.client;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;

import static NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade.*;

public abstract class ClientGradeConst {
    static private final Integer BRONZE_CUT = 0;
    static private final Integer GOLD_CUT = 1;
    static private final Integer DIAMOND_CUT = 3;
    static private final Integer CHALLENGER_CUT = 5;

    static public Integer getGradeCut(ClientGrade clientGrade){
        if(clientGrade.equals(BRONZE))
            return BRONZE_CUT;
        if(clientGrade.equals(GOLD))
            return GOLD_CUT;
        if(clientGrade.equals(DIAMOND))
            return DIAMOND_CUT;
        if(clientGrade.equals(CHALLENGER))
            return CHALLENGER_CUT;
        return BRONZE_CUT;
    }
}
