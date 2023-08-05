package com.example.board.repository;

import com.example.board.config.JpaConfig;
import com.example.board.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;


    @DisplayName("Select 테스트")
    @Test
    void testSelectFunction() {

        // given

        // when
        List<Article> articles = articleRepository.findAll();


        // then
        assertThat(articles)
                .isNotNull()
                .hasSize(0);

    }

    @DisplayName("Insert 테스트")
    @Test
    void testInsertFunction() {
        // given
        long count = articleRepository.count();

        // when
        Article article = articleRepository.save(Article.of("test", "test", "test"));

        // then
        assertThat(articleRepository.count()).isEqualTo(1);

    }

    @DisplayName("Update 테스트")
    @Test
    void testUpdateFunction() {
        // given
        Article article = articleRepository.saveAndFlush(Article.of("test", "test", "test"));

        // when
        article.setHashtag("test2");

        Article savedArticle = articleRepository.saveAndFlush(article);

        // then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag","test2");
    }

    @DisplayName("Delete 테스트")
    @Test
    void testDelteFunction() {
        // given
        Article article = articleRepository.saveAndFlush(Article.of("test", "test", "test"));

        // when
        List<Article> articles = articleRepository.findAll();

        articleRepository.delete(articles.get(0));


        // then
        assertThat(articleRepository.findAll().size()).isEqualTo(0);
    }
}