/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author filipe
 */
@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/pessoas-comum")
    public String pessoasComum() {
        return "pessoas-comum";
    }

    @RequestMapping("buscar-pessoas-por-filme")
    public String buscarPessoasPorFilme(HttpServletRequest request, HttpServletResponse response) {
        return "buscar-pessoas-por-filme";
    }
}
