package demo.resources;

import org.springframework.hateoas.ResourceSupport;

public class CommentResource extends ResourceSupport {
    private Integer pk;
    private String comment;
    private String email;

    public CommentResource() {
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
