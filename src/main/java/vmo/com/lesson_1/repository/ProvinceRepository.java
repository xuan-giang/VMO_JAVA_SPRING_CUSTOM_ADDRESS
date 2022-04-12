package vmo.com.lesson_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vmo.com.lesson_1.model.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findByCode(Long id);

    void deleteByCode(Long code);
}
