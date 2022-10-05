package NaNSsoGong.MrDaeBakDining.domain.member.service;

import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Boolean isLoginIdAvailable(String loginId) {
        List<Member> memberList = memberRepository.findAllByLoginId(loginId);
        for (var LoginAble : memberList)
            if (LoginAble.getEnable())
                return false;
        return true;
    }

    public Optional<Member> login(String loginId, String password) {
        List<Member> memberList = memberRepository.findAllByLoginId(loginId);
        for (var member : memberList)
            if (member.getEnable() && member.getPassword().equals(password))
                return Optional.of(member);
        return Optional.empty();
    }
}
