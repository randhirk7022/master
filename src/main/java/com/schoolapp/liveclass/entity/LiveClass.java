package com.schoolapp.liveclass.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "live_classes")
public class LiveClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(length = 2000)
	private String description;

	private Long academicYearId;
	private Long classId;
	private Long sectionId;
	private Long subjectId;
	private Long teacherId;

	private LocalDateTime scheduledStartTime;
	private LocalDateTime scheduledEndTime;
	private LocalDateTime actualStartTime;
	private LocalDateTime actualEndTime;

	private String providerName;
	private String providerMeetingId;
	private boolean reminderSent = false;

	@Column(length = 1000)
	private String joinUrl;

	@Column(length = 1000)
	private String hostUrl;

	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LiveClassStatus status = LiveClassStatus.SCHEDULED;

	private boolean recordingEnabled = true;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@PrePersist
	void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(Long academicYearId) {
		this.academicYearId = academicYearId;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public LocalDateTime getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(LocalDateTime scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	public LocalDateTime getScheduledEndTime() {
		return scheduledEndTime;
	}

	public void setScheduledEndTime(LocalDateTime scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}

	public LocalDateTime getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(LocalDateTime actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public LocalDateTime getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(LocalDateTime actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderMeetingId() {
		return providerMeetingId;
	}

	public void setProviderMeetingId(String providerMeetingId) {
		this.providerMeetingId = providerMeetingId;
	}

	public String getJoinUrl() {
		return joinUrl;
	}

	public void setJoinUrl(String joinUrl) {
		this.joinUrl = joinUrl;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LiveClassStatus getStatus() {
		return status;
	}

	public void setStatus(LiveClassStatus status) {
		this.status = status;
	}

	public boolean isRecordingEnabled() {
		return recordingEnabled;
	}

	public void setRecordingEnabled(boolean recordingEnabled) {
		this.recordingEnabled = recordingEnabled;
	}
	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}
}