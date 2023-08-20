package org.example.view;

import org.example.controller.PostController;
import org.example.enums.PostStatus;
import org.example.model.Label;
import org.example.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final Scanner scanner;

    public PostView(PostController postController) {
        this.postController = postController;
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        boolean running = true;

        while (running){
            System.out.println("1. Create Post");
            System.out.println("2. Get Post by ID");
            System.out.println("3. Get All Posts");
            System.out.println("4. Update Post");
            System.out.println("5. Delete Post");
            System.out.println("6. Add label to Post");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1 :
                    createPost();
                    break;
                case 2:
                    getPostById();
                    break;
                case 3:
                    getAllPosts();
                    break;
                case 4:
                    updatePost();
                    break;
                case 5:
                    deletePost();
                    break;
                case 6:
                    addLabelToPost();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void createPost(){

        System.out.println("Enter content: ");
        String content = scanner.nextLine();


        Date created = new Date();
        Date updated = new Date();

        List<Label> labels = new ArrayList<>();

        Post newPost = postController.createWriter(content, created, updated, labels);
        System.out.println("Post created with ID: " + newPost.getId());
    }

    private void getPostById(){
        System.out.println("Enter Post id: ");
        long id = scanner.nextLong();
        Post post = postController.getPostById(id);
        System.out.println("Post found: " + post);
    }

    public void getAllPosts(){
        List<Post> posts = postController.getAllPosts();
        for(Post post : posts){
            System.out.println(post);
        }
    }

    public void updatePost(){
        System.out.println("Enter Post id to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter update content: ");
        String content = scanner.nextLine();

        Date created = new Date();
        Date updated = new Date();

        List<Label> labels = new ArrayList<>();

        Post updatePost = postController.updatePost(id, content, created, updated, labels);
        System.out.println("Post updated with ID: " + updatePost.getId());
    }

    public void deletePost(){
        System.out.print("Enter post ID to delete: ");
        Long id = scanner.nextLong();
        postController.deletePost(id);
        System.out.println("Post deleted.");
    }

    public void addLabelToPost() {
        System.out.print("Enter Post ID to add a post to: ");
        Long postId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter label name: ");
        String name = scanner.nextLine();

        Label newLabel = new Label(name, PostStatus.ACTIVE);

        Post updatePost = postController.addLabelToPost(postId, newLabel);

        System.out.println("Label added to Post with ID: " + updatePost.getId());
    }

}

