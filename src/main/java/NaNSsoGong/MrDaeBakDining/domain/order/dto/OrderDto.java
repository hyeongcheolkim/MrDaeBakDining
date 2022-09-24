package NaNSsoGong.MrDaeBakDining.domain.order.dto;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OrderDto {
    private Address address;
    private List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
}
