package com.schoolapp.liveclass.repository;

import com.schoolapp.liveclass.entity.LiveClassProviderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveClassProviderEventRepository extends JpaRepository<LiveClassProviderEvent, Long> {
}