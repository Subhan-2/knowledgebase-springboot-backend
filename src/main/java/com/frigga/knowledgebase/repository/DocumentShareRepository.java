package com.frigga.knowledgebase.repository;

import com.frigga.knowledgebase.model.DocumentShare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentShareRepository extends JpaRepository<DocumentShare, Long> {
}
