package org.elnar.crudapp;

import org.elnar.crudapp.controller.LabelController;
import org.elnar.crudapp.controller.PostController;
import org.elnar.crudapp.controller.WriterController;
import org.elnar.crudapp.repository.LabelRepository;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.repository.WriterRepository;
import org.elnar.crudapp.repository.gson.GsonLabelRepositoryImpl;
import org.elnar.crudapp.repository.gson.GsonPostRepositoryImpl;
import org.elnar.crudapp.repository.gson.GsonWriterRepositoryImpl;
import org.elnar.crudapp.view.LabelView;
import org.elnar.crudapp.view.PostView;
import org.elnar.crudapp.view.WriterView;

import java.util.Scanner;

public class ApplicationContext {
     Scanner scanner = new Scanner(System.in);


    public void start(){
        System.out.println("Select an option:");
        System.out.println("1. Writer");
        System.out.println("2. Post");
        System.out.println("3. Label");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                writerRun();
                break;
            case 2:
                postRun();
                break;
            case 3:
                labelRun();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public void writerRun(){
        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        WriterController writerController = new WriterController(writerRepository);
        WriterView writerView = new WriterView(writerController);
        writerView.run();
    }

    public void postRun(){
        PostRepository postRepository = new GsonPostRepositoryImpl();
        PostController postController = new PostController(postRepository);
        PostView postView = new PostView(postController);
        postView.run();
    }

    public void labelRun(){
        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
        LabelController labelController = new LabelController(labelRepository);
        LabelView labelView = new LabelView(labelController);
        labelView.run();
    }
}
