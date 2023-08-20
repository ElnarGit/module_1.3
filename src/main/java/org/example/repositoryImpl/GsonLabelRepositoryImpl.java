package org.example.repositoryImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.enums.PostStatus;
import org.example.exception.NotFoundException;
import org.example.model.Label;
import org.example.repository.LabelRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private static final String FILE_PATH = "src/main/resources/labels.json";
    private final Gson gson = new Gson();
    private List<Label> labels;

    public GsonLabelRepositoryImpl(){
        loadLabels();
    }

    private void loadLabels(){
        try(Reader reader = new FileReader(FILE_PATH)){
            Type type = new TypeToken<List<Label>>(){}.getType();
            labels = gson.fromJson(reader, type);
            if(labels == null){
                labels = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLabels(){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(labels, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Label getById(Long id) {
        return labels.stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Label not found with id: " + id));
    }

    @Override
    public List<Label> getAll() {
        return labels;
    }

    @Override
    public Label save(Label label) {
        long nextId = labels.stream()
                .mapToLong(Label::getId).max().orElse(0) + 1;

        label.setId(nextId);
        labels.add(label);
        saveLabels();

        return label;
    }

    @Override
    public Label update(Label updateLabel) {
        labels.stream()
                .filter(label -> label.getId().equals(updateLabel.getId()))
                .findFirst()
                .ifPresent(label -> {
                    label.setName(updateLabel.getName());
                    label.setPostStatus(updateLabel.getPostStatus());
                    saveLabels();
                });

        return updateLabel;
    }

    @Override
    public void deleteById(Long id) {
        labels.stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .ifPresent(label -> label.setPostStatus(PostStatus.DELETED));

        saveLabels();

    }
}
