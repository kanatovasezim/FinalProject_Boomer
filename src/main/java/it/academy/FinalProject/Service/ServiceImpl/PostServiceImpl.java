package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Post;
import it.academy.FinalProject.Repository.PostRepo;
import it.academy.FinalProject.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Override
    public List<Post> getAll() {
        return postRepo.findAll();
    }

    @Override
    public Post getById(Long id) {
        return postRepo.findById(id).orElse(new Post());
    }

    @Override
    public Post save(Post post) {
        return postRepo.save(post);
    }

    @Override
    public void delete(Long id) {
        postRepo.deleteById(id);
    }
}
