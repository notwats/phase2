package project.controllers;

import project.database.PostDB;
import project.models.Comment;

public class MainScrolingController extends Controller{
    public static void addComment(Comment comment){
        PostDB.addComment(comment);
    }
}
