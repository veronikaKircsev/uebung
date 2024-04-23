package courses.openapi.service;

import java.util.Optional;

import courses.openapi.exception.CourseNotFoundException;
import courses.openapi.model.Course;
import courses.openapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
	
	private CourseRepository courseRepository;

	@Autowired
	MessageSource messages;
	
	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public Course createCourse(Course course) {
		return courseRepository.save(course);
	}

	@Override
	public Optional<Course> getCourseById(long courseId) {
		Optional<Course> optionalCourse = courseRepository.findById(courseId);
		if(optionalCourse.isEmpty()) {
			throw new CourseNotFoundException(String.format("No course with id %s is available", courseId));
		}
		return optionalCourse;
	}

	@Override
	public Iterable<Course> getCoursesByCategory(String category) {
		return courseRepository.findAllByCategory(category);
	}

	@Override
	public Iterable<Course> getCourses() {
		return courseRepository.findAll();
	}

	@Override
	public Course updateCourse(long courseId, Course course) {
		
		Optional<Course> optionalCourse = courseRepository.findById(courseId);
		if(optionalCourse.isPresent()) {
			Course dbCourse = optionalCourse.get();
			dbCourse.setName(course.getName());
			dbCourse.setCategory(course.getCategory());
			dbCourse.setDescription(course.getDescription());
			dbCourse.setRating(course.getRating());
			
			return courseRepository.save(dbCourse);
		}
		throw new CourseNotFoundException(String.format("No course with id %s is available", courseId));
	}

	@Override
	public void deleteCourses() {
		courseRepository.deleteAll();
	}

	@Override
	public void deleteCourseById(long courseId) {
		Optional<Course> optionalCourse = courseRepository.findById(courseId);
		if(optionalCourse.isPresent()) {
			courseRepository.deleteById(courseId);
		}
		throw new CourseNotFoundException(String.format("No course with id %s is available", courseId));
	}

	@Override
	public String deleteCourse(long licenseId){
		String responseMessage;
		deleteCourseById(licenseId);
		responseMessage = String.format(messages.getMessage("license.delete.message", null, null),licenseId);
		return responseMessage;
	}

}
