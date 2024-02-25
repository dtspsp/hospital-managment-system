package com.krasnikov.hospitalmanagmentsystem.service;

import com.krasnikov.hospitalmanagmentsystem.model.Patient;
import com.krasnikov.hospitalmanagmentsystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Получение всех пациентов
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    // Найти пациента по ID
    public Patient findById(Long id) {
        Optional<Patient> result = patientRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Не найден пациент с ID: " + id);
        }
    }

    // Сохранить или обновить пациента
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    // Удалить пациента по ID
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

}
