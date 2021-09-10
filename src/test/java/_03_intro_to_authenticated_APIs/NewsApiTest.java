package _03_intro_to_authenticated_APIs;

import _03_intro_to_authenticated_APIs.data_transfer_objects.ApiExampleWrapper;
import _03_intro_to_authenticated_APIs.data_transfer_objects.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.UriBuilder;

import _01_intro_to_APIs.data_transfer_objects.Result;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class NewsApiTest {

    NewsApi newsApi;
    
    @Mock
    WebClient webClient;
    
    @Mock
    RequestHeadersUriSpec requestUri;
    
    @Mock
    RequestHeadersSpec requestHeaders;
    
    @Mock
    ResponseSpec response;
    
    @Mock
    Mono<ApiExampleWrapper> resultMono;

    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	newsApi = new NewsApi();
    	newsApi.setWebClient(webClient);
    }
    
    @Test
    void itShouldGetNewsStoryByTopic() {
        //given
    	String topic = "Sports";
    	
    	ApiExampleWrapper wrapper = new ApiExampleWrapper();
    	Article expectedArticle = new Article();
    	expectedArticle.setTitle("Opinion: Football is a Sport");
    	expectedArticle.setContent("Not an opinion, but it is.");
    	expectedArticle.setUrl("sportsnice.net");
    	List<Article> expectedArticles = new ArrayList<Article>();
    	expectedArticles.add(expectedArticle);
    	
    	ApiExampleWrapper expectedWrapper = new ApiExampleWrapper();
    	expectedWrapper.setArticles(expectedArticles);
    	
    	when(webClient.get())
        	.thenReturn(requestUri);
    	when(requestUri.uri((Function<UriBuilder, URI>) any()))
        	.thenReturn(requestHeaders);
    	when(requestHeaders.retrieve())
    		.thenReturn(response);
    	when(response.bodyToMono(ApiExampleWrapper.class))
    		.thenReturn(resultMono);
    	when(resultMono.block())
        	.thenReturn(expectedWrapper);
    	
        //when
    	ApiExampleWrapper actualWrapper = newsApi.getNewsStoryByTopic(topic);
        //then
    	verify(webClient, times(1)).get();
    	Article actualArticle = actualWrapper.getArticles().get(0);
        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    void itShouldFindStory(){
        //given
    	String topic = "Sports";
    	String articleTitle = "Tennis: A Full History";
    	String articleContent = "Tennis has been a sport in the past and still is.";
    	String articleLink = "tennishist.com";
    	
    	Article article = new Article();
    	article.setTitle(articleTitle);
    	article.setContent(articleContent);
    	article.setUrl(articleLink);
    	List<Article> expectedArticles = new ArrayList<Article>();
    	expectedArticles.add(article);
    	
    	ApiExampleWrapper expectedWrapper = new ApiExampleWrapper();
    	expectedWrapper.setArticles(expectedArticles);
    	
    	when(webClient.get())
    		.thenReturn(requestUri);
    	when(requestUri.uri((Function<UriBuilder, URI>) any()))
    		.thenReturn(requestHeaders);
    	when(requestHeaders.retrieve())
    		.thenReturn(response);
    	when(response.bodyToMono(ApiExampleWrapper.class))
			.thenReturn(resultMono);
    	when(resultMono.block())
    		.thenReturn(expectedWrapper);
    	
    	String expectedArticle =
                articleTitle + " -\n"
                		+ articleContent + "\n"
                        + "Full article: " + articleLink;
        //when
    	String actualArticle = newsApi.findStory(topic);
        //then
    	verify(webClient, times(1)).get();
        assertEquals(expectedArticle, actualArticle);
    }
}