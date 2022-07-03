package hello.market.common.auth.dto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import hello.market.util.builder.CommonBuilder;
import hello.market.common.auth.dto.AuthDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auth")
@Getter
public class Auth {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "auth_cd")
	private String authCd;
	
	@Column(name = "auth_nm")
	private String authNm;
	
	private Auth(AuthBuilder builder) {
		this.authCd = builder.authCd;
		this.authNm = builder.authNm;
	}
	
	public static class AuthBuilder implements CommonBuilder<Auth> {
		private final String authCd;
		private final String authNm;
		
		public AuthBuilder(AuthDto authDto) {
			this.authCd = authDto.getAuthCd();
			this.authNm = authDto.getAuthNm();
		}

		@Override
		public Auth build() {
			return new Auth(this);
		}
	}
}
