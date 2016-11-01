/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ComentarioModel;
import modelo.FilmeModel;
import modelo.PostModel;
import modelo.UsuarioModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author filipe
 */
@Controller
public class ComentarioController {
    
    @RequestMapping("novo-comentario")
    public void addComentario(HttpServletRequest request, HttpServletResponse response) {
        //Colocando configuracoes
        response.setContentType("text/json");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setCharacterEncoding("UTF-8");
        
        JSONObject json = new JSONObject(); //cria objeto json

        try {
            ServletOutputStream out = response.getOutputStream();
            String id_social = (String) request.getSession().getAttribute("id_social"); //pega id social na sessao
            UsuarioModel user = UsuarioModel.carregarPorIdSocial(id_social); //busca o usuario pelo id social
            PostModel post = PostModel.carregaPost(Long.parseLong(request.getParameter("idPost"))); //busca o post que ira inserir o comentario
            String comentario = request.getParameter("comentario"); //comentario digitado pelo usuario

            //criar novo Comentario
            ComentarioModel comment = new ComentarioModel();
            comment.setUser(user);
            comment.setDescricao(comentario);
            comment.save();

            //caso usuario nao tenha filmes cria uma lista para ele senao addiciona na lista ja existente
            if (post.getComentario() == null) {
                ArrayList<ComentarioModel> comments = new ArrayList<>(); //cria uma nova lista
                comments.add(comment); // adiciona o filme a lista
                post.setComentario(comments); //adiciona comentario aos posts
            } else {
                post.getComentario().add(comment); //adiciona comentario na lista de post
            }
            
            post.save();
            
            json.accumulate("status", true);
            json.accumulate("msg", "Comentario Adicionado com sucesso");
            out.print(json.toString());
            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
