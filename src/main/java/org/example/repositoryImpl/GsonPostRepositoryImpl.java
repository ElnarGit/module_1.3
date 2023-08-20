package org.example.repositoryImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.exception.NotFoundException;
import org.example.model.Label;
import org.example.model.Post;
import org.example.repository.LabelRepository;
import org.example.repository.PostRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {
    private static final String FILE_PATH = "src/main/resources/posts.json";
    private final Gson gson = new Gson();
    private List<Post> posts;
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    public GsonPostRepositoryImpl(){
        loadPosts();
    }

    private void loadPosts(){
         try(Reader reader = new FileReader(FILE_PATH)){
             Type type = new TypeToken<List<Post>>(){}.getType();
             posts = gson.fromJson(reader, type);
             if(posts == null){
                 posts = new ArrayList<>();
             }
         } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePosts(){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(posts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Post getById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
    }

    @Override
    public List<Post> getAll() {
        return posts;
    }

    @Override
    public Post save(Post post) {
        long nextId = posts.stream()
                .mapToLong(Post::getId)
                .max().orElse(0) + 1;

        post.setId(nextId);
        posts.add(post);

        savePosts();

        return post;
    }

    @Override
    public Post update(Post updatePost) {
        posts.stream()
                .filter(post -> post.getId().equals(updatePost.getId()))
                .findFirst()
                .ifPresent(post -> {
                    post.setContent(updatePost.getContent());
                    post.setUpdated(updatePost.getUpdated());
                    post.setLabels(updatePost.getLabels());
                    savePosts();
                });

        return updatePost;
    }

    @Override
    public void deleteById(Long id) {
        posts.removeIf(post -> post.getId().equals(id));
        savePosts();
    }


    @Override
    public Post addLabelToPost(Long postId, Label label) {
        Post post = getById(postId);
        post.addLabel(label);
        savePosts();

        labelRepository.save(label);

        return post;
    }
}
