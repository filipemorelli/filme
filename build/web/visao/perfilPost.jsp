<%@page import="modelo.UsuarioModel"%>
<%@page import="java.util.List"%>
<%@page import="modelo.PostModel"%>
<%@page import="modelo.ComentarioModel"%>
<%@page import="java.util.ArrayList"%>
<%
    String comentarioContent = "<li class=\"list-group-item\">"
            + "<a href=\"[post-user-link-social]\" target=\"_blank\" class=\"content-image\">"
            + "<img class=\"profile-comment img-rounded\" src=\"[post-user-img]\">"
            + "</a>"
            + "[post-comentario-texto]"
            + "</li>";
    String postAcao = "<div class=\"action-dropdown\">"
            + "<div class=\"dropdown\">"
            + "<a href=\"#\" class=\"action-post dropdown-toggle\" data-toggle=\"dropdown\">Ações <i class=\"fa fa-caret-down\"></i></a>"
            + "<ul class=\"dropdown-menu\">"
            + "<li><a href=\"#\" class=\"exclui-post\"><i class=\"fa fa-trash\"></i> Excluir</a></li>"
            + "</ul>"
            + "</div>"
            + "</div>";
    String postConteudo = "<div class=\"panel panel-default post-content\" data-id=\"[post-id]\">"
            + "<div class=\"panel-thumbnail\"><img src=\"https://image.tmdb.org/t/p/w640[post-img]\" class=\"img-post\"></div>"
            + "<div class=\"panel-heading\">"
            + "<!--[post-acao]-->"
            + "<h4>[post-titulo]</h4>"
            + "</div>"
            + "<div class=\"panel-body\">"
            + "<p>"
            + "[post-descricao]"
            + "</p>"
            + "<hr>"
            + "<ul class=\"list-group comment-list\">"
            + "<!--[comentarios]-->"
            + "</ul>"
            + "<hr>"
            + "<form class=\"form-comentario\">"
            + "<div class=\"input-group\">"
            + "<div class=\"input-group-btn\">"
            + "<span class=\"btn btn-default\">+1</span>"
            + "</div>"
            + "<input type=\"text\" class=\"form-control\" placeholder=\"Comentar..\">"
            + "<span class=\"input-group-btn\">"
            + "<button class=\"btn btn-primary btn-add-comentario\" type=\"submit\">Comment</button>"
            + "</span>"
            + "</div>"
            + "</form>"
            + "</div>"
            + "</div>";

    UsuarioModel userMain = UsuarioModel.carregarPorIdSocial((String) session.getAttribute("id_social")); //busca o usuario
    List<PostModel> posts = PostModel.carregaPostsUsuario((String) session.getAttribute("id_social"));

    if (posts.isEmpty()) {
        out.println("<p class=\"lead center\">Sem Posts <i class=\"fa fa-frown-o\"></i></p>");
    } else {
        for (int i = 0; i < posts.size(); i++) {
            String postCompleto = postConteudo;
            postCompleto = postCompleto.replace("[post-id]", String.valueOf(posts.get(i).getId()));
            postCompleto = postCompleto.replace("[post-descricao]", posts.get(i).getDescricao());
            postCompleto = postCompleto.replace("[post-titulo]", posts.get(i).getFilme().getTitulo());
            postCompleto = postCompleto.replace("[post-img]", posts.get(i).getFilme().getUrl_poster());

            if (userMain.getId_social() == posts.get(i).getUser().getId_social()) {
                postCompleto = postCompleto.replace("<!--[post-acao]-->", postAcao);
            }

            if (posts.get(i).getComentario() == null || posts.get(i).getComentario().isEmpty()) {
                postCompleto = postCompleto.replace("<!--[comentarios]-->", "<li class=\"list-group-item text-primary\">Sem Comentarios, seja o Primeiro!</li>");
            } else {
                ArrayList<ComentarioModel> comentariosPost = new ArrayList<ComentarioModel>(posts.get(i).getComentario());
                for (int j = 0; j < comentariosPost.size(); j++) {
                    String comentarioCompleto = comentarioContent;
                    comentarioCompleto = comentarioCompleto.replace("[post-user-img]", comentariosPost.get(j).getUser().getUrl_imagem());
                    comentarioCompleto = comentarioCompleto.replace("[post-comentario-texto]", comentariosPost.get(j).getDescricao());
                    comentarioCompleto = comentarioCompleto.replace("[post-user-link-social]", comentariosPost.get(j).getUser().getUrl_social());

                    //acertar esse add comentarios
                    comentarioCompleto += "<!--[comentarios]-->";
                    postCompleto = postCompleto.replace("<!--[comentarios]-->", comentarioCompleto);
                }
            }

            out.println(postCompleto);
        }
    }

%>