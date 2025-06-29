package com.frigga.knowledgebase.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "document_shares")
public class DocumentShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "shared_user_id")
    private User sharedUser;

    private String permission = "view";
}
