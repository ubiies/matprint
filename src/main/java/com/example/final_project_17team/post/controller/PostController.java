package com.example.final_project_17team.post.controller;

import com.example.final_project_17team.comment.dto.CommentDto;
import com.example.final_project_17team.post.dto.PostDto;
import com.example.final_project_17team.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mate")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(
            @RequestBody PostDto dto,
            @RequestParam(name="restaurantId", defaultValue="0") Long restaurantId
    ){
        postService.createPost(dto, restaurantId);
        log.info(dto.toString());
        return setResponseEntity("동행 찾기 등록이 완료되었습니다.");
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<Map<String, String>> update(
            @RequestBody PostDto dto,
            @PathVariable("postId") Long postId
    ){
        postService.updatePost(dto, postId);
        log.info(dto.toString());
        return setResponseEntity("동행 찾기 등록이 수정되었습니다.");
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable("postId") Long postId
    ) {
        postService.deletePost(postId);
        return setResponseEntity("동행 모집 글을 삭제했습니다.");
    }

    @GetMapping("/readAll")
    public Page<PostDto> readAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit
    ){
        return postService.searchPost("", page, limit, "", 0, "");
    }

    @GetMapping("/search")
    public Page<PostDto> search(
            @RequestParam(name="target", defaultValue="") String target,
            @RequestParam(name="gender", defaultValue="") String gender,
            @RequestParam(name="age", defaultValue="0") Integer age,
            @RequestParam(name="status", defaultValue="") String status,
            @RequestParam(defaultValue="0") Integer page,
            @RequestParam(defaultValue="10") Integer limit
    ){
        return postService.searchPost(target, page, limit, gender, age, status);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Map<String, String>> createComment(
            @RequestBody CommentDto dto,
            @PathVariable("postId") Long postId
    ) {
        postService.crateComment(dto, postId);
        log.info(dto.toString());
        return setResponseEntity("댓글이 등록되었습니다.");
    }

    @GetMapping("/{postId}/comment")
    public Page<CommentDto> readAllComment(
            @PathVariable("postId") Long postId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        return postService.readComment(postId,page,limit);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<Map<String, String>> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto
    ) {
        postService.updateComment(dto, commentId);
        log.info(dto.toString());
        return setResponseEntity("댓글이 수정되었습니다.");
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        postService.deleteComment(commentId);
        return setResponseEntity("댓글이 삭제되었습니다.");
    }

    public ResponseEntity<Map<String, String>> setResponseEntity(String message) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", message);
        return ResponseEntity.ok(responseBody);
    }
}
