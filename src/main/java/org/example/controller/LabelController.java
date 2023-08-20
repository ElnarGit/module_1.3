package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.enums.PostStatus;
import org.example.model.Label;
import org.example.repository.LabelRepository;

import java.util.List;

@RequiredArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository;

    public Label createLabel(String name, PostStatus postStatus){
        Label newLabel = new Label(name, postStatus);
        return labelRepository.save(newLabel);
    }

    public Label getLabelById(Long id){
        return labelRepository.getById(id);
    }

    public List<Label> getAllLabels(){
        return labelRepository.getAll();
    }

    public Label updateLabel(Long id, String name, PostStatus postStatus){
        Label updateLabel = new Label(name, postStatus);
        updateLabel.setId(id);

        return labelRepository.update(updateLabel);
    }

    public void deleteLabel(Long id){
        labelRepository.deleteById(id);
    }
}
