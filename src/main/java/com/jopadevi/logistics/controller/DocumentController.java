package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Document;
import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.repository.DocumentRepository;
import com.jopadevi.logistics.repository.TruckRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final TruckRepository truckRepository;

    public DocumentController(
            DocumentRepository documentRepository,
            TruckRepository truckRepository
    ) {
        this.documentRepository = documentRepository;
        this.truckRepository = truckRepository;
    }


    // ==========================================
    // GET ALL DOCUMENTS
    // ==========================================

    @GetMapping
    public List<Document> getAllDocuments() {

        return documentRepository.findAll();
    }


    // ==========================================
    // GET DOCUMENT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(
            @PathVariable Long id
    ) {

        return documentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ==========================================
    // GET DOCUMENTS OF A TRUCK
    // ==========================================

    @GetMapping("/truck/{truckId}")
    public ResponseEntity<List<Document>> getDocumentsByTruck(
            @PathVariable Long truckId
    ) {

        if (!truckRepository.existsById(truckId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                documentRepository.findByTruckId(truckId)
        );
    }


    // ==========================================
    // ADD DOCUMENT TO TRUCK
    // ==========================================

    @PostMapping("/truck/{truckId}")
    public ResponseEntity<?> addDocument(
            @PathVariable Long truckId,
            @RequestBody Document document
    ) {

        Truck truck = truckRepository.findById(truckId)
                .orElse(null);

        if (truck == null) {
            return ResponseEntity.notFound().build();
        }

        document.setTruck(truck);

        /*
         * Automatically determine document status
         * from expiry date.
         */
        updateDocumentStatus(document);

        Document savedDocument =
                documentRepository.save(document);

        return ResponseEntity.ok(savedDocument);
    }


    // ==========================================
    // UPDATE DOCUMENT
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable Long id,
            @RequestBody Document updatedDocument
    ) {

        Document existingDocument =
                documentRepository.findById(id)
                        .orElse(null);

        if (existingDocument == null) {
            return ResponseEntity.notFound().build();
        }


        existingDocument.setDocumentType(
                updatedDocument.getDocumentType()
        );

        existingDocument.setDocumentNumber(
                updatedDocument.getDocumentNumber()
        );

        existingDocument.setIssueDate(
                updatedDocument.getIssueDate()
        );

        existingDocument.setExpiryDate(
                updatedDocument.getExpiryDate()
        );

        existingDocument.setFileName(
                updatedDocument.getFileName()
        );

        existingDocument.setFileUrl(
                updatedDocument.getFileUrl()
        );

        existingDocument.setNotes(
                updatedDocument.getNotes()
        );


        updateDocumentStatus(existingDocument);


        Document savedDocument =
                documentRepository.save(existingDocument);

        return ResponseEntity.ok(savedDocument);
    }


    // ==========================================
    // DELETE DOCUMENT
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(
            @PathVariable Long id
    ) {

        if (!documentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        documentRepository.deleteById(id);

        return ResponseEntity.ok(
                "Document deleted successfully"
        );
    }


    // ==========================================
    // DOCUMENT STATUS
    // ==========================================

    private void updateDocumentStatus(Document document) {

        LocalDate expiryDate =
                document.getExpiryDate();

        if (expiryDate == null) {
            document.setStatus("UNKNOWN");
            return;
        }


        LocalDate today =
                LocalDate.now();


        if (expiryDate.isBefore(today)) {

            document.setStatus("EXPIRED");

        } else if (
                !expiryDate.isAfter(
                        today.plusDays(30)
                )
        ) {

            document.setStatus("EXPIRING_SOON");

        } else {

            document.setStatus("VALID");
        }
    }
}