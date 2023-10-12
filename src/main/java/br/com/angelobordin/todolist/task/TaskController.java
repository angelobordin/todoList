package br.com.angelobordin.todolist.task;

import java.net.http.HttpResponse.ResponseInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskRepository repository;

    @PostMapping("/create")
    public ResponseEntity registerTask(@RequestBody TaskModel task, HttpServletRequest req) {
        task.setUserId((UUID) req.getAttribute("userId"));

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getStart_at())) {
            System.out.println(currentDate);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("A data de ínicio não pode ser anterior a data atual!");

        } else if (currentDate.isAfter(task.getEnd_at())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("A data de termino não pode ser anterior a data atual!");

        } else if (task.getStart_at().isAfter(task.getEnd_at())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("A data de término não pode ser anterior a data de ínicio!");

        }

        this.repository.save(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tarefa cadastrada com sucesso!");
    }

    @GetMapping("/list")
    public List<TaskModel> listTask(HttpServletRequest req) {
        var userId = req.getAttribute("userId");
        var taskList = this.repository.findByUserId((UUID) userId);
        return taskList;
    }

    @PutMapping("/update/{id}")
    public void updateTask(@RequestBody TaskModel task, HttpServletRequest req, @PathVariable UUID id) {

    }
}
