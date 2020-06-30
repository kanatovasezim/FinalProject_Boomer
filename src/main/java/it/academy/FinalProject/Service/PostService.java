package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAll();
    Post getById(Long id);
    Post save(Post post);
    void delete(Long id);
}
