package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.repository.LabelRepository;

import java.util.List;

@RequiredArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository;

    public Label createLabel(String name, LabelStatus labelStatus){
        Label newLabel = Label.builder()
                .name(name)
                .labelStatus(labelStatus)
                .build();

        return labelRepository.save(newLabel);
    }

    public Label getLabelById(Long id){
        return labelRepository.getById(id);
    }

    public List<Label> getAllLabels(){
        return labelRepository.getAll();
    }

    public Label updateLabel(Long id, String name, LabelStatus labelStatus){
        Label updateLabel = Label.builder()
                .name(name)
                .labelStatus(labelStatus)
                .build();

        updateLabel.setId(id);

        return labelRepository.update(updateLabel);
    }

    public void deleteLabel(Long id){
        labelRepository.deleteById(id);
    }
}
