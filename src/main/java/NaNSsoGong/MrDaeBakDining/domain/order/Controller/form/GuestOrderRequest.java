package NaNSsoGong.MrDaeBakDining.domain.order.Controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GuestOrderRequest {
    private Address address;
    private String cardNumber;
    private List<OrderSheetDto> orderSheetDtoList = new ArrayList<>();
}
