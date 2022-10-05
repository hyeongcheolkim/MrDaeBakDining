package NaNSsoGong.MrDaeBakDining.domain.tableware.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Tableware {
    @Id
    @GeneratedValue
    @Column(name = "tableware_id")
    private Long id;
    private String name;
}
