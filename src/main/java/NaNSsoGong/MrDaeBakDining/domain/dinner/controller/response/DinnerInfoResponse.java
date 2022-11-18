package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DinnerInfoResponse {
    private Long dinnerId;
    private String dinnerName;
    private String dinnerDescription;
    private Boolean dinnerOrderable;
    private String dinnerImageAbsolutePath;
    List<DinnerFoodInfoResponse> dinnerFoodInfoResponseList = new ArrayList<>();
    List<ExcludedStyleInfoResponse> excludedStyleInfoResponseList = new ArrayList<>();

    public DinnerInfoResponse(Dinner dinner) {
        this.dinnerId = dinner.getId();
        this.dinnerName = dinner.getName();
        this.dinnerDescription = dinner.getDescription();
        this.dinnerOrderable = dinner.getOrderable();
        this.dinnerImageAbsolutePath = dinner.getImageAbsolutePath();
        this.dinnerFoodInfoResponseList = dinner.getDinnerFoodList().stream()
                .map(DinnerFoodInfoResponse::new)
                .collect(Collectors.toList());

        this.excludedStyleInfoResponseList = dinner.getExcludedStyleList().stream()
                .map(ExcludedStyleInfoResponse::new)
                .collect(Collectors.toList());
    }
}
