package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Label;
import org.example.model.Post;
import org.example.repository.PostRepository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    public Post createWriter(String content, Date created,
                             Date updated, List<Label> labels){

        Post newPost = new Post(content,created, updated, labels);
        return postRepository.save(newPost);
    }

    public Post getPostById(Long id){
        return postRepository.getById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.getAll();
    }

    public Post updatePost(Long id,String content, Date created,
                             Date updated, List<Label> labels){

        Post updatePost = new Post(content,created, updated, labels);
        updatePost.setId(id);

        return postRepository.update(updatePost);
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public Post addLabelToPost(Long postId, Label label){
        return postRepository.addLabelToPost(postId, label);
    }
}
