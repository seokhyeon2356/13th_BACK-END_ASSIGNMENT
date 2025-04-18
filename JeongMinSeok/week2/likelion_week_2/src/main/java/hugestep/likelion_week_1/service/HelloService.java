package hugestep.likelion_week_1.service;

import hugestep.likelion_week_1.dto.HelloDto;
import hugestep.likelion_week_1.entity.HelloEntity;
import hugestep.likelion_week_1.repository.HelloEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloEntityRepository helloEntityRepository;

    // 할 일 목록 출력
    public List<HelloEntity> showAll(){ // 전체 리스트
            return helloEntityRepository.findAll();
    }

    // 할 일 추가
    public String addToDo(HelloDto addDo){
        helloEntityRepository.save(HelloEntity.builder()
               .toDo(addDo.getToDo())
               .completeState(false)
               .build()
        );
        return "ToDO: "+addDo.getToDo() + " 가 추가 되었습니다.";
    }

    // 할 일 삭제
    public String deleteToDo(Long id) {
        if (helloEntityRepository.existsById(id)) { // id와 일치하는게 있으면
            helloEntityRepository.deleteById(id);   // 삭제
            return "ID " + id + "번 할 일이 삭제되었습니다.";
        } else {
            return "ID " + id + "번 할 일을 찾을 수 없습니다.";
        }
    }

    // 할 일 수정
    public String updateToDo(Long id, String newToDo) {
        HelloEntity todo = helloEntityRepository.findById(id).orElse(null);

        if (todo != null) {
            todo.setToDo(newToDo); // 할 일 수정
            helloEntityRepository.save(todo); // 저장
            return "ID " + id + "번 할 일이 수정되었습니다.";
        } else {
            return "ID " + id + "번 할 일을 찾을 수 없습니다.";
        }
    }

    // 완료 상태 전환
    public String changeCompleteState(Long id) {
        HelloEntity todo = helloEntityRepository.findHelloEntityById(id);

        if (todo != null) {
            boolean current = todo.isCompleteState();
            todo.setCompleteState(!current); // true → false, false → true
            helloEntityRepository.save(todo); // 변경 사항 저장
            return "ID " + id + "번의 완료 상태가 " + !current + "로 변경되었습니다.";
        } else {
            return "ID " + id + "번 할 일을 찾을 수 없습니다.";
        }
    }
}
