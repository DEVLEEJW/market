package hello.market.member.dto.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hello.market.util.builder.CommonBuilder;
import hello.market.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
@Getter
public class Member implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155568814178953082L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sd_id")
	private Integer sdId;
	
	@Column(name = "sgg_id")
	private Integer sggId;
	
	@Column(name = "umd_id")
	private Integer umdId;
	
	@Column(name = "auth_cd")
	private String authCd;
	
	private Member(MemberBuilder builder) {
		this.email = builder.email;
		this.password = builder.password;
		this.name = builder.name;
		this.sdId = builder.sdId;
		this.sggId = builder.sggId;
		this.umdId = builder.umdId;
		this.authCd = builder.authCd;
	}
	
	public static class MemberBuilder implements CommonBuilder<Member> {
		private final String email;
		private final String password;
		private final String name;
		private final Integer sdId;
		private final Integer sggId;
		private final Integer umdId;
		private final String authCd;
		
		public MemberBuilder(MemberDto memberDto) {
			this.email = memberDto.getEmail();
			this.password = memberDto.getPassword();
			this.name = memberDto.getName();
			this.sdId = memberDto.getSdId();
			this.sggId = memberDto.getSggId();
			this.umdId = memberDto.getUmdId();
			this.authCd = memberDto.getAuthCd();
		}

		@Override
		public Member build() {
			return new Member(this);
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
	    for (String role : authCd.split(",")) {
	      roles.add(new SimpleGrantedAuthority(role));
	    }
	    return roles;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
