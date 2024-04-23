package courses.openapi.controller;

import java.util.List;
import java.util.Optional;


import courses.openapi.model.CourseRef;
import courses.openapi.service.CourseService;
import jakarta.validation.Valid;
import courses.openapi.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses/")
@Tag(name = "Course Controller", description = "This REST controller provide services to manage courses in the Course Tracker application")
public class  CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	@Operation(summary = "Provides all courses available in the Course Tracker application")
	public Iterable<Course> getAllCourses() {
		return courseService.getCourses();
	}
	
	@GetMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@Operation(summary = "Provides course details for the supplied course id from the Course Tracker application")
	public Optional<Course> getCourseById(@PathVariable("id") long courseId) {
		return courseService.getCourseById(courseId);
	}
	@RequestMapping(value="finde/{id}",method = RequestMethod.GET)
	public ResponseEntity<CourseRef> getCourseLink(@PathVariable("id") long courseId){
		Optional<Course> optionalCourse = courseService.getCourseById(courseId);
		if (optionalCourse.isPresent()) {
			Course course = optionalCourse.get();
			CourseRef courseRef = new CourseRef();
			courseRef.setId(course.getId());
			courseRef.setCategory(course.getCategory());
			courseRef.setName(course.getName());
			courseRef.setDescription(course.getDescription());
			courseRef.setRating(course.getRating());
			courseRef.add(linkTo(methodOn(CourseController.class).getCourseLink(courseId)).withSelfRel());
			return ResponseEntity.ok(courseRef);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	@GetMapping("category/{name}")
	@ResponseStatus(code = HttpStatus.OK)
	@Operation(summary = "Provides course details for the supplied course category from the Course Tracker application")
	public Iterable<Course> getCourseByCategory(@PathVariable("name") String category) {
		return courseService.getCoursesByCategory(category);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Creates a new course in the Course Tracker application")
	public Course createCourse(@Valid @RequestBody Course course) {
		return courseService.createCourse(course);
	}
	
	@PutMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Updates the course details in the Course Tracker application for the supplied course id")
	public void updateCourse(@PathVariable("id") long courseId, @Valid @RequestBody Course course) {
		courseService.updateCourse(courseId, course);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Deletes the course details for the supplied course id from the Course Tracker application")
	void deleteCourseById(@PathVariable("id") long courseId) {
		courseService.deleteCourseById(courseId);
	}
	
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Deletes all courses from the Course Tracker application")
	void deleteCourses() {
		courseService.deleteCourses();
	}

}
