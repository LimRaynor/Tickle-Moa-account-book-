# Service 패키지

## 왜 만들었나?
Controller가 직접 Mapper를 호출하면 비즈니스 로직이 섞여서 복잡해진다.
Service가 중간에서 "판단 + 가공"을 담당하고, Mapper에게 DB 작업을 시킨다.

## 흐름에서의 위치
```
Controller → Service (판단/가공) → Mapper → XML → DB
```
- Controller: "회원가입 요청 왔어"
- Service: "이메일 중복 확인하고, 비밀번호 암호화해서, DB에 저장할게"
- Mapper: "INSERT INTO users ..."

## @Service를 붙인 이유
- Spring이 이 클래스를 자동으로 관리(Bean 등록)해줌
- Controller에서 @Autowired로 주입받아 사용 가능

## @Autowired를 쓴 이유
- 필요한 객체(Mapper 등)를 직접 new 하지 않고 Spring이 자동으로 넣어줌
- 예: private UserMapper userMapper; → Spring이 알아서 구현체를 주입

## Service가 하는 일 (Mapper와의 차이)
- Mapper: SQL 실행만 담당 (단순 DB 작업)
- Service: 판단 + 가공 후 Mapper 호출
  - 이메일 중복 확인 (판단)
  - 비밀번호 암호화 (가공)
  - 여러 Mapper를 조합해서 호출 가능

## 주의: 자기 자신을 주입하면 안 됨
```java
// 틀림! 자기가 자기를 부르는 꼴
private TransactionService transactionService;

// 맞음! Mapper를 주입받아야 함
private TransactionMapper transactionMapper;
```
