package NaNSsoGong.MrDaeBakDining.domain.dinner.domain;

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
public class Dinner {
    @Id
    @GeneratedValue
    @Column(name="dinner_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "dinner")
    List<DinnerItem> dinnerItemList = new ArrayList<>();
}
