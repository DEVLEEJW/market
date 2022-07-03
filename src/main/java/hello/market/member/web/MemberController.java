package hello.market.member.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.market.common.address.dto.AddressDto;
import hello.market.common.address.dto.entity.AddressSd;
import hello.market.common.address.dto.entity.AddressSgg;
import hello.market.common.address.dto.entity.AddressUmd;
import hello.market.common.address.service.AddressService;
import hello.market.member.dto.MemberDto;
import hello.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	private final AddressService addressService;
	
	@GetMapping("/")
	public String main() {
		return "/market/main";
	}
	
	@GetMapping("/login")
	public String loginForm(@RequestParam(value = "error", required = false) String error,
            				@RequestParam(value = "exception", required = false) String exception, Model model) {
		
		model.addAttribute("error", error);
        model.addAttribute("exception", exception);
		return "/market/member/login";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("member", new MemberDto());
		model.addAttribute("addressSd", addressService.findAllSd());
		model.addAttribute("addressSgg", addressService.findAllSgg());
		model.addAttribute("addressUmd", addressService.findAllUmd());
		return "/market/member/register";
	}
	
	@PostMapping("/register")
	public String register(@Validated @ModelAttribute("member") MemberDto memberDto, BindingResult result) {
		boolean dupRes = memberService.duplicateByEmail(memberDto);
		if(dupRes) {
			result.rejectValue("email", null, "이미 사용중인 이메일 입니다.");
		}
		
		if (result.hasErrors()) {
			log.info("register BindingResult={}", result);
			return "/market/member/register";
		}
		memberService.save(memberDto);
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/";
	}
	
	@GetMapping("/member/{email}")
	public String myPageForm(@PathVariable String email, Model model) {
		model.addAttribute("member", memberService.loadUserByUsername(email));
		return "/market/member/myPage";
	}
	
	@PostMapping("/member/{email}")
	public String myPage(@PathVariable String email, @Validated @ModelAttribute("member") MemberDto memberDto, BindingResult result) {
		if (!memberDto.getPassword().isBlank()) {
			boolean matches = memberService.matchesPassword(memberDto);
			if (!matches) {
				result.rejectValue("password", null, "비밀번호가 틀립니다.");
			}
		}
		if (result.hasErrors()) {
			log.info("myPage BindingResult={}", result);
			return "/market/member/myPage";
		}
		return "redirect:/member/{id}";
	}
}
