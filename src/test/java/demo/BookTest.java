package demo;

import demo.entities.Book;
import demo.entities.Comment;
import demo.repositories.BookRepository;
import demo.repositories.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BookTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    private MockMvc mockMvc;
    private Book book;
    private Comment comment;
    private Comment comment2;
//    private User user;
//    private User otherUser;

//    private void login(User user) {
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

//        this.user = new User("username", "password", new ArrayList<GrantedAuthority>());
//        this.otherUser = new User("other", "password", new ArrayList<GrantedAuthority>());

        this.book  = new Book();
        this.book.setAuthor("Linsy");
        this.book.setTitle("boek1");
        this.book = this.bookRepository.save(this.book);

        this.comment = new Comment();
        this.comment.setBook(this.book);
        this.comment.setMessage("comment op boek1 door user");
//        this.comment.setUser(this.user);
        this.commentRepository.save(this.comment);

        this.comment2 = new Comment();
        this.comment2.setBook(this.book);
        this.comment2.setMessage("comment2 op boek1 door otherUser");
//        this.comment2.setUser(this.otherUser);
        this.commentRepository.save(this.comment2);




    }


    @Test
    public void testPostComment() throws Exception {
        // get link of book
        String pathToBook = "http://localhost/books/10";


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comments");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content("{\"book\": {\"pk\": 10}, \"comment\": \"testpost\", \"email\": \"linsy@mail.com\"}");


        ResultActions result = mockMvc.perform(requestBuilder);
        String content = result.andReturn().getResponse().getContentAsString();
        result.andExpect(status().is(201));

    }

    @Test
    public void testPostBook() throws Exception {
        // get link of book
        String pathToBook = "http://localhost/books/10";


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
//        requestBuilder.content("{\"title\": \"postboek\", \"author\": \"postauthor\"}");
        requestBuilder.content("{\"title\": \"postboek\"}");
        ResultActions result = mockMvc.perform(requestBuilder);




        String content = result.andReturn().getResponse().getContentAsString();
        result.andExpect(status().is(201));

    }


//    @Test
//    public void testCommentsForUser() throws Exception {
//        login(this.user);
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comments");
//        requestBuilder.contentType(MediaType.APPLICATION_JSON);
//        ResultActions result = mockMvc.perform(requestBuilder);
//        String content = result.andReturn().getResponse().getContentAsString();
//        result.andExpect(status().is(200));
//    }

    @Test
    public void testList() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(requestBuilder);
        String content = result.andReturn().getResponse().getContentAsString();
        result.andExpect(status().is(200));

    }

    @Test
    public void testComment() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comments/" + this.comment.getPk());
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(requestBuilder);
        String content = result.andReturn().getResponse().getContentAsString();
        result.andExpect(status().is(200));
    }

    @Test
    public void testCommentsForBook() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/" + this.book.getPk() + "/comments");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(requestBuilder);
        String content = result.andReturn().getResponse().getContentAsString();
        result.andExpect(status().is(200));
    }
}
