package com.schoolapp.recording.storage;

public interface RecordingStorageService {

    String generateSecurePlaybackUrl(String storageKey, Long recordedClassId, Long studentId);

    void deleteRecording(String storageKey);

    String getProviderName();
}