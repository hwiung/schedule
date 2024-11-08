### intro. API, ERD, SQL을 만들어서 기초적인 설계를 작업을하여 API 설계와 구현 능력, CRUD 작업의 이해와 구현 능력, SQL 및 데이터베이스 활용 능력을 상승 시켰고, 결과적으로 웹 애플리케이션의 기본 구조를 설계하고 구현하는 전반적인 기술을 습득하였다.

### 1. 일정 생성 API

- **URL**: '/api/schedules'
- **Method**: 'POST'
    - **Request Body**:
      - **task**: 할 일 내용
      - **author**: 작성자 이름
      - **password**: 비밀번호(수정/삭제시 필요)
      - **createdAt와 updatedAt 필드는 서버에서 자동으로 현재 날짜와 시간으로 설정됩니다.**

```json
{
  "task": "요리 레시피",
  "author": "백종원",
  "password": "1234"
}
```

- **Response**: 
  - 201 created

```json
{
  "id": 1,
  "task": "후기 작성",
  "author": "갑",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 2. 전체 일정 조회 API

- **URL**: '/api/schdules'
- **Method**: 'GET'
- **Query Parameters (선택)**
  - **updatedAt**: 조회할 일정의 수정일
  - **author**: 조회할 일정의 작성자 이름
  - **updatedAt 또는 author 중 하나, 또는 둘 다 포함할 수 있으며, 조건이 없는 경우 전체 일정 목록을 조회합니다.**
  - **정렬 기준**: updatedAt(수정일) 기준으로 내림차순 정렬하여 반환
  
- **Response**:
  - '200 OK'

```json
[
  {
    "id": 1,
    "task": "과제 제출",
    "author": "김씨",
    "createdAt": "2024-01-01T013:00:00",
    "updatedAt": "2024-01-01T14:00:00"
  },

  {
    "id": 2,
    "task": "TIL 작성",
    "author": "박씨",
    "createdAt": "2024-01-01T021:00:00",
    "updatedAt": "2024-01-01T22:00:00"
  }
]
```

### 3. 특정 일정 조회 API

- **URL**: '/api/schedules/{id}'
- **Method**: 'GET'
- **Path Parameters**
    - **id**: '조회할 일정의 고유 ID'
- **Response**: 

  - '200 OK'(성공시)

```json
{
  "id": 1,
  "task": "강의 수강",
  "author": "스파르탄",
  "createdAt": "2024-01-01T09:00:00",
  "updatedAt": "2024-01-01T21:00:00"
}
```

  - 404 Not Found (일정이 존재하지 않을 시):

```json
    {
    "status": 404,
    "error": "Not Found",
    "message": "일정을 찾을 수 없습니다."
    }
```

## 4. 일정 수정 API

- **URL**: /api/schedules/{id}
- **Method**: 'PUT'
- **Request Parameters**
    - **id**: '수정할 일정의 고유 ID'
- **선택한 일정 수정**
  - 선택한 일정 내용 중 '할 일 내용', '작성자 이름'만 수정 가능하며, 이때 서버에 비밀번호를 함께 전달합니다.
  - 작성일 은 변경할 수 없으며, 수정일 은 수정 완료 시, 수정한 시점으로 변경합니다.
- **Request Body**
  - **task**: 수정된 할 일 내용
  - **author**: 수정된 작성자 이름
  - **password**: 비밀번호(수정/삭제시 필요)

```json
{
  "task": "수정된 할 일 내용",
  "author": "수정된 작성자 이름",
  "password": "비밀번호"
}
```

- **Response**:

  - '200 OK'(성공 시):
  
```json
{
      "id": 1,
      "task": "수정된 할 일 내용",
      "author": "수정된 작성자 이름",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-02T10:00:00"
}
  ```
  
  - '403 Forbidden' (비밀번호 불일치 시):

```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "비밀번호가 일치하지 않습니다."
}
```

  - '404 Not Found' (일정이 존재하지 않을 시):

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "일정을 찾을 수 없습니다."
}
```

## 5. 일정 삭제 API

- **URL**: /api/schedules/{id}
- **Method**: 'DELETE'
- **Request Parameters**
    - **id**: '수정할 일정의 고유 ID'
- **선택한 일정 삭제**
  - 선택한 일정을 삭제할 수 있으며, 이때 비밀번호를 서버에 함께 전달합니다.
- **Request Body**
  - **password**: 비밀번호(수정/삭제시 필요)
  
```json
{
  "password": "비밀번호"
  }
```

- **Response**
  - '204 No Content'(성공 시): 요청이 성공해도 클라이언트에게 따로 응답을 반환하지 않음.
  - '403 Forbidden'(비밀번호 불일치 시)
  
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "비밀번호가 일치하지 않습니다."
}
```
  - 404 Not Found (일정이 존재하지 않을 시)
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "일정을 찾을 수 없습니다."
}
```
![ERD Diagram](./erd-diagram.jpg)

