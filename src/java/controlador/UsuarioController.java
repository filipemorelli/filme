package controlador;

import negocio.LoginFacebook;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {

    private final LoginFacebook loginFacebook = new LoginFacebook();

    @RequestMapping("/loginfb")
    public String logarComFacebook() {
        System.out.println("on method Logar");
        return "redirect:" + loginFacebook.getLoginRedirectURL();
    }

    @RequestMapping("/loginfbresponse")
    public String respostaFacebook(String code, HttpServletRequest request) throws MalformedURLException, IOException {
        System.out.println("on method respostaFacebook");
        return loginFacebook.obterUsuarioFacebook(code, request);
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // excluir a 
        return "redirect:/login";
    }

}
