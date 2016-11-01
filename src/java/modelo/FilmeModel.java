package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.Query;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "filme")
public class FilmeModel implements Serializable {

    public static boolean hasFilme(String id_api) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select f from filme f where f.id_api = :id_api");
        query.setParameter("id_api", id_api);
        Boolean existUser = query.list().isEmpty();

        HibernateUtil.getSession().getTransaction().commit();

        return !existUser;
    }

    public static FilmeModel getFilmePorIdAPI(String id_api) {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("select f from filme as f where f.id_api = :id_api");
        query.setParameter("id_api", id_api);
        HibernateUtil.getSession().getTransaction().commit();
        return (FilmeModel) query.list().get(0);
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty(message = "Digite o titulo")
    private String titulo;

    @NotNull
    @NotEmpty(message = "Digite o titulo original")
    private String titulo_original;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String url_poster;

    @NotNull
    @NotEmpty(message = "Digite a lingua do filme")
    @Column(length = 5)
    private String lingua;

    @Temporal(TemporalType.DATE)
    private Calendar data_lancameto;

    private String id_api;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Calendar created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Calendar modified;

    @ManyToMany
    @JoinTable(name = "filme_genero",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id"))
    private Collection<GeneroModel> generos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo_original() {
        return titulo_original;
    }

    public void setTitulo_original(String titulo_original) {
        this.titulo_original = titulo_original;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl_poster() {
        return url_poster;
    }

    public void setUrl_poster(String url_poster) {
        this.url_poster = url_poster;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public Calendar getData_lancameto() {
        return data_lancameto;
    }

    public void setData_lancameto(Calendar data_lancameto) {
        this.data_lancameto = data_lancameto;
    }

    public String getId_api() {
        return id_api;
    }

    public void setId_api(String id_api) {
        this.id_api = id_api;
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

    public Collection<GeneroModel> getGeneros() {
        return generos;
    }

    public void setGeneros(Collection<GeneroModel> generos) {
        this.generos = generos;
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

}
