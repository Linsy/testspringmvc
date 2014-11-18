package demo.repositories;

import demo.entities.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by linsy on 29/10/14.
 */
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
}
