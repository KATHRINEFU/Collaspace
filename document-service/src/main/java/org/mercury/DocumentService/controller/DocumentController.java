package org.mercury.DocumentService.controller;

import org.mercury.DocumentService.bean.Document;
import org.mercury.DocumentService.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DocumentController
 * @Description TODO
 * @Author katefu
 * @Date 1/2/24 11:10 PM
 * @Version 1.0
 **/
@RestController("/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/get/{id}")
    public Document getDocumentById(@PathVariable int id){
        return documentService.getById(id);
    }

    @PostMapping("/add")
    public void addDocument(@RequestBody Document document){
        documentService.saveDocument(document);
    }
}
