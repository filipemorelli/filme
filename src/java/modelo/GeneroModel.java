package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "genero")
public class GeneroModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private int id_api;

    private String nome;

    public Long getId() {
        return id;
    }

    public int getId_api() {
        return id_api;
    }

    public String getNome() {
        return nome;
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
