package NaNSsoGong.MrDaeBakDining.domain.member.controller.form;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class MemberPageListRequest extends PageListRequest {
    private Boolean enable = true;

    public PageRequest of(){
        return PageRequest.of(super.getPage(), super.getSize(), Sort.Direction.valueOf(super.getDirection()), super.getSortBy());
    }
}
