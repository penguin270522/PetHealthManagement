package com.example.pethealth.repositories;

import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.model.Appointment;
import com.example.pethealth.repositories.custom.appointment.AppointmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> , AppointmentRepositoryCustom {
    List<Appointment> findByCode(String code);
    @Query("select u from Appointment u where u.appointmentStatus = ?1")
    List<Appointment> searchByAppointmentStatus(AppointmentStatus appointmentStatus);

    @Query("select u from Appointment u where u.nameUser like %?1%")
    List<Appointment> findByNameUser(String name);

    

}
