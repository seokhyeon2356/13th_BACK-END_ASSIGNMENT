package hugestep.likelion_week_1.controller;

import hugestep.likelion_week_1.dto.HelloDto;
import hugestep.likelion_week_1.entity.HelloEntity;
import hugestep.likelion_week_1.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping("/todolist")
    public List<HelloEntity> showAllUser() {
        return helloService.showAll();
    }

    @PostMapping("/todolist")
    public String add(@RequestBody HelloDto helloDto) {
        return helloService.addToDo(helloDto);
    }

    @DeleteMapping("/todolist/{id}")
    public String delete(@PathVariable Long id) {
        return helloService.deleteToDo(id);
    }

    @PutMapping("/todolist/{id}")
    public String update(@PathVariable Long id, @RequestParam String newtoDo) {
        return helloService.updateToDo(id, newtoDo);
    }

    @PatchMapping("/todolist/{id}")
    public String toggle(@PathVariable Long id) {
        return helloService.changeCompleteState(id);
    }

}
