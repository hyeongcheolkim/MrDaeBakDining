package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class OrderSheetPriceBeforeSaleResponse {
    @NotNull
    private Long orderSheetId;
    @NotNull
    private Integer orderSheetPriceBeforeSale;
}
