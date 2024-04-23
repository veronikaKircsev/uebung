package courses.openapi.model;


import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Entity
@Table(name = "COURSES")
@Data
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotEmpty
	@Column(name = "NAME")
	private String name;

	@NotEmpty
	@Column(name = "CATEGORY")
	private String category;

	@Min(value = 1, message = "Minimum rating value is 1")
	@Max(value = 5, message = "maximum rating value is 5")
	@Column(name = "RATING")
	private int rating;

	@NotEmpty
	@Column(name = "DESCRIPTION")
	private String description;
}
