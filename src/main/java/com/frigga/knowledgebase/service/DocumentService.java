package com.frigga.knowledgebase.service;

import com.frigga.knowledgebase.model.*;
import com.frigga.knowledgebase.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentShareRepository documentShareRepository;

    public Document createDocument(Long userId, Document document) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        document.setOwner(user);
        Document savedDoc = documentRepository.save(document);
        processMentions(savedDoc);
        return savedDoc;
    }

    public List<Document> getUserDocuments(Long userId) {
        return documentRepository.findByOwnerId(userId);
    }

    public Optional<Document> getDocumentById(Long docId) {
        return documentRepository.findById(docId);
    }

    public Document updateDocument(Long docId, Document updatedDoc) throws Exception {
        Document existingDoc = documentRepository.findById(docId)
                .orElseThrow(() -> new Exception("Document not found"));

        existingDoc.setTitle(updatedDoc.getTitle());
        existingDoc.setContent(updatedDoc.getContent());
        existingDoc.setPublic(updatedDoc.isPublic());

        Document savedDoc = documentRepository.save(existingDoc);
        processMentions(savedDoc);
        return savedDoc;
    }

    public List<Document> searchDocuments(String keyword) {
        return documentRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }

    public List<Document> getDocumentsSharedWithUser(Long userId) {
        return documentShareRepository.findAll().stream()
                .filter(share -> share.getSharedUser().getId().equals(userId))
                .map(DocumentShare::getDocument)
                .toList();
    }

    private void processMentions(Document document) {
        String content = document.getContent();
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String mentionedUsername = matcher.group(1);
            userRepository.findAll().stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(mentionedUsername))
                    .findFirst()
                    .ifPresent(mentionedUser -> {
                        DocumentShare share = new DocumentShare();
                        share.setDocument(document);
                        share.setSharedUser(mentionedUser);
                        share.setPermission("view");
                        documentShareRepository.save(share);
                    });
        }
    }
}
