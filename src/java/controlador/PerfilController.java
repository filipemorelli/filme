/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.FilmeModel;
import modelo.UsuarioModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author filipe
 */
@Controller
public class PerfilController {

    @RequestMapping("/perfil")
    public String perfil() {
        return "perfil";
    }

    @RequestMapping("/add-filme-ajax")
    public void addFilmeAjax(HttpServletRequest request, HttpServletResponse response) {
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

            //caso usuario nao tenha filmes cria uma lista para ele senao addiciona na lista ja existente
            if (user.getFilmes() == null) {
                ArrayList<FilmeModel> filmes = new ArrayList<>(); //cria uma nova lista
                filmes.add(filme); // adiciona o filme a lista
                user.setFilmes(filmes); //adiciona o filme ao usuario
            } else {
                user.getFilmes().add(filme); //adiciona usuario na lista existente
            }

            user.save(); // salva usuario e os seus dados

            json.accumulate("status", true);
            json.accumulate("msg", "Adiciona objeto");
            out.print(json.toString());
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping("buscar-filmes-perfil")
    public void buscarFilmes(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setCharacterEncoding("UTF-8");

        JSONObject json = new JSONObject();

        try {
            ServletOutputStream out = response.getOutputStream();
            String id_social = (String) request.getSession().getAttribute("id_social");
            UsuarioModel user = UsuarioModel.carregarPorIdSocial(id_social);

            JSONArray js = new JSONArray(user.getFilmes());

            json.accumulate("status", true);
            json.accumulate("msg", js.toString());
            out.print(json.toString());
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping("excluir-filme-perfil")
    public void excluirFilmePerfil(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setCharacterEncoding("UTF-8");

        JSONObject json = new JSONObject();
        int indice = Integer.parseInt(request.getParameter("indice"));

        try {
            ServletOutputStream out = response.getOutputStream();
            String id_social = (String) request.getSession().getAttribute("id_social");
            UsuarioModel user = UsuarioModel.carregarPorIdSocial(id_social);

            if (user.getFilmes().isEmpty()) {
                json.accumulate("status", false);
                json.accumulate("msg", "Erro nenhum filme atribuido!");
                out.print(json.toString());
                out.close();
            } else {
                ArrayList<FilmeModel> filmes = new ArrayList<>(user.getFilmes()); // cria uma lista
                filmes.remove(indice); // remove filme da lista
                user.setFilmes(filmes); //salva a nova lista
                        
                json.accumulate("status", true);
                json.accumulate("msg", "Filme exluido do perfil com sucesso!");
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
