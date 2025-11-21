import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceClient {

    private final WebClient employeeWebClient;

    public EmployeeSimpleDto getEmployeeBasicInfo(Long employeeId) {
        return employeeWebClient.get()
                .uri("/api/employees/{id}/simple", employeeId) // Endpoint zwracający lekkie DTO
                .retrieve()
                .bodyToMono(EmployeeSimpleDto.class) // Mapujemy na klasę z common-domain
                .block();
    }
}