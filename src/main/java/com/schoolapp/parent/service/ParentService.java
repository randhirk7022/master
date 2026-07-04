package com.schoolapp.parent.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.parent.dto.*;
import com.schoolapp.parent.entity.Parent;
import com.schoolapp.parent.entity.StudentParent;
import com.schoolapp.parent.repository.ParentRepository;
import com.schoolapp.parent.repository.StudentParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private final StudentParentRepository studentParentRepository;

    public ParentService(
            ParentRepository parentRepository,
            StudentParentRepository studentParentRepository
    ) {
        this.parentRepository = parentRepository;
        this.studentParentRepository = studentParentRepository;
    }

    public ParentResponse createParent(CreateParentRequest request) {
        if (parentRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Parent phone number already exists");
        }

        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setPhoneNumber(request.getPhoneNumber());
        parent.setWhatsappNumber(request.getWhatsappNumber());
        parent.setEmail(request.getEmail());
        parent.setAddress(request.getAddress());
        parent.setUserId(request.getUserId());

        if (request.getNotificationPreference() != null) {
            parent.setNotificationPreference(request.getNotificationPreference());
        }

        return toParentResponse(parentRepository.save(parent));
    }

    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll()
                .stream()
                .map(this::toParentResponse)
                .toList();
    }

    public ParentResponse getParentById(Long id) {
        return toParentResponse(findParent(id));
    }

    public ParentResponse updateParent(Long id, UpdateParentRequest request) {
        Parent parent = findParent(id);

        if (request.getFirstName() != null) {
            parent.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            parent.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            parent.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getWhatsappNumber() != null) {
            parent.setWhatsappNumber(request.getWhatsappNumber());
        }

        if (request.getEmail() != null) {
            parent.setEmail(request.getEmail());
        }

        if (request.getAddress() != null) {
            parent.setAddress(request.getAddress());
        }

        if (request.getUserId() != null) {
            parent.setUserId(request.getUserId());
        }

        if (request.getNotificationPreference() != null) {
            parent.setNotificationPreference(request.getNotificationPreference());
        }

        return toParentResponse(parentRepository.save(parent));
    }

    public void deleteParent(Long id) {
        parentRepository.delete(findParent(id));
    }

    public StudentParentResponse linkStudentParent(LinkStudentParentRequest request) {
        if (studentParentRepository.existsByStudentIdAndParentId(
                request.getStudentId(),
                request.getParentId()
        )) {
            throw new RuntimeException("Student and parent already linked");
        }

        StudentParent studentParent = new StudentParent();
        studentParent.setStudentId(request.getStudentId());
        studentParent.setParentId(request.getParentId());
        studentParent.setRelationType(request.getRelationType());

        return toStudentParentResponse(studentParentRepository.save(studentParent));
    }

    public List<StudentParentResponse> getParentsByStudentId(Long studentId) {
        return studentParentRepository.findByStudentId(studentId)
                .stream()
                .map(this::toStudentParentResponse)
                .toList();
    }

    public List<StudentParentResponse> getStudentsByParentId(Long parentId) {
        return studentParentRepository.findByParentId(parentId)
                .stream()
                .map(this::toStudentParentResponse)
                .toList();
    }

    private Parent findParent(Long id) {
        return parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id: " + id));
    }

    private ParentResponse toParentResponse(Parent parent) {
        return new ParentResponse(
                parent.getId(),
                parent.getFirstName(),
                parent.getLastName(),
                parent.getPhoneNumber(),
                parent.getWhatsappNumber(),
                parent.getEmail(),
                parent.getAddress(),
                parent.getUserId(),
                parent.getNotificationPreference()
        );
    }

    private StudentParentResponse toStudentParentResponse(StudentParent studentParent) {
        return new StudentParentResponse(
                studentParent.getId(),
                studentParent.getStudentId(),
                studentParent.getParentId(),
                studentParent.getRelationType()
        );
    }
}