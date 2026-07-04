package com.schoolapp.academic.service;

import com.schoolapp.academic.dto.AcademicRequests.*;
import com.schoolapp.academic.entity.*;
import com.schoolapp.academic.repository.*;
import com.schoolapp.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicService {

    private final AcademicYearRepository academicYearRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;
    private final ClassSubjectRepository classSubjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;

    public AcademicService(
            AcademicYearRepository academicYearRepository,
            SchoolClassRepository schoolClassRepository,
            SectionRepository sectionRepository,
            SubjectRepository subjectRepository,
            ClassSubjectRepository classSubjectRepository,
            TeacherSubjectRepository teacherSubjectRepository
    ) {
        this.academicYearRepository = academicYearRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.sectionRepository = sectionRepository;
        this.subjectRepository = subjectRepository;
        this.classSubjectRepository = classSubjectRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
    }

    public AcademicYear createAcademicYear(CreateAcademicYearRequest request) {
        if (academicYearRepository.existsByName(request.getName())) {
            throw new RuntimeException("Academic year already exists");
        }

        AcademicYear academicYear = new AcademicYear();
        academicYear.setName(request.getName());
        academicYear.setStartDate(request.getStartDate());
        academicYear.setEndDate(request.getEndDate());
        academicYear.setActive(request.isActive());

        return academicYearRepository.save(academicYear);
    }

    public List<AcademicYear> getAcademicYears() {
        return academicYearRepository.findAll();
    }

    public SchoolClass createClass(CreateClassRequest request) {
        academicYearRepository.findById(request.getAcademicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("Academic year not found"));

        if (schoolClassRepository.existsByNameAndAcademicYearId(
                request.getName(),
                request.getAcademicYearId()
        )) {
            throw new RuntimeException("Class already exists for this academic year");
        }

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(request.getName());
        schoolClass.setAcademicYearId(request.getAcademicYearId());

        return schoolClassRepository.save(schoolClass);
    }

    public List<SchoolClass> getClasses(Long academicYearId) {
        if (academicYearId != null) {
            return schoolClassRepository.findByAcademicYearId(academicYearId);
        }

        return schoolClassRepository.findAll();
    }

    public Section createSection(CreateSectionRequest request) {
        schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        if (sectionRepository.existsByNameAndClassId(request.getName(), request.getClassId())) {
            throw new RuntimeException("Section already exists for this class");
        }

        Section section = new Section();
        section.setName(request.getName());
        section.setClassId(request.getClassId());

        return sectionRepository.save(section);
    }

    public List<Section> getSections(Long classId) {
        if (classId != null) {
            return sectionRepository.findByClassId(classId);
        }

        return sectionRepository.findAll();
    }

    public Subject createSubject(CreateSubjectRequest request) {
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Subject code already exists");
        }

        Subject subject = new Subject();
        subject.setName(request.getName());
        subject.setCode(request.getCode());

        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public ClassSubject assignSubjectToClass(AssignSubjectToClassRequest request) {
        schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        if (classSubjectRepository.existsByClassIdAndSubjectId(
                request.getClassId(),
                request.getSubjectId()
        )) {
            throw new RuntimeException("Subject already assigned to class");
        }

        ClassSubject classSubject = new ClassSubject();
        classSubject.setClassId(request.getClassId());
        classSubject.setSubjectId(request.getSubjectId());

        return classSubjectRepository.save(classSubject);
    }

    public List<ClassSubject> getClassSubjects(Long classId) {
        return classSubjectRepository.findByClassId(classId);
    }

    public TeacherSubject assignSubjectToTeacher(AssignSubjectToTeacherRequest request) {
        subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        if (request.getSectionId() != null) {
            sectionRepository.findById(request.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        }

        TeacherSubject teacherSubject = new TeacherSubject();
        teacherSubject.setTeacherId(request.getTeacherId());
        teacherSubject.setSubjectId(request.getSubjectId());
        teacherSubject.setClassId(request.getClassId());
        teacherSubject.setSectionId(request.getSectionId());

        return teacherSubjectRepository.save(teacherSubject);
    }

    public List<TeacherSubject> getTeacherSubjects(Long teacherId) {
        return teacherSubjectRepository.findByTeacherId(teacherId);
    }
}