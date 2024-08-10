package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuditionIntegrationClient {


    @Autowired
    private RestTemplate restTemplate;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public List<AuditionPost> getPosts() {
       try {
            String url = BASE_URL + "/posts";
            return restTemplate.getForObject(url, List.class);
        } catch (Exception e) {
            throw new SystemException("Error while fetching posts", e.getMessage(), 500);
        }
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            String url = BASE_URL + "/posts/" + id;
            return restTemplate.getForObject(url, AuditionPost.class);
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException("Error while fetching post with ID: " + id, e.getMessage(), e.getStatusCode().value());
            }
        }
    }
     public List<Object> getCommentsForPost(final String postId) {
        try {
            String url = BASE_URL + "/posts/" + postId + "/comments";
            return restTemplate.getForObject(url, List.class);
        } catch (final Exception e) {
            throw new SystemException("Error while fetching comments for post ID: " + postId, e.getMessage(), 500);
        }
    }

    // Method to get comments based on the postId query parameter
    public List<Object> getCommentsByPostId(final String postId) {
        try {
            String url = BASE_URL + "/comments?postId=" + postId;
            return restTemplate.getForObject(url, List.class);
        } catch (final Exception e) {
            throw new SystemException("Error while fetching comments for post ID: " + postId, e.getMessage(), 500);
        }
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
}
