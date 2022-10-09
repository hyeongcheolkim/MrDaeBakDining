package NaNSsoGong.MrDaeBakDining.domain.order.dto;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private LocalDateTime reserveTime;
    private Integer totalPriceAfterSale;
    private List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
}
