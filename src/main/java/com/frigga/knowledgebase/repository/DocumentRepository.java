package com.frigga.knowledgebase.repository;

import com.frigga.knowledgebase.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByOwnerId(Long userId);
    List<Document> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}

