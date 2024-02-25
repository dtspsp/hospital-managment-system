package com.krasnikov.hospitalmanagmentsystem.controller;

import com.krasnikov.hospitalmanagmentsystem.model.Patient;
import com.krasnikov.hospitalmanagmentsystem.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    private PatientService patientService;
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        patientService = mock(PatientService.class);
        patientController = new PatientController(patientService);
    }

    @Test
    void testSavePatient_PositiveCase() {
        // Позитивный тест на сохранение пациента
        String fullName = "John Doe";
        int age = 30;
        String gender = "Male";
        String disease = "Fever";
        String admissionDate = "2022-01-01";
        String dischargeDate = "2022-01-10";

        String result = patientController.savePatient(fullName, age, gender, disease, admissionDate, dischargeDate);

        assertEquals("redirect:/", result);
        verify(patientService, times(1)).save(any(Patient.class));
    }

    @Test
    void testSavePatient_NegativeCase() {
        // Негативный тест на сохранение пациента с некорректной датой
        String fullName = "Jane Smith";
        int age = 25;
        String gender = "Female";
        String disease = "Cold";
        String admissionDate = "2022-01-01";
        String dischargeDate = "Invalid Date"; // Некорректная дата

        String result = patientController.savePatient(fullName, age, gender, disease, admissionDate, dischargeDate);

        assertEquals("redirect:/showNewPatientForm", result);
        verify(patientService, never()).save(any(Patient.class));
    }

    @Test
    void testViewHomePage() {
        // Тест для метода viewHomePage()
        String result = patientController.viewHomePage(mock(Model.class));

        assertEquals("index", result);
        verify(patientService, times(1)).findAll();
    }

    @Test
    void testShowNewPatientForm() {
        // Тест для метода showNewPatientForm()
        String result = patientController.showNewPatientForm(mock(Model.class));

        assertEquals("new_patient", result);
    }

    @Test
    void testShowFormForUpdate() {
        // Тест для метода showFormForUpdate()
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);
        when(patientService.findById(id)).thenReturn(patient);

        String result = patientController.showFormForUpdate(id, mock(Model.class));

        assertEquals("update_patient", result);
        verify(patientService, times(1)).findById(id);
    }

    @Test
    void testDeletePatient() {
        // Тест для метода deletePatient()
        Long id = 1L;

        String result = patientController.deletePatient(id);

        assertEquals("redirect:/", result);
        verify(patientService, times(1)).deleteById(id);
    }

    @Test
    void testUpdatePatient() {
        // Тест для метода updatePatient()
        Patient patient = new Patient();
        patient.setId(1L);

        String result = patientController.updatePatient(patient);

        assertEquals("redirect:/", result);
        verify(patientService, times(1)).save(patient);
    }

}