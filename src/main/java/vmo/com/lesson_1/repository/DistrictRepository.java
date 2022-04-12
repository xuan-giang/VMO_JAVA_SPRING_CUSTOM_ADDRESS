package vmo.com.lesson_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vmo.com.lesson_1.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}
