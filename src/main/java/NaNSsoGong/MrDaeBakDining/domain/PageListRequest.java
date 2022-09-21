package NaNSsoGong.MrDaeBakDining.domain;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageListRequest {
    private Integer page;
    private Integer size;
    private String sortBy = "name";
    private String direction = "ASC";

    public PageRequest of(){
        return PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortBy);
    }
}