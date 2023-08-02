package com.sparta.pracspring_blog.common.config;

import com.sparta.pracspring_blog.common.jwt.JwtTokenProvider;
import com.sparta.pracspring_blog.common.jwt.JwtAuthenticationFilter;
import com.sparta.pracspring_blog.common.jwt.JwtAuthorizationFilter;
import com.sparta.pracspring_blog.common.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/* Http 설정 관리와 Bean 등록을 하는 설정(Configuration) 클래스

    (Spring Security 인증 절차)
    ?? 어떤 순서로 이루어지지 ??

 */


@Configuration // 설정과 관련된 Bean 등록 애너테이션 (설정 + @Component)
@EnableWebSecurity // Spring Security 지원 즉, Spring Security 설정을 활성화시키는 애너테이션
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;


    //-----------------------------------------------


    /* password 암호화 및 해시에 필요한 PasswordEncoder 클래스를 Bean 으로 등록하기 위해, passwordEncoder() 를 정의하고 @Bean 을 통해 메서드를 Bean 으로 등록 */
    @Bean // 메서드 레벨에서의 Bean 등록 애너테이션
    public PasswordEncoder passwordEncoder() {

        // BCryptPasswordEncoder 를 사용하여 비밀번호를 암호화하는 PasswordEncoder 를 생성하고 반환
        return new BCryptPasswordEncoder();
    }
    // -> PasswordEncoder 를 Bean 으로 등록하면,
    // 다른 Spring Security 관련 로직에서 PasswordEncoder 를 주입받아 사용할 수 있게 된다.


    //-----------------------------------------------


    /* Spring Security 에서 인증을 처리하는 핵심 인터페이스인 AuthenticationManager 를 Bean 으로 등록하기 위해, authenticationManager() 메서드를 정의하고 및 Bean 등록 */
    @Bean
    // 1) AuthenticationConfiguration 을 매개변수로 받아서
    // AuthenticationConfiguration : Spring Security 에서 제공하는 클래스로, 인증 관련 설정을 구성하는데 사용
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        // 2) getAuthenticationManager() 를 호출하여 AuthenticationManager 를 반환
        return configuration.getAuthenticationManager();

    }
    // -> AuthenticationManager 를 Bean 으로 등록함으로써,
    // 여러가지 Spring Security 관련 로직에서 AuthenticationManager 를 주입받아 사용할 수 있게 된다. EX. 인증 필터, 커스텀 인증 로직


    //-----------------------------------------------


    /* CF. Security Filter Chain
     : HTTP 요청을 처리하고 보안 관련 작업을 수행하는 일련의 필터들의 연속을 말한다. 즉, HTTP 요청이 들어오면 Spring Security가 필터 체인을 통해 보안 기능을 적용하고 인증/인가 처리를 수행한다. 참고로 이러한 필터 체인은 FilterChainProxy라는 Spring Security의 컴포넌트에 의해 생성 및 관리되며 아래와 같은 필터들을 포함한다.

         * EX. 인증관련필터 (JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter, OAuth2AuthenticationFilter, etc.),
         * 인가관련필터 (RoleBasedAuthorizationFilter,    PermissionBasedAuthorizationFilter, etc.), etc.

     * 위와 같은 필터들은 필터 체인 내에서 순서대로 실행된다.
     */


    //-----------------------------------------------


    /* JWT 토큰을 사용한 인증 처리를 담당하는 필터인 JwtAuthenticationFilter 를 Bean 으로 등록하는 메서드 */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {

        // 1) jwtTokenProvider 를 생성자 매개변수로 전달해 JwtAuthenticaitonFilter 를 초기화
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtTokenProvider);

        // 2) JwtAuthenticationFilter 에 AuthenticationManager 를 설정하여, 해당 filter 에서 인증이 수행될 수 있도록 한다.
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));

        return filter;
    }
    // -> JwtAuthenticationFilter를 Bean으로 등록하면,
    // Spring Security의 필터 체인에 자동으로 포함되어 JWT 인증을 처리하는데 사용된다.


    //-----------------------------------------------


    /* JWT 토큰을 사용한 인가(허가) 처리를 담당하는 필터인 JwtAuthorizationFilter 를 Bean 으로 등록하는 메서드 */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {

        return new JwtAuthorizationFilter(jwtTokenProvider, userDetailsService);

        // JwtAuthorizationFilter : HTTP 요청 헤더에서 JWT 토큰을 추출하고, 토큰의 유효성을 검사하여 해당 요청에 대한 인가 처리를 수행하는 역할을 한다.
    }
    // -> JwtAuthorizationFilter를 Bean으로 등록하면,
    // Spring Security의 필터 체인에 자동으로 포함되며 JWT 인가를 처리하는 데 사용된다.


    //-----------------------------------------------


    /* Spring Security의 '필터 체인을 구성하는 역할을 하는 SecurityFilterChain'을 Bean으로 등록 */
    @Bean

    // 1) Spring Security 구성 클래스인 HttpSecurity 를 매개변수로 받아 필터체인에 대한 구체적인 설정 정의
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 2) CSRF(Cross-Site Request Forgery) 보호기능 비활성화
        http.csrf((csrf) -> csrf.disable());


        /* CF. 'CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화한다'는 것은 Spring Security에서 CSRF 공격을 방지하기 위한 기능을 해제하는 것 */

        /* CF. CSRF란?
            * 사이트 간 요청 위조라는 공격 기법으로 인증된 사용자의 권한 즉, 토큰 또는 세션 정보를 탈취하여 악의적인 요청을 하는 기법이다.
            * Spring Security는 기본적으로 CSRF 보호 기능을 활성화하여 이러한 CSRF 공격을 방지한다. 여기서 CSRF 보호 기능은 서버에서 생성한 토큰을 요청과 함께 전달하여 해당 토큰을 검증하는 방식으로 동작한다. 그러나 일부 상황에서는 CSRF 공격의 위험이 낮거나 특정 요구사항에 맞지 않는 경우에는 CSRF 보호 기능을 비활성화하기도 한다.
        */


        // 3) default 설정인 Session 관리 방식을 비활성화하고 Stateless 한 세션 관리를 사용하도록 설정한다. 이는 JWT 방식을 사용하기 위함이다.
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );


        /* CF. Stateless 한 세션 관리란?
            * Spring Security의 기본 동작은 세션을 사용하여 인증된 사용자의 상태를 유지하는 것이다. 즉, 세션은 서버에 사용자 정보를 저장하고 클라이언트는 세션 ID를 사용하여 서버의 세션과 연결된다. 이러한 세션 방식은 상태를 유지하고 서버의 메모리를 사용하기 때문에 서버에 과부하가 발생할 수 있는 단점이 있다.
            * 이를 보완하기 위해, STATELESS 세션 관리 방식을 사용할 수 있는데, 이는 세션을 사용하지 않고 서버가 각 요청에 대해 독립적으로 처리하는 방식이다. 클라이언트가 모든 인정 정모를 요청 헤더에 포함시켜 전달하게 되므로, 서버는 클라이언트의 상태를 유지하지 않고 요청이 있을 때만 인증 정보를 확인하여 처리해주면 된다.
            * 이러한 방식은 RESTful API와 같이 상태를 저장하지 않는 Stateless한 서비스에서 자주 사용되는 방식이다.
        */


        // 4) HTTP 요청에 대한 인가 규칙 정의
        // 일부 요청을 허용하고 나머지 요청은 인증을 필요한 것으로 설정
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 즉, 정적 리소스 접근 허용 설정
                        .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
                        .requestMatchers("/api/users/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                        .anyRequest().authenticated() //위에서 설정되지 않은 모든 요청에 대해서는 인증이 필요하다는 설정
        );


        // 5) Spring Security의 로그인 설정
        // 로그인 페이지 설정 및 해당 페이지에 대한 접근을 허용
        http.formLogin((formLogin) ->
                formLogin
                        .loginPage("/api/users/signIn").permitAll()
        );


        // 6) 커스텀 필터 등록 및 순서 지정
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // jwtAuthorizationFilter()가 JwtAuthenticationFilter보다 먼저 실행되고,
        // jwtAuthenticationFilter()가 UsernamePasswordAuthenticationFilter보다 먼저 실행


        // 7) HttpSecurity 객체를 사용하여 SecurityFilterChain을 빌드하여 반환
        return http.build();
    }
    // -> SecurityFilterChain을 Bean으로 등록하면,
    // 빌드된 SecurityFilterChain이 Spring Security의 필터 체인에 등록되어 요청 처리에 사용할 수 있게 된다.


    //-----------------------------------------------


}
