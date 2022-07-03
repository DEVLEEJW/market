package hello.market.member.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hello.market.member.dto.MemberDto;
import hello.market.member.dto.entity.Member;
import hello.market.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	@Override
	public Member loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}
	
	public boolean duplicateByEmail(MemberDto memberDto) {
		return memberRepository.existsByEmail(memberDto.getEmail());
	}
	
	public Member save(MemberDto memberDto) {
		memberDto.setPassword(new BCryptPasswordEncoder().encode(memberDto.getPassword()));
		memberDto.setAuthCd("ROLE_USER");
		return memberRepository.save(new Member.MemberBuilder(memberDto).build());
	}
	
	public boolean matchesPassword(MemberDto memberDto) {
		return new BCryptPasswordEncoder().matches(memberDto.getPassword(), loadUserByUsername(memberDto.getEmail()).getPassword());
	}
	
	public Member modify(MemberDto memberDto) {
		return null;
	}
}
