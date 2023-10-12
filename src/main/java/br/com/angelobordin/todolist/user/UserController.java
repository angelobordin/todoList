package br.com.angelobordin.todolist.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

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
    public ResponseEntity<String> RegisterUser(@RequestBody UserModel user) {
        var userExists = this.repository.findByUsername(user.getUsername());
        
        if (userExists != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado");
        }

        var passHash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passHash);

        this.repository.save(user); 
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuário criado com sucesso");
    }
}
 