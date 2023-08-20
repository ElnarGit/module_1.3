package org.example.repository;

import org.example.model.Label;
import org.example.model.Post;

public interface PostRepository extends GenericRepository<Post, Long> {
    Post addLabelToPost(Long postId, Label label);
}
