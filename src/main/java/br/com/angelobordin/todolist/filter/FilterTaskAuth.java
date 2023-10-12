package br.com.angelobordin.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.angelobordin.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain cb)
            throws ServletException, IOException {

        var routePath = req.getServletPath();
        if (routePath.startsWith("/task")) {
            var authCrypted = req.getHeader("Authorization").substring("Basic".length()).trim();
            var authDecrypted = Base64.getDecoder().decode(authCrypted);
            var auth = new String(authDecrypted);

            String username = auth.split(":")[0];
            String password = auth.split(":")[1];

            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                res.sendError(401, "Usuário sem autorização!");

            } else {
                var passVerified = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passVerified.verified) {
                    req.setAttribute("userId", user.getId());
                    cb.doFilter(req, res);
                } else {
                    res.sendError(401, "Credenciais incorretas!");

                }
            }

        } else {
            cb.doFilter(req, res);
        }

    }

}
