package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long BOOK_ID = 1L;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @Transactional(readOnly = true)
    void shouldFindExpectedCommentById() {
        val optionalActualComment = commentRepository.findById(FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent();
    }

    @Test
    @Transactional(readOnly = true)
    void shouldReturnCorrectBooksListWithAllInfo() {

       val comments = commentRepository.findByBookId(BOOK_ID);
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getComment().equals(""));
    }

    @Test
    @Transactional
    void testSave() {

        Optional<Book> optionalBook = bookRepository.findById(1L);
        assertThat(optionalBook).isPresent();

        val comment = new Comment(0L, "comment 2", optionalBook.get());

        Comment savedComment = commentRepository.save(comment);

        Optional<Comment> optionalActualComment = commentRepository.findById(savedComment.getId());

        assertThat(optionalActualComment).isPresent();
    }

    @Test
    @Transactional
    void testUpdate() {

        Optional<Comment> optionalComment = commentRepository.findById(1L);
        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();

            String newComment = "new comment";

            comment.setComment(newComment);

            commentRepository.save(comment);

            Optional<Comment> optionalActualComment = commentRepository.findById(comment.getId());

            assertThat(optionalActualComment).isPresent();
            assertThat(optionalActualComment.get().getComment().equals(newComment));

        }
    }

}