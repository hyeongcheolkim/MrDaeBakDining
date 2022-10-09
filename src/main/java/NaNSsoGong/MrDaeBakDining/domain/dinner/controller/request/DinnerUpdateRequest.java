package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request;

import lombok.Data;

@Data
public class DinnerUpdateRequest {
    private String name;
    private String description;
    private Boolean orderable;
}
