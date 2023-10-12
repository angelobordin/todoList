package br.com.angelobordin.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DEFINE A CLASSE COMO REST(API);
// DEFINE O PATH PARA EXECUTAR A CLASSE;
@RestController
@RequestMapping("/user")
public class UserController {

    // SPRING VAI GERENCIAR O CICLO DE VIDA DA PROPRIEDADE;
    @Autowired
    private IUserRepository repository;
    
    // DEFINE O METODO HTTPE SUB-ROTA DE ACESSO AO MÉTODO DA CLASSE;
    @PostMapping("/create")
    public String RegisterUser(@RequestBody UserModel user) {
        this.repository.save(user);
        return "Usuário criado com sucesso";
    }
}
 