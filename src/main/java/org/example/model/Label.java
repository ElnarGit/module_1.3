package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.enums.PostStatus;

@NoArgsConstructor
@Getter @Setter
@ToString
public class Label {
    private Long id;
    private String name;
    private PostStatus postStatus;

    public Label(String name, PostStatus postStatus) {
        this.name = name;
        this.postStatus = postStatus;
    }
}
