package demo.administration;


import demo.entities.Comment;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.annotation.Administration;
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FiltersConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.utils.FieldValueRenderer;

//@Administration(Comment.class)
public class CommentAdministration extends AdministrationConfiguration<Comment>{
    public FieldSetConfigurationUnit listView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field( "message" ).caption( "Message" )
                .dynamic( "book.title" ).caption( "Book title" )
                .field( "email" ).caption( "Email" ).build();
    }

    public FieldSetConfigurationUnit quickView( final FieldSetConfigurationUnitBuilder fragmentBuilder ) {
        return fragmentBuilder
                .field("message").caption( "Message" )
                .renderable(bookTitleRenderer()).caption( "Book title" )
                .field("email").caption( "Email" ).build();
    }

    public static FieldValueRenderer bookTitleRenderer() {
        return new FieldValueRenderer() {
            @Override
            public String apply(Object comment) {
                return ((Comment)comment).getBook().getTitle();
            }
        };
    }

    public FiltersConfigurationUnit filters( final FiltersConfigurationUnitBuilder filterBuilder ) {
        return filterBuilder.filter("Book", "book").build();
    }
}
