package org.elnar.crudapp.model;

import lombok.*;
import org.elnar.crudapp.enums.WriterStatus;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
public class Writer {
    private Long id;
    private String firstname;
    private String lastName;
    private List<Post> posts;
    private WriterStatus writerStatus;

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }
}
