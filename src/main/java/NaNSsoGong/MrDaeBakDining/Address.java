package NaNSsoGong.MrDaeBakDining;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
