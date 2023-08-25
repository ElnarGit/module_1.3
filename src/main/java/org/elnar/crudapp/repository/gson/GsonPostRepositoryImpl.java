package org.elnar.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.exception.NotFoundException;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.repository.LabelRepository;
import org.elnar.crudapp.repository.PostRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {
    private final String FILE_PATH = "src/main/resources/posts.json";
    private final Gson gson = new Gson();
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    private List<Post> loadPosts() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<Post>>() {
            }.getType();
            List<Post> posts = gson.fromJson(reader, type);

            if (posts == null) {
                posts = new ArrayList<>();
            }

            return posts;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private void savePosts(List<Post> posts) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(posts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Post getById(Long id) {
        return loadPosts().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
    }

    @Override
    public List<Post> getAll() {
        return loadPosts();
    }

    @Override
    public Post save(Post post) {
        List<Post> currentPosts = loadPosts();

        long nextId = IdGenerated.generatedNextId(currentPosts, Post::getId);

        post.setId(nextId);
        currentPosts.add(post);
        savePosts(currentPosts);

        return post;
    }

    @Override
    public Post update(Post updatePost) {
        List<Post> currentPosts = loadPosts();
        List<Post> updatePosts = currentPosts.stream()
                .map(existingPost -> {
                    if (existingPost.getId().equals(updatePost.getId())) {
                        existingPost.setContent(updatePost.getContent());
                        existingPost.setUpdated(updatePost.getUpdated());
                        existingPost.setLabels(updatePost.getLabels());
                        existingPost.setPostStatus(updatePost.getPostStatus());

                        for(Label label : updatePost.getLabels()){
                            labelRepository.update(label);
                            labelRepository.save(label);
                        }
                    }
                    return existingPost;
                }).toList();

        savePosts(updatePosts);
        return updatePost;
    }

    @Override
    public void deleteById(Long id) {
        List<Post> currentPosts = loadPosts();

        List<Post> deletePosts = currentPosts.stream()
                .map(existingPost -> {
                    if (existingPost.getId().equals(id)) {
                        existingPost.setPostStatus(PostStatus.DELETED);
                    }
                    return existingPost;
                }).toList();

        savePosts(deletePosts);
    }
}
