package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.domain.DataInitiatorForTest;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderReservedTime;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderReservedTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    DataInitiatorForTest dataInitiator;
    @Autowired
    OrderReservedTimeRepository orderReservedTimeRepository;

    @BeforeEach
    void init() {
        dataInitiator.dataInit();
    }

    @Test
    void 예약기능정상작동(){
        LocalDateTime reservedTime = LocalDateTime.of(2022, 10, 10, 10, 10, 10);
        OrderDto orderDto = dataInitiator.orderDto;
        orderDto.setOrderStatus(OrderStatus.RESERVED);
        orderDto.setReserveTime(reservedTime);
        Long id = orderService.makeGuestOrder(dataInitiator.guest.getId(), orderDto);
        Optional<OrderReservedTime> foundTime = orderReservedTimeRepository.findById(id);
        assertThat(foundTime).isPresent();
        assertThat(foundTime.get().getReservedTime()).isEqualTo(reservedTime);
    }
}