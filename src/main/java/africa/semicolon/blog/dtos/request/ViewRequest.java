package africa.semicolon.blog.dtos.request;

import africa.semicolon.blog.data.model.User;
import lombok.Data;

@Data
public class ViewRequest {
    private User viewer;
}
