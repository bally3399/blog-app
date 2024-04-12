package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Comment;
import africa.semicolon.blog.data.model.View;
import africa.semicolon.blog.data.repositories.ViewRepository;
import africa.semicolon.blog.dtos.request.ViewRequest;
import africa.semicolon.blog.dtos.response.ViewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.blog.utils.Mapper.map;

@Service
public class ViewServicesImpl implements ViewServices{
    @Autowired
    private ViewRepository viewRepository;
    @Override
    public ViewResponse view(ViewRequest viewRequest) {
        View view = map(viewRequest);
        ViewResponse response = map(view);
        viewRepository.save(view);
        return response;

    }

    @Override
    public View findViewBy(String viewer) {
        return null;
    }
}
