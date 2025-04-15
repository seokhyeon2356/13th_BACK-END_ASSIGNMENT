package hugestep.likelion_week_1.controller;

import hugestep.likelion_week_1.dto.HelloDto;
import hugestep.likelion_week_1.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping("/todolist")
    public ArrayList<HelloDto> showAllUser() {
        return helloService.showAll();
    }

    @PostMapping("/todolist/add")
    public String add(@RequestParam String toDo) {
        return helloService.addToDo(toDo);
    }

    @DeleteMapping("/todolist/{id}")
    public String delete(@PathVariable int id) {
        return helloService.deleteToDo(id);
    }

    @PutMapping("/todolist/{id}")
    public String update(@PathVariable int id, @RequestParam String toDo) {
        return helloService.updateToDo(id, toDo);
    }

    @PatchMapping("/todolist/{id}/toggle")
    public String toggle(@PathVariable int id) {
        return helloService.changeCompleteState(id);
    }

}
