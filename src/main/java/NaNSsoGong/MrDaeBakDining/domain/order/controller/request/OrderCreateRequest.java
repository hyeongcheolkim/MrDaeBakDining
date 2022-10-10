package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderCreateRequest {
    @NotNull
    private Address address;
    @NotNull
    private OrderStatus orderStatus;
    @Nullable
    private LocalDateTime reservedTime;
    @NotNull
    private Integer totalPriceAfterSale;
    private List<OrderSheetCreateRequest> orderSheetCreateRequestList = new ArrayList<>();

    public OrderDto toOrderDto() {
        OrderDto ret = OrderDto.builder()
                .address(this.address)
                .orderStatus(this.orderStatus)
                .orderSheetDtoList(this.orderSheetCreateRequestList.stream()
                        .map(OrderSheetCreateRequest::toOrderSheetDto)
                        .collect(Collectors.toList()))
                .totalPriceAfterSale(this.totalPriceAfterSale)
                .orderTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES
                ))
                .build();
        if (orderStatus.equals(OrderStatus.RESERVED))
            ret.setOrderStatus(OrderStatus.RESERVED);
        return ret;
    }
}