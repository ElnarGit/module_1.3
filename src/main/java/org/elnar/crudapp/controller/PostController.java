package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.model.Post;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    public Post createWriter(String content, Date created,
                             Date updated, List<Label> labels, PostStatus postStatus){

        Post newPost = Post.builder()
                .content(content)
                .created(created)
                .updated(updated)
                .labels(labels)
                .postStatus(postStatus)
                .build();

        return postRepository.save(newPost);
    }

    public Post getPostById(Long id){
        return postRepository.getById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.getAll();
    }

    public Post updatePost(Long id,String content, Date created,
                             Date updated, List<Label> labels, PostStatus postStatus){

        Post updatePost = Post.builder()
                .content(content)
                .created(created)
                .updated(updated)
                .labels(labels)
                .postStatus(postStatus)
                .build();

        updatePost.setId(id);

        return postRepository.update(updatePost);
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public Post addLabelToPost(Long postId, Label label){
        Post post = postRepository.getById(postId);
        post.addLabel(label);
        return postRepository.update(post);
    }
}
