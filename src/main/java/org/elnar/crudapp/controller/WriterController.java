package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.repository.WriterRepository;

import java.util.List;

@RequiredArgsConstructor
public class WriterController {
    private final WriterRepository writerRepository;


    public Writer createWriter(String firstname, String lastname,
                               List<Post> posts, WriterStatus writerStatus){

        Writer newWriter = Writer.builder()
                .firstname(firstname)
                .lastName(lastname)
                .posts(posts)
                .writerStatus(writerStatus)
                .build();

        return writerRepository.save(newWriter);
    }

    public Writer getWriterById(Long id){
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters(){
        return writerRepository.getAll();
    }

    public Writer updateWriter(Long id, String firstname, String lastname,
                               List<Post> posts, WriterStatus writerStatus){

        Writer updateWriter = Writer.builder()
                .firstname(firstname)
                .lastName(lastname)
                .posts(posts)
                .writerStatus(writerStatus)
                .build();

        updateWriter.setId(id);

        return writerRepository.update(updateWriter);
    }

    public void deleteWriter(Long id){
        writerRepository.deleteById(id);
    }

    public Writer addPostToWriter(Long writerId, Post post) {
        Writer writer = writerRepository.getById(writerId);
        writer.addPost(post);
        return writerRepository.update(writer);
    }
}