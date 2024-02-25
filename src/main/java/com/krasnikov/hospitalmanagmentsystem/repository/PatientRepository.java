package com.krasnikov.hospitalmanagmentsystem.repository;

import com.krasnikov.hospitalmanagmentsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
