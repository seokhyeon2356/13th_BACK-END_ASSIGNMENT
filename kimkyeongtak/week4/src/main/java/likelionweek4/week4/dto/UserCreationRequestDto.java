package likelionweek4.week4.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreationRequestDto {

    private String username;
    private String password;
}
