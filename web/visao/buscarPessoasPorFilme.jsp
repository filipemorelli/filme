<%@page import="java.util.List"%>
<%@page import="modelo.UsuarioModel"%>
<%@page import="java.util.Enumeration"%>
<%
    String id_api = request.getParameter("id");
    String titulo = request.getParameter("titulo");
    String id_social = (String) session.getAttribute("id_social");
    List<UsuarioModel> usuariosCurtemFilme = UsuarioModel.usuariosEmComumComFilme(id_api, id_social);

    if (usuariosCurtemFilme == null || !usuariosCurtemFilme.isEmpty()) {
        for (UsuarioModel usuario : usuariosCurtemFilme) {
%>
<div class="panel panel-default resultado-pessoas">
    <div class="panel-body">
        <p class="lead"><img src="<%= usuario.getUrl_imagem()%>" class="img-circle pull-left"> <a href="<%= usuario.getUrl_social()%>" target="_blank"><%= usuario.getNome()%></a></p>
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