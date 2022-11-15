package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response;

public interface DinnerNameAndIdDto {
    String getName();
    Long getId();
    default String convertToJsonString(){
        return getName().concat("/").concat(String.valueOf((getId())));
    }
}
