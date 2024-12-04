package com.example.pethealth.repositories;

import com.example.pethealth.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    @Override
    Optional<Medicine> findById(Long aLong);
    @Query("select u from Medicine u where u.typeMedicine.id = ?1")
    List<Medicine> findByTypeMedicineId(long id);
}
