package ru.skillbox.postservice.controller;

import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skillbox.commonlib.dto.post.PostDto;
import ru.skillbox.commonlib.dto.post.PostType;
import ru.skillbox.commonlib.dto.post.wrappers.TagWrapper;
import ru.skillbox.postservice.TestDependenciesContainer;
import ru.skillbox.postservice.model.entity.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PostControllerIT extends TestDependenciesContainer {
    @BeforeEach
    void initBeforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        clearAllRepositories();
    }

    @Test
    @DisplayName("test get post by id test")
    void getPostById_ReturnsValidPost() throws Exception {
        savePostInDbAndGet(generateTestPostDto(), 1L);

        mockMvc.perform(get(apiPrefix + "/post/" + postRepository
                        .findAll().get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    @DisplayName("update post is correct test")
    void updatePostById_CorrectUpdate() throws Exception {
        Post newPost = saveAndGetTestPostToUpdate();
        PostDto updatedPostDto = getUpdatedPostDtoByPost(newPost);
        String updatedPostJson = objectMapper.writeValueAsString(updatedPostDto);
        mockMvc.perform(put(apiPrefix + "/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPostJson)
                        .header("id", 1L))
                .andExpect(status().isCreated());

        Post post = postRepository.getPostByIdOrThrowException(newPost.getId());
        assertEquals(post.getPostText(), updatedPostDto.getPostText());
    }

    @Test
    @DisplayName("update post is forbidden of fake author test")
    void updatePostById_NotAccess() throws Exception {
        Post newPost = saveAndGetTestPostToUpdate();
        PostDto updatedPostDto = getUpdatedPostDtoByPost(newPost);
        String updatedPostJson = objectMapper.writeValueAsString(updatedPostDto);
        mockMvc.perform(put(apiPrefix + "/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPostJson)
                        .header("id", 2L))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("post successful deleted test")
    void deletePostById_CorrectDelete() throws Exception {
        createAndDeletePost(1L)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("post deletion failed of fake author test")
    void deletePostById_NotAccess() throws Exception {
        createAndDeletePost(2L)
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("search posts with specific criteria api test")
    void searchPosts() throws Exception {

        PostDto testPostDto = generateTestPostDto();
        savePostInDbAndGet(testPostDto, 1L);
        mockMvc.perform(get(apiPrefix + "/post")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .param("sort", "id,asc")
                        .param("ids", "1")
                        .param("accountIds", "1")
                        .param("author", testPostDto.getAuthorId().toString())
                        .param("title", testPostDto.getTitle())
                        .param("postText", testPostDto.getPostText())
                        .param("withFriends", String.valueOf(false))
                        .param("isDelete", String.valueOf(false))
                        .header("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("creating post test")
    void createPost() throws Exception {
        String postJson = objectMapper.writeValueAsString(generateTestPostDto());
        mockMvc.perform(post(apiPrefix + "/post?publishDate=" + System.currentTimeMillis())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson)
                        .header("id", 1L))
                .andExpect(status().isCreated());
    }

    //-------------------------UTIL-METHODS------------------------------

    private Post saveAndGetTestPostToUpdate() {
        return savePostInDbAndGet(PostDto.builder()
                .title("Test Post to update")
                .postText("This is a test post to update")
                .authorId(1L)
                .type(PostType.POSTED)
                .tags(List.of(new TagWrapper("string")))
                .build(), 1L);
    }

    private PostDto getUpdatedPostDtoByPost(Post newPost) {
        PostDto updatedPostDto = postMapper.postToPostDto(newPost);
        String updatedPostText = "post updated";
        updatedPostDto.setPostText(updatedPostText);
        updatedPostDto.setId(newPost.getId());
        return updatedPostDto;
    }

    private Post saveAndGetTestPostToDelete() {
        return savePostInDbAndGet(PostDto.builder()
                .title("Test Post to delete")
                .postText("This is a test post to delete")
                .authorId(1L)
                .type(PostType.POSTED)
                .tags(List.of(new TagWrapper("string")))
                .build(), 1L);
    }

    private ResultActions createAndDeletePost(Long userId) throws Exception {
        Post post = saveAndGetTestPostToDelete();
        return mockMvc.perform(delete(apiPrefix + "/post/" + post.getId())
                .header("id", userId));
    }
}