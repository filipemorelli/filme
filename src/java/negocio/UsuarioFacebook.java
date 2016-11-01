
package negocio;

import javax.servlet.http.HttpServletRequest;
import modelo.UsuarioModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;

@Scope("session")
public class UsuarioFacebook {

    private Long id;
    private String firstName;
    private Integer timezone;
    private String email;
    private Boolean verified;
    private String middleName;
    private String gender;
    private String lastName;
    private String link;
    private String locale;
    private String name;
    private String updatedTime;

    public UsuarioFacebook(JSONObject jsonUsuario, HttpServletRequest request) throws JSONException {
        UsuarioModel user;
        if (UsuarioModel.existUser(jsonUsuario.getString("id"))) {
            //update
            user = UsuarioModel.carregarPorIdSocial(jsonUsuario.getString("id"));
        } else {
            user = new UsuarioModel();
            user.setId_social(jsonUsuario.getString("id"));
        }
        user.setNome(jsonUsuario.getString("name"));
        user.setSexo(jsonUsuario.getString("gender"));
        user.setEmail(jsonUsuario.getString("email"));
        user.setUrl_imagem(jsonUsuario.getJSONObject("picture").getJSONObject("data").getString("url"));
        user.setUrl_social(jsonUsuario.getString("link"));
        user.saveOrUpdate();

        //adiciona a sessao
        request.getSession().setAttribute("id_social", user.getId_social());
        request.getSession().setAttribute("url_social", user.getUrl_social());
        request.getSession().setAttribute("name", user.getNome());
        request.getSession().setAttribute("gender", user.getSexo());
        request.getSession().setAttribute("email", user.getEmail());
        request.getSession().setAttribute("picture", user.getUrl_imagem());
    }

    @Override
    public String toString() {
        return "UsuarioFacebook [id=" + id + ", firstName=" + firstName
                + ", timezone=" + timezone + ", email=" + email + ", verified="
                + verified + ", middleName=" + middleName + ", gender="
                + gender + ", lastName=" + lastName + ", link=" + link
                + ", locale=" + locale + ", name=" + name + ", updatedTime="
                + updatedTime + "]";
    }

}
