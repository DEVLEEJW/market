package hello.market.util.handler;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {

		log.info("로그인 실패");

		String errorMessage;
		if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException) {
			errorMessage = "아이디 또는 비밀번호를 확인해주세요.";
		} else if (e instanceof UsernameNotFoundException) {
			errorMessage = "존재하지 않는 아이디 입니다.";
		} else {
			errorMessage = "잠시 후 다시 시도해주세요.";
		}

		errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); // 한글 인코딩 깨짐 방지
		setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
		super.onAuthenticationFailure(request, response, e);
	}
}
