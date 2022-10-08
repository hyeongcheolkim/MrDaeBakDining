package NaNSsoGong.MrDaeBakDining.domain.tableware.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TablewareCreateRequest {
    @NotEmpty
    private String name;
}
