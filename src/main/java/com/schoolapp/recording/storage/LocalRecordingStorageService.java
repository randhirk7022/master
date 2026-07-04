package com.schoolapp.recording.storage;

import org.springframework.stereotype.Service;

@Service
public class LocalRecordingStorageService implements RecordingStorageService {

    @Override
    public String generateSecurePlaybackUrl(String storageKey, Long recordedClassId, Long studentId) {
        return "https://recordings.example.com/play/"
                + storageKey
                + "?recordedClassId="
                + recordedClassId
                + "&studentId="
                + studentId
                + "&token=demo-secure-token";
    }

    @Override
    public void deleteRecording(String storageKey) {
    }

    @Override
    public String getProviderName() {
        return "LOCAL";
    }
}