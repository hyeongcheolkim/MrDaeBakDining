package NaNSsoGong.MrDaeBakDining.domain.rider.domain;

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
public class RiderSign {
    @Id
    @GeneratedValue
    @Column(name = "rider_sign_id")
    private Long id;
    private String name;
    private String loginId;
    private String password;

    public Rider toRider(){
        Rider rider = new Rider();
        rider.setName(this.name);
        rider.setLoginId(this.loginId);
        rider.setPassword(this.password);
        return rider;
    }
}
