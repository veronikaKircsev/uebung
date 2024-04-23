package courses.openapi.repository;

import courses.openapi.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Iterable<Course> findAllByRating(int rating);
	
	Iterable<Course> findAllByCategory(String category);
	
	void deleteByName(String name);
}
