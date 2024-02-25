package com.krasnikov.hospitalmanagmentsystem.controller;

import com.krasnikov.hospitalmanagmentsystem.model.Patient;
import com.krasnikov.hospitalmanagmentsystem.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Получить список всех пациентов", description = "Возвращает список всех пациентов в системе")
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listPatients", patientService.findAll());
        return "index"; // Thymeleaf шаблон в src/main/resources/templates/index.html
    }

    @Operation(summary = "Показать форму для добавления нового пациента", description = "Возвращает HTML-форму для добавления нового пациента")
    @GetMapping("/showNewPatientForm")
    public String showNewPatientForm(Model model) {
        Patient patient = new Patient(); // создаем объект для хранения данных формы
        model.addAttribute("patient", patient);
        return "new_patient"; // Thymeleaf шаблон для добавления пациента
    }

    @Operation(summary = "Сохранить пациента", description = "Сохраняет нового пациента в базе данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Пациент сохранен успешно и произошло перенаправление"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пациента")
    })
    @PostMapping("/savePatient")
    public String savePatient(@RequestParam("fullName") String fullName,
                              @RequestParam("age") Integer age,
                              @RequestParam("gender") String gender,
                              @RequestParam("disease") String disease,
                              @RequestParam("admissionDate") String admissionDateString,
                              @RequestParam("dischargeDate") String dischargeDateString) {
        Patient patient = new Patient();
        // Установка простых полей
        patient.setFullName(fullName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setDisease(disease);

        // Преобразование строковых дат в Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date admissionDate = format.parse(admissionDateString);
            Date dischargeDate = format.parse(dischargeDateString);
            patient.setAdmissionDate(admissionDate);
            patient.setDischargeDate(dischargeDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // Обработка ошибки
            // Возможно, добавить сообщение об ошибке в модель и вернуть пользователя на форму
            return "redirect:/showNewPatientForm";
        }

        // Сохранение пациента
        patientService.save(patient);

        return "redirect:/"; // Перенаправление на главную страницу после сохранения
    }

    @Operation(summary = "Показать форму для редактирования информации о пациенте",
            description = "Возвращает HTML-форму для редактирования информации о пациенте")
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Patient patient = patientService.findById(id);
        System.out.println("Patient ID: " + patient.getId()); // Для отладки
        model.addAttribute("patient", patient);
        return "update_patient";
    }

    @Operation(summary = "Удалить пациента", description = "Удаляет пациента из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Пациент удален успешно и произошло перенаправление"),
            @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable(value = "id") long id) {
        // Вызываем метод удаления пациента
        patientService.deleteById(id);
        return "redirect:/"; // Перенаправляем на главную страницу
    }

    @Operation(summary = "Удалить пациента", description = "Удаляет пациента из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Пациент удален успешно и произошло перенаправление"),
            @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @PostMapping("/updatePatient")
    public String updatePatient(@ModelAttribute("patient") Patient patient) {
        // Здесь patient должен содержать ID, полученный из скрытого поля формы
        // Сервис сохранит изменения в существующем пациенте, используя его ID
        patientService.save(patient);
        return "redirect:/"; // Перенаправление на главную страницу или список пациентов
    }

}
