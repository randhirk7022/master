package com.schoolapp.teacher.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.teacher.dto.*;
import com.schoolapp.teacher.entity.*;
import com.schoolapp.teacher.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherResponse createTeacher(CreateTeacherRequest request) {
        if (teacherRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists");
        }

        Teacher teacher = new Teacher();
        teacher.setEmployeeCode(request.getEmployeeCode());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setQualification(request.getQualification());
        teacher.setJoiningDate(request.getJoiningDate());
        teacher.setUserId(request.getUserId());

        return toResponse(teacherRepository.save(teacher));
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TeacherResponse getTeacherById(Long id) {
        return toResponse(findTeacher(id));
    }

    public TeacherResponse updateTeacher(Long id, UpdateTeacherRequest request) {
        Teacher teacher = findTeacher(id);

        if (request.getFirstName() != null) {
            teacher.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            teacher.setLastName(request.getLastName());
        }

        if (request.getEmail() != null) {
            teacher.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            teacher.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getQualification() != null) {
            teacher.setQualification(request.getQualification());
        }

        if (request.getJoiningDate() != null) {
            teacher.setJoiningDate(request.getJoiningDate());
        }

        if (request.getStatus() != null) {
            teacher.setStatus(request.getStatus());
        }

        if (request.getUserId() != null) {
            teacher.setUserId(request.getUserId());
        }

        return toResponse(teacherRepository.save(teacher));
    }

    public TeacherResponse deactivateTeacher(Long id) {
        Teacher teacher = findTeacher(id);
        teacher.setStatus(TeacherStatus.INACTIVE);
        return toResponse(teacherRepository.save(teacher));
    }

    public void deleteTeacher(Long id) {
        teacherRepository.delete(findTeacher(id));
    }

    private Teacher findTeacher(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
    }

    private TeacherResponse toResponse(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getEmployeeCode(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getPhoneNumber(),
                teacher.getQualification(),
                teacher.getJoiningDate(),
                teacher.getStatus(),
                teacher.getUserId()
        );
    }
}