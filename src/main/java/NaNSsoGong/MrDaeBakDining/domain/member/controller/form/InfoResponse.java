package NaNSsoGong.MrDaeBakDining.domain.member.controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.MemberGrade;
import lombok.Data;

@Data
public class InfoResponse {
    private String name;
    private String cardNumber;
    private Address address;
    private MemberGrade memberGrade;

    public InfoResponse(Member member) {
        name = member.getName();
        cardNumber = member.getCardNumber();
        address = member.getAddress();
        memberGrade = member.getMemberGrade();
    }
}
