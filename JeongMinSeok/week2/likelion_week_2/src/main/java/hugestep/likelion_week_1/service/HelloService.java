package hugestep.likelion_week_1.service;

import hugestep.likelion_week_1.dto.HelloDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class HelloService {
    private final ArrayList<HelloDto> toDoList = new ArrayList<>();

    // 할 일 목록 출력
    public ArrayList<HelloDto> showAll(){ // 전체 리스트
            return toDoList;
    }

    // 할 일 추가
    private int id = 1; // id 할당용
    public String addToDo(String addDo){
        toDoList.add(HelloDto.builder()
               .id(id++)
               .toDo(addDo)
               .completeState(false)
               .build()
        );
        return "ToDO: "+addDo + " 가 추가 되었습니다.(id:"+(id-1)+")";
    }

    // 할 일 삭제
    public String deleteToDo(int id) {
        boolean removed = toDoList.removeIf(todo -> todo.getId() == id); // 리스트안에서 id값과 일치하는게 있으면 삭제
        if (removed) {
            return "ID " + id + "번 할 일이 삭제되었습니다.";
        } else {
            return "ID " + id + "번 할 일을 찾을 수 없습니다.";
        }
    }

    // 할 일 수정
    public String updateToDo(int id, String newToDo) {
        for (int i = 0; i < toDoList.size(); i++) {
            HelloDto todo = toDoList.get(i);
            if (todo.getId() == id) {
                todo.setToDo(newToDo); // 할 일 수정
                return "ID " + id + "번 할 일이 수정되었습니다.";
            }
        }
        return "ID " + id + "번 할 일을 찾을 수 없습니다.";
    }

    // 완료 상태 전환
    public String changeCompleteState(int id) {
        for (int i = 0; i < toDoList.size(); i++) {
            HelloDto todo = toDoList.get(i);
            if (todo.getId() == id) {
                boolean current = todo.isCompleteState();
                todo.setCompleteState(!current); // true -> false, false -> true
                return "ID " + id + "번의 완료 상태가 " + !current + "로 변경되었습니다.";
            }
        }
        return "ID " + id + "번 할 일을 찾을 수 없습니다.";
    }
}
