package org.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private List<Label> labels;

    public Post(String content, Date created, Date updated, List<Label> labels) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
    }

    public void addLabel(Label label){
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(label);
    }
}
