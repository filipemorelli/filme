/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.Query;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Filipe
 */
@Entity(name = "usuario")
public class UsuarioModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty(message = "Digite o nome")
    private String nome;

    @NotNull
    @NotEmpty(message = "Selecione o sexo")
    @Column(length = 10)
    private String sexo;

    @Column(columnDefinition = "TEXT")
    private String url_imagem;

    @NotNull
    @NotEmpty(message = "Digite o email corretamente")
    private String email;
    private String id_social;

    @Column(columnDefinition = "TEXT")
    private String url_social;

    @ManyToMany
    private Collection<FilmeModel> filmes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Calendar created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Calendar modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUrl_imagem() {
        return url_imagem;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_social() {
        return id_social;
    }

    public void setId_social(String id_social) {
        this.id_social = id_social;
    }

    public String getUrl_social() {
        return url_social;
    }

    public void setUrl_social(String url_social) {
        this.url_social = url_social;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + " , Sexo " + this.sexo;
    }

    public void save() {
        HibernateUtil.getSession().beginTransaction();
        HibernateUtil.getSession().save(this);
        HibernateUtil.getSession().getTransaction().commit();
    }

    public void update() {
        HibernateUtil.getSession().beginTransaction();
        HibernateUtil.getSession().update(this);
        HibernateUtil.getSession().getTransaction().commit();
    }

    public void saveOrUpdate() {
        HibernateUtil.getSession().beginTransaction();
        HibernateUtil.getSession().saveOrUpdate(this);
        HibernateUtil.getSession().getTransaction().commit();
    }

    public void delete() {
        HibernateUtil.getSession().beginTransaction();
        HibernateUtil.getSession().delete(this);
        HibernateUtil.getSession().getTransaction().commit();
    }

    public UsuarioModel load(long id) {
        HibernateUtil.getSession().beginTransaction();
        UsuarioModel userModel = (UsuarioModel) HibernateUtil.getSession().load(UsuarioModel.class, id);
        HibernateUtil.getSession().getTransaction().commit();
        return userModel;
    }

    public static List<UsuarioModel> loadAll() {
        HibernateUtil.getSession().beginTransaction();
        List<UsuarioModel> lista = HibernateUtil.getSession().createQuery("select u from usuario as u").list();
        HibernateUtil.getSession().getTransaction().commit();
        return lista;
    }

    public static UsuarioModel carregarPorIdSocial(String social_id) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select u from usuario as u where u.id_social = :paramIdSocial");
        query.setParameter("paramIdSocial", social_id);
        HibernateUtil.getSession().getTransaction().commit();
        return (UsuarioModel) query.list().get(0);
    }

    public static boolean existUser(String social_id) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select u from usuario as u where u.id_social = :paramIdSocial");
        query.setParameter("paramIdSocial", social_id);
        Boolean existUser = query.list().isEmpty();

        HibernateUtil.getSession().getTransaction().commit();

        return !existUser;
    }

    public static List<UsuarioModel> usuariosEmComumComFilme(String id_api, String id_user_social) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("SELECT u FROM usuario u JOIN u.filmes f WHERE f.id_api = :id AND u.id_social != :idUser");
        query.setParameter("id", id_api);
        query.setParameter("idUser", id_user_social);
        HibernateUtil.getSession().getTransaction().commit();
        return query.list();
    }

    public static List<UsuarioModel> usuariosEmComum(String id_social) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("SELECT u FROM usuario u JOIN u.filmes f WHERE u.id_social != :id AND f.id != :id AND f.id IN ( SELECT f1.id from usuario u1 JOIN u1.filmes f1 where u1.id_social = :id)");
        query.setParameter("id", id_social);
        HibernateUtil.getSession().getTransaction().commit();
        return query.list();
    }

    private static Long idSocialToID(String idSocial) {
        UsuarioModel u = carregarPorIdSocial(idSocial);
        return u.getId();
    }

    public Collection<FilmeModel> getFilmes() {
        return filmes;
    }

    public void setFilmes(Collection<FilmeModel> filmes) {
        this.filmes = filmes;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getModified() {
        return modified;
    }

    public void setModified(Calendar modified) {
        this.modified = modified;
    }
}
