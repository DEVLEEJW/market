package hello.market.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import hello.market.util.handler.LoginFailHandler;
import hello.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	private final MemberService memberService;
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); 
	}

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	public LoginFailHandler loginFailHandler() {
		return new LoginFailHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(memberService).passwordEncoder(passwordEncoder());
		
		return httpSecurity
				.authorizeRequests()
	            .antMatchers("/login", "/register").permitAll()
	            .antMatchers("/member/**").authenticated()
	            .anyRequest().permitAll()
				.and()
				.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
	            .loginPage("/login")
	            .defaultSuccessUrl("/")
	            .failureHandler(loginFailHandler())
	            .and()
            	.logout()
	            .logoutSuccessUrl("/")
	            .invalidateHttpSession(true)
				.and()
				.exceptionHandling().accessDeniedPage("/accessDenied")
				.and()
				.build();
	}
}
