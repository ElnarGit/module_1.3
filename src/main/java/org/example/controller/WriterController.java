package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.enums.PostStatus;
import org.example.model.Post;
import org.example.model.Writer;
import org.example.repository.WriterRepository;

import java.util.List;

@RequiredArgsConstructor
public class WriterController {
    private final WriterRepository writerRepository;

    public Writer createWriter(String firstname, String lastname,
                               List<Post> posts, PostStatus postStatus){
        Writer newWriter = new Writer(firstname, lastname, posts, postStatus);

        return writerRepository.save(newWriter);
    }

    public Writer getWriterById(Long id){
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters(){
        return writerRepository.getAll();
    }

    public Writer updateWriter(Long id, String firstname, String lastname,
                               List<Post> posts, PostStatus postStatus){
        Writer updateWriter = new Writer(firstname, lastname, posts, postStatus);
        updateWriter.setId(id);

        return writerRepository.update(updateWriter);
    }

    public void deleteWriter(Long id){
        writerRepository.deleteById(id);
    }

    public Writer addPostToWriter(Long writerId, Post post) {
        return writerRepository.addPostToWriter(writerId, post);
    }
}