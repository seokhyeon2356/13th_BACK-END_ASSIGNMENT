package hugestep.likelion_week_1.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HelloDto {
    private int id; // 해야하는 일 번호
    private String toDo;   // 해야하는 일
    private boolean completeState; // 완료 유무
}
