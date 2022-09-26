package NaNSsoGong.MrDaeBakDining.domain.order.Controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientOrderRequest {
    private Address address;
    private List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
}
