package com.frigga.knowledgebase.controller;

import com.frigga.knowledgebase.model.Document;
import com.frigga.knowledgebase.security.JwtUtil;
import com.frigga.knowledgebase.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header != null ? header.replace("Bearer ", "") : "";
        return Long.parseLong(jwtUtil.getUserIdFromToken(token));
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document, HttpServletRequest request) throws Exception {
        Long userId = getUserIdFromRequest(request);
        Document createdDoc = documentService.createDocument(userId, document);
        return ResponseEntity.ok(createdDoc);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getUserDocuments(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        List<Document> docs = documentService.getUserDocuments(userId);
        return ResponseEntity.ok(docs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document document) throws Exception {
        Document updatedDoc = documentService.updateDocument(id, document);
        return ResponseEntity.ok(updatedDoc);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchDocuments(@RequestParam String keyword) {
        List<Document> results = documentService.searchDocuments(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/shared-with-me")
    public ResponseEntity<List<Document>> getSharedDocs(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(documentService.getDocumentsSharedWithUser(userId));
    }
}
