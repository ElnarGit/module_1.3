package org.example.repository;

import org.example.model.Post;
import org.example.model.Writer;

public interface WriterRepository extends GenericRepository<Writer, Long>{
    Writer addPostToWriter(Long writerId, Post post);
}
