package org.elnar.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.exception.NotFoundException;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.repository.LabelRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private static final String FILE_PATH = "src/main/resources/labels.json";
    private final Gson gson = new Gson();


    public GsonLabelRepositoryImpl(){
        loadLabels();
    }

    private List<Label> loadLabels(){
        try(Reader reader = new FileReader(FILE_PATH)){
            Type type = new TypeToken<List<Label>>(){}.getType();
            List<Label> labels = gson.fromJson(reader, type);

            if(labels == null){
                labels = new ArrayList<>();
            }

            return labels;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private void saveLabels(List<Label> labels){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(labels, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Label getById(Long id) {
        return loadLabels().stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Label not found with id: " + id));
    }

    @Override
    public List<Label> getAll() {
        return loadLabels();
    }

    @Override
    public Label save(Label label) {
        List<Label> currentLabels = loadLabels();

        long nextId = IdGenerated.generatedNextId(currentLabels, Label::getId);

        label.setId(nextId);
        currentLabels.add(label);
        saveLabels(currentLabels);

        return label;
    }



    @Override
    public Label update(Label updateLabel) {
        List<Label> currentLabels = loadLabels();
        List<Label> updateLabels = currentLabels.stream()
                .map(exisitngLabel -> {
                    if (exisitngLabel.getId().equals(updateLabel.getId())){
                        return updateLabel;
                    }
                    return exisitngLabel;
                }).toList();

        saveLabels(updateLabels);

        return updateLabel;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> currentLabels = loadLabels();

        List<Label> deleteLabels = currentLabels.stream()
                .map(exisitngLabel -> {
                    if(exisitngLabel.getId().equals(id)){
                        exisitngLabel.setLabelStatus(LabelStatus.DELETED);
                    }
                    return exisitngLabel;
                }).toList();

        saveLabels(deleteLabels);
    }
}
