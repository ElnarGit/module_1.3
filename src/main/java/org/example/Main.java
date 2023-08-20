package org.example;

import org.example.controller.LabelController;
import org.example.controller.PostController;
import org.example.controller.WriterController;
import org.example.repository.LabelRepository;
import org.example.repository.PostRepository;
import org.example.repository.WriterRepository;
import org.example.repositoryImpl.GsonLabelRepositoryImpl;
import org.example.repositoryImpl.GsonPostRepositoryImpl;
import org.example.repositoryImpl.GsonWriterRepositoryImpl;
import org.example.view.LabelView;
import org.example.view.PostView;
import org.example.view.WriterView;

public class Main {
    public static void main(String[] args) {
        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        WriterController writerController = new WriterController(writerRepository);
        WriterView writerView = new WriterView(writerController);
        //writerView.run();

        PostRepository postRepository = new GsonPostRepositoryImpl();
        PostController postController = new PostController(postRepository);
        PostView postView = new PostView(postController);
        //postView.run();

        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
        LabelController labelController = new LabelController(labelRepository);
        LabelView labelView = new LabelView(labelController);
        labelView.run();


    }
}