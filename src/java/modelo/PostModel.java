package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.Query;

@Entity(name = "post")
public class PostModel implements Serializable {

    public static PostModel carregaPost(Long idPost) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select p from post as p where p.id = :idPost");
        query.setParameter("idPost", idPost);
        HibernateUtil.getSession().getTransaction().commit();
        return (PostModel) query.list().get(0);
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private FilmeModel filme;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_comentario",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "comentario_id"))
    private Collection<ComentarioModel> comentario;

    @ManyToOne
    private UsuarioModel user;

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

    public FilmeModel getFilme() {
        return filme;
    }

    public void setFilme(FilmeModel filme) {
        this.filme = filme;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<ComentarioModel> getComentario() {
        return comentario;
    }

    public void setComentario(Collection<ComentarioModel> comentario) {
        this.comentario = comentario;
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

    public UsuarioModel getUser() {
        return user;
    }

    public void setUser(UsuarioModel user) {
        this.user = user;
    }

    public static List<PostModel> carregaPostsUsuario(String id_social) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select p from post p where p.user.id_social = :id_social");
        query.setParameter("id_social", id_social);
        HibernateUtil.getSession().getTransaction().commit();
        return query.list();
    }

    public static List<PostModel> carregaTodosPosts() {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select p from post p ORDER BY p.modified DESC");
        HibernateUtil.getSession().getTransaction().commit();
        return query.list();
    }

}
