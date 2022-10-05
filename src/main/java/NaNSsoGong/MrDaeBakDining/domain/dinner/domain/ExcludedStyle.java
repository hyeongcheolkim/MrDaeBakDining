package NaNSsoGong.MrDaeBakDining.domain.dinner.domain;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ExcludedStyle {
    @Id
    @GeneratedValue
    @Column(name="excluded_style_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "dinner_id")
    private Dinner dinner;
    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;
}
