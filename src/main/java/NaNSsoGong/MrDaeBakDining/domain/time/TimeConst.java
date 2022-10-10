package NaNSsoGong.MrDaeBakDining.domain.time;

import lombok.Getter;
import lombok.Setter;


public abstract class TimeConst {
    @Getter
    @Setter
    static Integer openHour = 15;
    @Getter
    @Setter
    static Integer openMinute = 30;
    @Getter
    @Setter
    static Integer closeHour = 22;
    @Getter
    @Setter
    static Integer closeMinute = 0;
}
