# SSG.COM 사전 과제
## [테스트 바로가기](https://ssg-market.store/)
## [Swagger UI 바로가기](https://ssg-market.store/ssg/swagger-ui/index.html)
---

## 💻 기술 스택 및 개발 환경
| 구분          | 기술                                         |
|---------------|----------------------------------------------|
| Backend       | Spring Boot 3.5.0, Spring Data JPA, Querydsl |
| DB            | H2                                           |
| Build Tool    | Gradle                                       |
| 문서화        | Swagger                                      |
| 테스트        | JUnit5                                       |
| Frontend(선택) | Vue 3.5.16                                  |
| CI/CD(선택)   | GitHub Actions, Docker, AWS EC2, ECR, NGINX |

---
## 📁 디렉토리 구조
```
📦market
 ┣ 📂.gradle
 ┣ 📂.idea
 ┣ 📂.build
 ┣ 📂gradle
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂com.ssg.market
 ┃ ┃ ┃ ┃ ┃ ┗ 📂data         
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂model (entity, enumerate)
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┗ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂item
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂api (create, read, update, delete)
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂controller, dto(req, res), service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto # 해당 도메인에서의 공통으로 사용되는 dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂repository # 해당 도메인에서 공통으로 사용되는 repo(querydsl)
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂utils # 해당 도메인에서 공통으로 사용되는 클래스 및 비즈니스 로직
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂order
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂orderItem
 ┃ ┃ ┃ ┃ ┃ ┗ 📂global
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂errorhandling
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┗ 📂resources
 ┣ 📜.gitignore
 ┣ 📜build.gradle
 ┣ 📜Dockerfile
 ┣ 📜gradlew
 ┣ 📜gradlew.bat
 ┗ 📜settings.gradle
```

---
## 🧱 아키텍처 및 레이어 설계
### 🔸 도메인 모델
- **Item**: 재고 및 판매 관련 도메인
- **Order**: 다수의 상품과 총 구매 금액 관리
- **OrderItem**: 주문과 상품 매핑, 개별 주문 상태 관리
### 🔸 계층 분리

| 계층       | 설명                                                                 |
|------------|----------------------------------------------------------------------|
| Controller | REST API 요청 처리                                       |
| Service    | 비즈니스 로직 및 트랜잭션 처리                                      |
| Repository | Service Layer에서 요청하는 DB 연산을 수행 및 데이터 반환                  |

---

## 📌 주요 기능
### ✅ 주문 생성 API
- 상품 ID와 수량 리스트를 기반으로 새로운 주문 생성
- 주문 총액 저장 및 상품 재고 차감
- 상품의 재고는 다수의 주문으로부터 공유되는 자원이기 때문에, 동시 주문을 고려하여 재고 초과를 방지해야 함
  -  **비관적 락(@Lock)** 을 사용
#### 예외 처리
- 재고 부족
- 존재하지 않는 상품
- 주문 내 중복 상품 요청
  - 주문의 의도와 맞지 않다고 판단하여 에러 취급

### ✅ 주문 취소 API
- 주문 내 특정 상품만 부분 취소 가능
- 주문 상태 변경 및 상품 재고 복구, 총 금액 수정
- **Querydsl + Fetch Join** 으로 주문목록 조회
  - 주문 목록 조회 후, 상품 재고 복구 및 총 금액 수정과정에서 Fetch Join 필요
  - **@EntityGraph** 는 조건 필터링을 지원하지 않음
  - **@Query**는 문자열 직접 작성 및 유지보수성 낮음
#### 예외 처리
- 이미 취소된 상품
- 존재하지 않는 주문/상품

### ✅ 주문 상품 조회 API
- 주문 ID에 해당하는 전체 주문 상품 리스트, 수량, 개별 금액, 총 금액 반환
#### 예외 처리
- 존재하지 않는 주문

### 🟡 전체 상품 조회 API (선택)
- 전체 상품 리스트 조회
  
### 🟡 데이터 리셋 API (선택)
- 테스트 편의를 위해 모든 데이터 초기화 및 샘플 상품 삽입

---

## ⚠️ 기타 고려사항

### 📌 디렉토리 구조

- 과제 요구사항 외 실제 서비스 확장을 고려한 구조 설계

### 📌 예외 처리 전략

- **GlobalExceptionHandler**  
  - 유효성 검사, 타입 에러 등 공통 처리

- **CustomExceptionHandler**  
  - 비즈니스 로직 관련 예외 처리  
  - 에러 코드 및 메시지 커스터마이징, 필요 시 추가 데이터 포함 ex) 에러 발생을 유발하는 ID 리스트 같이 출력
