package demo.resources;

import org.springframework.hateoas.ResourceSupport;


public class BookResource extends ResourceSupport {
    private Integer pk;
    private String author;
    private String title;

    public BookResource() {
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
