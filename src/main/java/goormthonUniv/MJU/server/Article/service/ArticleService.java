package goormthonUniv.MJU.server.Article.service;

import goormthonUniv.MJU.server.Article.controller.dto.ArticleRequest;
import goormthonUniv.MJU.server.Article.domain.Article;
import goormthonUniv.MJU.server.Article.domain.Image;
import goormthonUniv.MJU.server.Article.exception.ArticleErrorCode;
import goormthonUniv.MJU.server.Article.exception.ArticleException;
import goormthonUniv.MJU.server.Article.repository.ArticleRepository;
import goormthonUniv.MJU.server.Article.util.FileStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final FileStore fileStore;

    public void createArticle(Long memberId, ArticleRequest request, List<MultipartFile> imageFiles) {
        Article article = new Article(memberId, request.title(), request.content());
        articleRepository.save(article);

        if(imageFiles != null) {
            for (MultipartFile file : imageFiles) {
                String storedPath = fileStore.store(file);
                Image image = new Image(file.getOriginalFilename(), storedPath, article);
                article.addImage(image);
            }
        }
    }

    @Transactional(readOnly = true)
    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.NOT_FOUND_ARTICLE));
    }

    @Transactional(readOnly = true)
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }
}
