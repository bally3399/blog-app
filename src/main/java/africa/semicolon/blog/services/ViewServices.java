package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.View;
import africa.semicolon.blog.dtos.request.ViewRequest;

public interface ViewServices {
    View view(ViewRequest viewRequest);

    View findViewBy(String viewer);
}
