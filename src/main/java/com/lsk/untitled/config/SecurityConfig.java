package com.lsk.untitled.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 
@Configuration // 빈등록(IoC관리)
public class SecurityConfig {

//	@Autowired
//	private PrincipalDetailService principalDetailService;
	
	@Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    } // 세션관련 AuthenticationManager를 사용하기위해 만듬, 오버라이드가 안됨.. 
	
    @Bean // IoC가 돼요!! (비밀번호 암호화)
    BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아아
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
//    } 2.7버전 위로 오면서 필요가 없어졌다고 한다...
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
    	http
    	.csrf((csrfConfig) ->
    		csrfConfig.disable()); // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
        // 2. Form 로그인 설정
    	http
    	.formLogin((formLogin) ->
    		formLogin
    			.loginPage("/auth/loginForm")
    			.usernameParameter("username")
    			.passwordParameter("password")
    			.loginProcessingUrl("/auth/loginProc") // 52 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인
    			.successHandler((eq, resp, authentication) -> {
    				System.out.println("디버그 : 로그인이 완료되었습니다");
    				resp.sendRedirect("/");
    			})
    			.failureHandler((req, resp, ex) -> {
    				System.out.println("디버그 : 로그인 실패 -> " + ex.getMessage());
    			}));
        // 3. 인증, 권한 필터 설정
        http.authorizeRequests(
                authorize -> authorize.antMatchers("/users/**","/board/**").authenticated()
                        .antMatchers("/manager/**")
                        .access("hasRole('ADMIN') or hasRole('MANAGER')")
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}