1. BOM 문자 에러

뭐가 문제였나

AccountController.java, TransactionController.java, AuthController.java 파일 맨 앞에 눈에 안 보이는 EF BB BF 3바이트가 붙어있었음.

이게 BOM (Byte Order Mark) — Windows 메모장이나 일부 에디터가 "이 파일은 UTF-8이에요"라고 표시하려고 파일 맨 앞에 몰래 넣는 바이트.

왜 에러가 나나

Java 컴파일러는 파일 첫 줄을 package com.tickle_moa...로 기대하는데, BOM 때문에 ?package com.tickle_moa...로 읽힘. ?가 뭔지 모르니까 illegal character: '\ufeff' 에러.

어떻게 고쳤나

sed로 3개 파일 앞의 BOM 바이트를 제거함.

  ---
2. AuthController 깨진 한글

뭐가 문제였나

return ResponseEntity.ok("?뚯썝媛???깃났");

원래 "회원가입 성공"이었을 텍스트가 인코딩이 꼬여서 깨져있었음.

왜 이렇게 되나

파일을 UTF-8로 저장했는데 다른 인코딩(EUC-KR 등)으로 다시 열어서 저장하면 한글 바이트가 엉뚱하게 변환됨.

어떻게 고쳤나

파일을 정상 UTF-8로 다시 작성하면서 "회원가입 성공"으로 복원.

  ---
3. 401 Unauthorized (Spring Security 차단)

뭐가 문제였나

build.gradle에 spring-boot-starter-security 의존성이 있는데, SecurityConfig.java가 비어있었음:

public class SecurityConfig {
}

2가지가 동시에 빠져있었음:
- BCryptPasswordEncoder 빈 → 서버 시작 자체가 안 됨
- SecurityFilterChain 설정 → 모든 API가 401로 차단됨

왜 이렇게 되나

Spring Security는 설정이 없으면 기본값이 "전부 차단". 로그인 폼도 자동 생성해서 인증 안 된 요청은 전부 401 응답.

어떻게 고쳤나

@Bean
public BCryptPasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();  // 비밀번호 암호화용 빈 등록
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
http
.csrf(csrf -> csrf.disable())           // CSRF 끔 (API 서버라서)
.authorizeHttpRequests(auth -> auth
.anyRequest().permitAll()            // 모든 요청 허용
);
return http.build();
}

JWT 미구현 상태니까 일단 전체 허용(permitAll)으로 열어둔 것. 나중에 JWT 구현하면 여기에 인증 필터를 추가하게 됨.

들여쓰기 실수 : 원인 찾았습니다. mybatis, jwt, server가 spring: 안에 들여쓰기 되어 있어서 spring.jwt.secret으로 인식되고 있어요.
이 세 개는 spring: 밖에 있어야 합니다.