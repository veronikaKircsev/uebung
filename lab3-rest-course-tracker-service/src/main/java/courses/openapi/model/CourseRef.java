package courses.openapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
public class CourseRef extends RepresentationModel<CourseRef> {

    private Long id;
    private String name;
    private String category;
    private int rating;
    private String description;
}
