package NaNSsoGong.MrDaeBakDining.domain.order.dto;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Address address;
    private LocalDateTime orderTime;
    private List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
}
