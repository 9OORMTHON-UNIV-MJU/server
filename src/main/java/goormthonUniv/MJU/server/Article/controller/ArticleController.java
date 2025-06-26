package goormthonUniv.MJU.server.Article.controller;

import goormthonUniv.MJU.server.Article.controller.dto.ArticleRequest;
import goormthonUniv.MJU.server.Article.controller.dto.ArticleResponse;
import goormthonUniv.MJU.server.Article.controller.dto.ArticleResponses;
import goormthonUniv.MJU.server.Article.domain.Article;
import goormthonUniv.MJU.server.Article.domain.Image;
import goormthonUniv.MJU.server.Article.service.ArticleService;
import goormthonUniv.MJU.server.global.resolver.annotation.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Article", description = "게시글 관련 API")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(
            summary = "게시글 생성",
            description = "게시글을 작성하고, 선택적으로 이미지 파일들을 업로드합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/articles")
    public ResponseEntity<Void> createArticle(
            @Parameter(description = "게시글 본문 데이터", required = true)
            @RequestPart("article") ArticleRequest request,

            @Parameter(description = "업로드할 이미지 파일들 (선택)")
            @RequestPart(value = "images", required = false)
            List<MultipartFile> images,

            @Parameter(hidden = true) // @Auth는 Swagger 문서에는 표시하지 않음
            @Auth Long memberId
    ) {
        articleService.createArticle(memberId, request, images);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID로 단건 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ArticleResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글 없음")
    })
    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse> getArticleById(
            @Parameter(description = "게시글 ID", required = true)
            @PathVariable Long articleId
    ) {
        Article article = articleService.findArticle(articleId);

        List<String> imagePaths = article.getImages().stream()
                .map(Image::getStoredPath)
                .toList();

        return ResponseEntity.ok(new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                imagePaths
        ));
    }

    @Operation(
            summary = "게시글 전체 조회",
            description = "전체 게시글 목록을 조회하며, 각 게시글에는 대표 이미지 1장만 포함됩니다."
    )
    @ApiResponse(responseCode = "200", description = "전체 게시글 조회 성공", content = @Content(schema = @Schema(implementation = ArticleResponses.class)))
    @GetMapping("/articles")
    public ResponseEntity<ArticleResponses> getAllArticles() {
        List<Article> articles = articleService.findAllArticles();

        List<ArticleResponse> articleResponses = articles.stream()
                .map(article -> {
                    List<Image> images = article.getImages();
                    List<String> imagePaths = new ArrayList<>();

                    if (images != null && !images.isEmpty()) {
                        imagePaths.add(images.get(0).getStoredPath()); // 이미지 한 장만 가져옴
                    }

                    return new ArticleResponse(
                            article.getId(),
                            article.getTitle(),
                            article.getContent(),
                            imagePaths
                    );
                }).toList();

        return ResponseEntity.ok(new ArticleResponses(articleResponses));
    }
}
