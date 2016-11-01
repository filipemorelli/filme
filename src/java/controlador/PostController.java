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
public class PostController {

    @RequestMapping("novo-post")
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
            FilmeModel filme; // cria a variavel filme

            //se existe filme cadastrado
            if (FilmeModel.hasFilme((String) request.getParameter("id"))) {
                filme = FilmeModel.getFilmePorIdAPI((String) request.getParameter("id"));
            } else {
                //criar novo filme
                filme = new FilmeModel();
                filme.setId_api((String) request.getParameter("id"));
                filme.setLingua((String) request.getParameter("idioma"));
                filme.setTitulo_original((String) request.getParameter("titulo_original"));
                filme.setTitulo((String) request.getParameter("titulo"));
                filme.setUrl_poster((String) request.getParameter("poster"));
                filme.setDescricao((String) request.getParameter("descricao"));
                filme.save();
            }

            PostModel post = new PostModel();
            post.setFilme(filme);
            post.setUser(user);
            post.setDescricao(request.getParameter("postDescricao"));
            post.save();

            json.accumulate("status", true);
            json.accumulate("msg", "Post Adicionado com sucesso");
            out.print(json.toString());
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping("excluir-post")
    public void excluirComentario(HttpServletRequest request, HttpServletResponse response) {
        //Colocando configuracoes
        response.setContentType("text/json");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setCharacterEncoding("UTF-8");

        JSONObject json = new JSONObject(); //cria objeto json

        try {
            ServletOutputStream out = response.getOutputStream();
            String id_social = (String) request.getSession().getAttribute("id_social"); //pega id social na sessao

            UsuarioModel user = UsuarioModel.carregarPorIdSocial(id_social); //busca o usuario pelo id social
            PostModel post = PostModel.carregaPost(Long.parseLong(request.getParameter("idPost")));

            if (post.getUser().getId() == user.getId()) {
                //pode excluir
                
                post.delete();
                
                json.accumulate("status", true);
                json.accumulate("msg", "Post excluido com sucesso!");
                out.print(json.toString());
                out.close();
            } else {
                json.accumulate("status", false);
                json.accumulate("msg", "Não pode excluir post de outro usuario");
                out.print(json.toString());
                out.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
