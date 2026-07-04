package com.schoolapp.liveclass.repository;

import com.schoolapp.liveclass.entity.LiveClassParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LiveClassParticipantRepository extends JpaRepository<LiveClassParticipant, Long> {
    List<LiveClassParticipant> findByLiveClassId(Long liveClassId);
    List<LiveClassParticipant> findByStudentId(Long studentId);
    Optional<LiveClassParticipant> findByLiveClassIdAndStudentId(Long liveClassId, Long studentId);
    boolean existsByLiveClassIdAndStudentId(Long liveClassId, Long studentId);
}