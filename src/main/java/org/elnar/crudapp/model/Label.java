package org.elnar.crudapp.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.elnar.crudapp.enums.LabelStatus;


@Getter
@Setter
@ToString
@Builder
//@Accessors(chain = true)
public class Label {
    private Long id;
    private String name;
    private LabelStatus labelStatus;
}
