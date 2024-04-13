package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.View;
import africa.semicolon.blog.data.repositories.ViewRepository;
import africa.semicolon.blog.dtos.request.ViewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.blog.utils.Mapper.map;

@Service
public class ViewServicesImpl implements ViewServices{
    @Autowired
    private ViewRepository viewRepository;
    @Override
    public View view(ViewRequest viewRequest) {
        View view = map(viewRequest);
        return viewRepository.save(view);
    }

    @Override
    public View findViewBy(String viewer) {
        return null;
    }
}
