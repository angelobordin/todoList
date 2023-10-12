package br.com.angelobordin.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task") 
public class TaskController {
    
    @Autowired
    private ITaskRepository repository;

    @PostMapping("/create")
    public String registerTask(@RequestBody TaskModel task) {
        this.repository.save(task);
        return "Tarefa cadastrada com sucesso";
    }
}
