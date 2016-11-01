<%@page import="modelo.FilmeModel"%>
<%@page import="java.util.List"%>
<%@page import="modelo.UsuarioModel"%>
<%@page import="java.util.Enumeration"%>
<%
    String id_social = (String) session.getAttribute("id_social");
    List<UsuarioModel> usuariosCurtemFilme = UsuarioModel.usuariosEmComum(id_social);

    if (usuariosCurtemFilme == null || !usuariosCurtemFilme.isEmpty()) {
        for (UsuarioModel usuario : usuariosCurtemFilme) {
%>
            <div class="panel panel-default resultado-pessoas">
                <div class="panel-body">
                    <p class="lead"><img src="<%= usuario.getUrl_imagem() %>" class="img-circle pull-left"> <a href="<%= usuario.getUrl_social() %>" target="_blank"><%= usuario.getNome()%></a></p>
                    <small>
                        <%
                            String filmesEmTexto = "";
                            for(FilmeModel f: usuario.getFilmes()){
                                filmesEmTexto += f.getTitulo() + ", ";
                            }
                            out.print(filmesEmTexto.substring(0, filmesEmTexto.length() - 2));
                        %>
                    </small>
                </div>
            </div>
<%
        }
    } else {
%>
        <div class="panel panel-default resultado-pessoas">
            <div class="panel-body">
                <p class="lead center">Nenhum pessoa encontrada!</p>
            </div>
        </div>
<%
    }

%>