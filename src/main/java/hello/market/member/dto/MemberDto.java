package hello.market.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MemberDto {
	
	private Long id;
	
	@Email
	@NotBlank(message = "이메일을 입력해주세요.")
	private String email;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
	private String authCd;
	
	@NotBlank(message = "사용하실 닉네임을 입력해주세요.")
	private String name;
	private Integer sdId;
	private Integer sggId;
	private Integer umdId;
}
