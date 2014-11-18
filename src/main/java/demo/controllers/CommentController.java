package demo.controllers;

import demo.assemblers.CommentResourceAssembler;
import demo.entities.Book;
import demo.entities.Comment;
import demo.repositories.BookRepository;
import demo.repositories.CommentRepository;
import demo.resources.CommentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@ExposesResourceFor(Comment.class)
@RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityLinks entityLinks;
    @Autowired
    private CommentResourceAssembler commentResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<CommentResource>> list(@
// AuthenticationPrincipal User user) {
    public ResponseEntity<List<CommentResource>> list() {
        Page<Comment> comments =  commentRepository.findAll(new PageRequest(0,20));

        ArrayList<CommentResource> commentResourcePage = new ArrayList<>();
        for (Comment comment: comments){
            CommentResource commentResource = commentResourceAssembler.toResource(comment);
            commentResource.add(entityLinks.linkToSingleResource(Book.class, comment.getBook().getId()).withRel("book"));
            commentResourcePage.add(commentResource);
        }
        return new ResponseEntity<List<CommentResource>>(commentResourcePage, HttpStatus.OK);
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<CommentResource> detail(@PathVariable Integer pk){
        Comment comment = commentRepository.findOne(pk);
        CommentResource commentResource = commentResourceAssembler.toResource(comment);
        //commentDTO.add(linkTo(BookController.class).slash(comment.getBook()).withRel("book"));
        commentResource.add(entityLinks.linkToSingleResource(Book.class, comment.getBook().getId()).withRel("book"));

        return new ResponseEntity<CommentResource>(commentResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommentResource> create(@RequestBody Comment body){
        if(body.getBook() != null) {
            Book book = bookRepository.findOne(body.getBook().getPk());
            body.setBook(book);
        }

        Comment comment = commentRepository.save(body);
        CommentResource commentResource = commentResourceAssembler.toResource(comment);
        commentResource.add(entityLinks.linkToSingleResource(Book.class, comment.getBook().getId()).withRel("book"));
        return new ResponseEntity<CommentResource>(commentResource, HttpStatus.CREATED);
    }

}


