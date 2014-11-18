package demo.administration;

import demo.entities.Book;
import org.lightadmin.api.config.annotation.Administration;


@Administration(Book.class)
public class BookAdministration {
}
