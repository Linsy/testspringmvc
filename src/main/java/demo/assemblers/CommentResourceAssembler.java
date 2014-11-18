package demo.assemblers;

import demo.controllers.CommentController;
import demo.entities.Comment;
import demo.resources.CommentResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CommentResourceAssembler extends ResourceAssemblerSupport<Comment, CommentResource> {


    public CommentResourceAssembler() {
        super(CommentController.class, CommentResource.class);
    }

    @Override
    public CommentResource toResource(Comment comment) {
        CommentResource commentResource = createResourceWithId(comment.getId(), comment);
        commentResource.setComment(comment.getMessage());
        commentResource.setPk(comment.getPk());
        commentResource.setEmail(comment.getEmail());
//        commentResource.add(linkTo(BookController.class).slash(comment.getBook().getId()).withRel("book"));
        return commentResource;
    }
}
