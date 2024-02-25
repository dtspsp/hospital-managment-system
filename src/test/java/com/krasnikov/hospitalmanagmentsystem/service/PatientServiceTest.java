package com.krasnikov.hospitalmanagmentsystem.service;

import com.krasnikov.hospitalmanagmentsystem.model.Patient;
import com.krasnikov.hospitalmanagmentsystem.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdPositive() {
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        Patient result = patientService.findById(id);

        assertEquals(id, result.getId());
    }

    @Test
    public void testFindByIdNegative() {
        Long id = 1L;

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> patientService.findById(id));
    }

    @Test
    public void testSave() {
        Patient patient = new Patient();

        patientService.save(patient);

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        patientService.deleteById(id);

        verify(patientRepository, times(1)).deleteById(id);
    }
}