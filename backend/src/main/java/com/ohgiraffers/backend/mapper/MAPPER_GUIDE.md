# Mapper 패키지

## 왜 만들었나?
Service가 DB에 접근하려면 중간 다리가 필요하다.
Mapper 인터페이스가 "이런 DB 작업이 있다"를 선언하고,
Mapper XML이 "실제 SQL은 이거다"를 정의한다.

## 흐름에서의 위치
```
Controller → Service → Mapper 인터페이스 → Mapper XML → DB
                        (메서드 선언)      (실제 SQL)
```
- Service는 Mapper 인터페이스의 메서드만 호출
- 실제 SQL은 XML에 있어서 Java 코드와 SQL이 분리됨

## 왜 interface로 만들었나?
- MyBatis가 XML의 SQL을 보고 구현체를 자동으로 만들어줌
- 개발자는 "메서드 선언"만 하면 됨 (구현은 XML이 대신함)

## @Mapper를 붙인 이유
- MyBatis에게 "이 인터페이스를 스캔해서 구현체 만들어줘"라고 알려주는 표시
- 안 붙이면 MyBatis가 이 인터페이스를 인식 못함

## 인터페이스 메서드 ↔ XML 연결 규칙
- 인터페이스 메서드 이름 = XML의 id (정확히 일치해야 함)
- XML의 namespace = 인터페이스 전체 경로

```
Java:  User findByEmail(String email);
        ↕ id가 같으면 연결됨
XML:   <select id="findByEmail"> SELECT * FROM users ... </select>
```

## XML에서 SQL 쓸 때 주의
- SQL 안의 컬럼명은 DB 그대로 (snake_case: user_id, account_id)
- #{필드명}은 Java 객체의 필드명 (camelCase: userId, accountId)
- MyBatis가 자동 변환해주는 건 SELECT 결과 → Java 매핑뿐
- INSERT/WHERE의 #{} 안은 Java 필드명을 써야 함
