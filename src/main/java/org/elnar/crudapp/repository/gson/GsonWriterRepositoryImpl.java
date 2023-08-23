package org.elnar.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.exception.NotFoundException;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.repository.WriterRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GsonWriterRepositoryImpl implements WriterRepository {
    private static final  String FILE_PATH = "src/main/resources/writers.json";
    private final Gson gson = new Gson();
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public GsonWriterRepositoryImpl(){
        loadWriters();
    }

    //предназначен для загрузки данных о писателях из файла
    // и преобразования этих данных в объекты типа List<Writer>
    private List<Writer> loadWriters(){
        try(Reader reader = new FileReader(FILE_PATH)){
            Type type = new TypeToken<List<Writer>>(){}.getType(); // десериализация
            List<Writer> writers = gson.fromJson(reader, type);

            if(writers == null){
                writers = new ArrayList<>();
            }
            return writers;

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    //предназначен для сохранения данных о писателях в файл
    private void saveWriters(List<Writer> writers){
       try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(writers, writer);              //сериализация
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer getById(Long id) {
        return loadWriters().stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Writer not found with id: " + id));
    }

    @Override
    public List<Writer> getAll() {
        return loadWriters();
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> currentWriters = loadWriters();

        long nextId = IdGenerated.generatedNextId(currentWriters, Writer::getId);

        writer.setId(nextId);
        currentWriters.add(writer);
        saveWriters(currentWriters);

        return writer;
    }


    @Override
    public Writer update(Writer updateWriter) {
        List<Writer> currentWriters = loadWriters();
        List<Writer> updatedWriters = currentWriters.stream()
                .map(existingWriter -> {
                    if (existingWriter.getId().equals(updateWriter.getId())) {
                        existingWriter.setFirstname(updateWriter.getFirstname());
                        existingWriter.setLastName(updateWriter.getLastName());
                        existingWriter.setPosts(updateWriter.getPosts());
                        existingWriter.setWriterStatus(updateWriter.getWriterStatus());

                        for (Post post : updateWriter.getPosts()) {
                            postRepository.update(post);
                            postRepository.save(post);
                        }
                    }
                    return existingWriter;
                }).toList();

        saveWriters(updatedWriters);
        return updateWriter;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> currentWriters = loadWriters();

        List<Writer> deleteWriters = currentWriters.stream()
                .map(exisitngWriter -> {
                    if (exisitngWriter.getId().equals(id)) {
                        exisitngWriter.setWriterStatus(WriterStatus.DELETED);
                    }
                    return exisitngWriter;
                }).toList();

        saveWriters(deleteWriters);
    }
}
