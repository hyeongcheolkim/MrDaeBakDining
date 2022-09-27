package NaNSsoGong.MrDaeBakDining.domain.order.domain;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderSheet {
    @Id
    @GeneratedValue
    @Column(name = "order_sheet_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    private Style style;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_id")
    private Dinner dinner;
    @OneToMany(mappedBy = "orderSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderSheetItem> orderItemList = new ArrayList<>();
}
