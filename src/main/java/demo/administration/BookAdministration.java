package demo.administration;

import demo.entities.Book;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FiltersConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.PersistentFieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;


public class BookAdministration extends AdministrationConfiguration<Book> {
// Simple changes
//    public EntityMetadataConfigurationUnit configuration( EntityMetadataConfigurationUnitBuilder configurationBuilder ) {
//        return configurationBuilder.nameField( "author" ).pluralName("Books").build();
//    }

    public EntityMetadataConfigurationUnit configuration( EntityMetadataConfigurationUnitBuilder configurationBuilder ) {
        return configurationBuilder.nameExtractor( bookExtractor() ).build();
    }





//    public FieldSetConfigurationUnit formView( final PersistentFieldSetConfigurationUnitBuilder fragmentBuilder ) {
//        return fragmentBuilder
//                .field( "author" ).caption( "Author" )
//                .field( "title" ).caption( "Title" ).build();
//    }

    /*
    public FieldSetConfigurationUnit listView( FieldSetConfigurationUnitBuilder fragmentBuilder ) {
        return fragmentBuilder
                .field( "name" ).caption( "Name" )
                .field( "users" ).caption( "Customers" ).build();
    }

    public FieldSetConfigurationUnit quickView( FieldSetConfigurationUnitBuilder fragmentBuilder ) {
        return fragmentBuilder
                .field( "name" ).caption( "Name" )
                .field( "users" ).caption( "Customers" ).build();
    }

    public FieldSetConfigurationUnit showView( final FieldSetConfigurationUnitBuilder fragmentBuilder ) {
        return fragmentBuilder
                .field( "name" ).caption( "Name" )
                .field( "users" ).caption( "Customers" ).build();
    }

    public FieldSetConfigurationUnit formView( final PersistentFieldSetConfigurationUnitBuilder fragmentBuilder ) {
        return fragmentBuilder
                .field( "name" ).caption( "Name" )
                .field( "users" ).caption( "Customers" ).build();
    }

    public FiltersConfigurationUnit filters( final FiltersConfigurationUnitBuilder filterBuilder ) {
        return filterBuilder
                .filter( "Name", "name" )
                .filter( "Customers", "users" ).build();
    }*/




    private static EntityNameExtractor<Book> bookExtractor() {
        return new EntityNameExtractor<Book>() {
            @Override
            public String apply( final Book book ) {
                return String.format("%s-%s", book.getId(), book.getTitle());
            }
        };
    }


}
