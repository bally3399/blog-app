package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.View;
import africa.semicolon.blog.dtos.request.ViewRequest;
import africa.semicolon.blog.dtos.response.ViewResponse;

public interface ViewServices {
    ViewResponse view(ViewRequest viewRequest);

    View findViewBy(String viewer);
}
