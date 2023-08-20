package org.example.repositoryImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.enums.PostStatus;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.model.Writer;
import org.example.repository.PostRepository;
import org.example.repository.WriterRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GsonWriterRepositoryImpl implements WriterRepository {
    private static final  String FILE_PATH = "src/main/resources/writers.json";
    private final Gson gson = new Gson();
    private List<Writer> writers;
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public GsonWriterRepositoryImpl(){
        loadWriters();
    }



    //предназначен для загрузки данных о писателях из файла
    // и преобразования этих данных в объекты типа List<Writer>
    private void loadWriters(){
        try(Reader reader = new FileReader(FILE_PATH)){
            Type type = new TypeToken<List<Writer>>(){}.getType(); // десериализация
            writers = gson.fromJson(reader, type);
            if(writers == null){
                writers = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //предназначен для сохранения данных о писателях в файл
    private void saveWriters(){
       try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(writers, writer);              //сериализация
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer getById(Long id) {
        return writers.stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException("Writer not found with id: " + id));
    }

    @Override
    public List<Writer> getAll() {
        return writers;
    }

    @Override
    public Writer save(Writer writer) {
        long nextId = writers.stream()
                .mapToLong(Writer::getId).max().orElse(0) + 1;

        writer.setId(nextId);
        writers.add(writer);
        saveWriters();

        return writer;
    }



    @Override
    public Writer update(Writer updateWriter) {
        writers.stream()
                .filter(writer -> writer.getId().equals(updateWriter.getId()))
                .findFirst()
                .ifPresent(writer -> {
                            writer.setFirstname(updateWriter.getFirstname());
                            writer.setLastName(updateWriter.getLastName());
                            writer.setPosts(updateWriter.getPosts());
                            writer.setPostStatus(updateWriter.getPostStatus());
                            saveWriters();
                        });

        return updateWriter;
    }

    @Override
    public void deleteById(Long id) {
        writers.stream()
                        .filter(writer -> writer.getId().equals(id))
                        .findFirst()
                        .ifPresent(writer ->
                                writer.setPostStatus(PostStatus.DELETED));

        saveWriters();
    }

    @Override
    public Writer addPostToWriter(Long writerId, Post post) {
        Writer writer = getById(writerId);
        writer.addPost(post);
        saveWriters();

        postRepository.save(post);
        return writer;
    }



}
