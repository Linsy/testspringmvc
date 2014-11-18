package demo.assemblers;

import demo.controllers.BookController;
import demo.entities.Book;
import demo.resources.BookResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by linsy on 30/10/14.
 */
@Component
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    public BookResource toResource(Book book) {
        BookResource bookResource =  createResourceWithId(book.getId(), book);
        bookResource.setPk(book.getPk());
        bookResource.setAuthor(book.getAuthor());
        bookResource.setTitle(book.getTitle());
        return bookResource;
    }

}
