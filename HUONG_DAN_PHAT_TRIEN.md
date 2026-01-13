# Hướng Dẫn Phát Triển Dự Án Hexagonal Architecture (Tiếng Việt)

Tài liệu này giải thích cấu trúc dự án và quy trình từng bước để thêm mới một tính năng nghiệp vụ (Business Feature) vào hệ thống.

## 1. Cấu Trúc Thư Mục

Dự án tuân theo kiến trúc **Hexagonal (Ports and Adapters)**, chia làm 3 lớp chính tách biệt rõ ràng:

```text
src/main/java/com/example/hexagon
├── domain              <-- LỚP 1: NGHIỆP VỤ CỐT LÕI (Core Domain)
│   └── (Entities, Logic thuần, không framework)
│
├── application         <-- LỚP 2: ỨNG DỤNG (Application Layer)
│   ├── port
│   │   ├── in          <-- Input Ports (Use Cases - Định nghĩa làm gì)
│   │   └── out         <-- Output Ports (Giao tiếp ra ngoài - DB, API khác)
│   └── service         <-- Triển khai Input Ports (Service Logic)
│
└── infrastructure      <-- LỚP 3: HẠ TẦNG (Infrastructure Layer)
    ├── adapter
    │   ├── in
    │   │   └── web     <-- Web Adapter (Controllers, DTOs requests)
    │   └── out
    │       └── persistence <-- Persistence Adapter (JPA, Database)
    └── configuration   <-- Cấu hình Spring Boot
```

### Chi tiết các lớp:

1.  **Domain (`domain`)**:

    - Chứa các **POJO** (Plain Old Java Object).
    - **QUAN TRỌNG**: Không được chứa bất kỳ annotation nào của Framework (không `@Entity`, không `@Service`, không `@Autowired`).
    - Ví dụ: `Todo.java` chỉ chứa các thuộc tính và logic nghiệp vụ thuần túy.

2.  **Application (`application`)**:

    - Đóng vai trò điều phối.
    - **`port.in` (Input Ports)**: Là các Interface định nghĩa các "Hành động" mà hệ thống có thể thực hiện (Ví dụ: `CreateTodoUseCase`, `GetTodoQuery`).
    - **`port.out` (Output Ports)**: Là các Interface định nghĩa việc hệ thống cần lấy/lưu dữ liệu từ đâu (Ví dụ: `TodoRepositoryPort`).
    - **`service`**: Implement các `port.in`. Tại đây sẽ gọi đến `port.out` để thao tác dữ liệu. Service **không biết** dữ liệu được lưu ở đâu (Database hay File).

3.  **Infrastructure (`infrastructure`)**:
    - Nơi duy nhất chứa code dính đến Framework (Spring Boot, Hibernate, SQL).
    - **`adapter.in.web`**: Chứa Controller. Nhiệm vụ là nhận Request REST, validate dữ liệu, rồi gọi `port.in`.
    - **`adapter.out.persistence`**: Chứa JPA Entity, Repository. Nhiệm vụ là implement `port.out` để thực sự lưu dữ liệu vào Database.

---

## 2. Quy Trình Thêm Một Tính Năng Mới

Giả sử bạn cần làm tính năng: **"Xóa Todo"** (Delete Todo).

Hãy làm theo đúng thứ tự luồng dữ liệu (từ trong ra ngoài hoặc từ nghiệp vụ đến hạ tầng):

### Bước 1: Định nghĩa Input Port (Use Case)

- **Vị trí**: `application/port/in`
- **Hành động**: Tạo interface `DeleteTodoUseCase`.
  ```java
  public interface DeleteTodoUseCase {
      void deleteTodo(Long id);
  }
  ```

### Bước 2: Định nghĩa Output Port (Nếu cần thao tác DB mới)

- **Vị trí**: `application/port/out`
- **Hành động**: Mở `TodoRepositoryPort`, thêm hàm `deleteById`.
  ```java
  public interface TodoRepositoryPort {
      // ... các hàm cũ
      void deleteById(Long id);
  }
  ```

### Bước 3: Implement Service (Application Logic)

- **Vị trí**: `application/service/TodoService.java`
- **Hành động**: Implement `DeleteTodoUseCase` và viết logic.
  ```java
  @Override
  public void deleteTodo(Long id) {
      // Kiểm tra tồn tại nếu cần
      todoRepositoryPort.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
      todoRepositoryPort.deleteById(id);
  }
  ```

### Bước 4: Implement Persistence Adapter (Hạ tầng DB)

- **Vị trí**: `infrastructure/adapter/out/persistence`
- **Hành động**: Cập nhật `TodoPersistenceAdapter` để thực thi hàm `deleteById` vừa thêm ở Bước 2.
  ```java
  @Override
  public void deleteById(Long id) {
      springDataTodoRepository.deleteById(id);
  }
  ```

### Bước 5: Implement Web Adapter (API)

- **Vị trí**: `infrastructure/adapter/in/web/TodoController.java`
- **Hành động**: Tạo Endpoint API để gọi `DeleteTodoUseCase`.

  ```java
  private final DeleteTodoUseCase deleteTodoUseCase; // Inject thêm

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
      deleteTodoUseCase.deleteTodo(id);
      return ResponseEntity.noContent().build();
  }
  ```

## 3. Tổng Kết Nguyên Tắc Vàng

1.  **Code Domain không phụ thuộc ai**: Nếu bạn thấy `import org.springframework...` trong package `domain`, code đó vi phạm quy tắc.
2.  **Service chỉ làm việc với Interface**: Service không được gọi trực tiếp `TodoPersistenceAdapter` hay `SpringDataRepository`. Nó chỉ được gọi `TodoRepositoryPort`.
3.  **Tách biệt DTO và Entity**:
    - `Todo.java` (Domain): Dùng trong Service logic.
    - `TodoJpaEntity.java` (Infrastructure): Dùng để mapping với Database Table.
    - `CreateTodoRequest` / `TodoResponse` (Infrastructure): Dùng để giao tiếp API JSON.
    - Việc chuyển đổi (Mapping) xảy ra ở lớp Adapter.
