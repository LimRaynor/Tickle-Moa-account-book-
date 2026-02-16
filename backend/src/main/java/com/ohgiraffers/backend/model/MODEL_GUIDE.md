# Model 패키지

## 왜 만들었나?
DB에서 데이터를 꺼내면 Java에서 받을 그릇이 필요하다.
Model 클래스가 그 그릇 역할을 한다. (테이블 1개 = Model 1개)

## 흐름에서의 위치
```
Controller → Service → Mapper → XML(SQL) → DB
                         ↕
                    Model (데이터 운반)
```
Mapper가 DB에서 데이터를 꺼내면 → Model 객체에 담아서 → Service로 전달한다.
Service가 데이터를 저장하려면 → Model 객체에 넣어서 → Mapper에 전달한다.

## 왜 이렇게 작성했나?

### @Data를 붙인 이유
- getter/setter를 일일이 쓰면 필드마다 6줄씩 늘어남
- Lombok @Data가 자동 생성해주니까 필드 선언만 하면 됨

### 필드를 camelCase로 쓴 이유
- DB는 snake_case (user_id), Java는 camelCase (userId)가 관례
- application.yml에 map-underscore-to-camel-case: true 설정해놔서
  MyBatis가 user_id → userId로 자동 매핑해줌

### Long을 쓴 이유 (int 대신)
- DB의 BIGINT은 범위가 매우 큼 → int(21억)으로 부족할 수 있음
- PK, FK는 Long으로 통일하는 게 관례

### BigDecimal을 쓴 이유 (String, double 대신)
- 돈 계산에 double 쓰면 소수점 오차 발생 (0.1 + 0.2 = 0.30000000004)
- BigDecimal은 정확한 소수점 계산 보장
- DB의 DECIMAL 타입과 1:1 매핑

### LocalDateTime / LocalDate를 쓴 이유 (Date 대신)
- java.util.Date는 옛날 방식 (Java 8 이전)
- LocalDateTime, LocalDate는 Java 8+ 표준
- 더 직관적이고 다루기 쉬움
