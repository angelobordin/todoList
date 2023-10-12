package br.com.angelobordin.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ANNOTATION QUE DEFINE A CLASSE COMO REST(API);
// ANNOTATION QUE DEFINE O PATH PARA EXECUTAR A CLASSE;
@RestController
@RequestMapping("/user")
public class UserController {
    
    // ANNOTATION QUE DEFINE O METODO HTTPE SUB-ROTA DE ACESSO AO MÉTODO DA CLASSE;
    @PostMapping("/create")
    public void RegisterUser(@RequestBody UserModel user) {
        System.out.println(user.getName());
    }
}
 