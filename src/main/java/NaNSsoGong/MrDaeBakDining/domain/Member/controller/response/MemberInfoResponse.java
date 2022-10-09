package NaNSsoGong.MrDaeBakDining.domain.member.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class MemberInfoResponse {
    private Long memberId;
    private String memberName;
    private String memberLoginId;
    private String memberPassword;

    public MemberInfoResponse(Member member){
        this.memberId = member.getId();
        this.memberName = member.getName();
        this.memberLoginId = member.getLoginId();
        this.memberPassword = member.getPassword();
    }
}
