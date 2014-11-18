package demo.entities;

import org.springframework.hateoas.Identifiable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Comment implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer pk;
    @ManyToOne(fetch= FetchType.LAZY)
    @NotNull
    private Book book;
    @NotNull
    private String message;
    private String email;

    public Comment() {
    }

    @Override
    public Integer getId() {
        return this.getPk();
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

