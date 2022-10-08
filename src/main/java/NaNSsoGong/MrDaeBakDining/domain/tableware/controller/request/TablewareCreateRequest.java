package NaNSsoGong.MrDaeBakDining.domain.tableware.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class TablewareCreateRequest {
    @NotEmpty
    private String name;
}
