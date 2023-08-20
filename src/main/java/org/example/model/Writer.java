package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.enums.PostStatus;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@ToString
public class Writer {
    private Long id;
    private String firstname;
    private String lastName;
    private List<Post> posts;
    private PostStatus postStatus;

    public Writer(String firstname, String lastName, List<Post> posts, PostStatus postStatus) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.posts = posts;
        this.postStatus = postStatus;
    }

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }
}
