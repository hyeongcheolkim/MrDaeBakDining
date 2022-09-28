package NaNSsoGong.MrDaeBakDining.domain.order.dto;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private LocalDateTime reserveTime;
    private List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
}
