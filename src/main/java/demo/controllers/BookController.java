package demo.controllers;


import demo.assemblers.BookResourceAssembler;
import demo.assemblers.CommentResourceAssembler;
import demo.entities.Book;
import demo.entities.Comment;
import demo.repositories.BookRepository;
import demo.resources.BookResource;
import demo.resources.CommentResource;
import demo.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/books")
@ExposesResourceFor(Book.class)
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityLinks entityLinks;
    @Autowired
    private BookValidator bookValidator;
    @Autowired
    private ResourceBundleMessageSource messageResource;

//    @InitBinder
//    private void initBinder(WebDataBinder binder){
//        binder.setValidator(bookValidator);
//    }


    @RequestMapping(value = "/{pk}/comments", method = RequestMethod.GET)
    public ResponseEntity<List<CommentResource>> getCommentsForBook(@PathVariable("pk") Integer pk) {
        ArrayList<CommentResource> comments = new ArrayList<>();
        CommentResourceAssembler assembler = new CommentResourceAssembler();
        Book book = bookRepository.findOne(pk);
        for (Comment comment : book.getComments()) {
            comments.add(assembler.toResource(comment));
        }
        return new ResponseEntity<List<CommentResource>>(comments, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BookResource> create(@RequestBody Book body, BindingResult result) {
        BookValidator bookValidator = new BookValidator();
        bookValidator.validate(body, result);
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                String field = fieldError.getField();
                String msg = fieldError.toString();
                //String msg2 = messageResource.getMessage(fieldError, Locale.getDefault());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BookResourceAssembler assembler = new BookResourceAssembler();
        Book book = new Book();
        book.setAuthor(body.getAuthor());
        book.setTitle(body.getTitle());
        book = bookRepository.save(book);
        BookResource bookResource = assembler.toResource(book);
        return new ResponseEntity<BookResource>(bookResource, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookResource>> getBooks(@RequestParam(value = "page",required=false, defaultValue = "0") Integer page,
                               @RequestParam(value="page_size",required=false, defaultValue = "20") Integer pageSize) {

        BookResourceAssembler assembler = new BookResourceAssembler();
        Page<Book> books =  bookRepository.findAll(new PageRequest(page,pageSize));
        ArrayList<BookResource> bookResourcePage = new ArrayList<>();

        for (Book book: books){

            BookResource bookResource = assembler.toResource(book);
            bookResource.add(linkTo(methodOn(BookController.class).getCommentsForBook(book.getId())).withRel("comments"));

            bookResourcePage.add(bookResource);
        }
        return new ResponseEntity<List<BookResource>>(bookResourcePage, HttpStatus.OK);
    }

}


