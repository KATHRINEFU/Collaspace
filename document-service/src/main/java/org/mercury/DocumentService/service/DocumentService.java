package org.mercury.DocumentService.service;

import org.mercury.DocumentService.bean.Document;
import org.mercury.DocumentService.dao.DocumentDao;
import org.mercury.DocumentService.dto.DocumentCreationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName DocumentService
 * @Description TODO
 * @Author katefu
 * @Date 1/2/24 11:13 PM
 * @Version 1.0
 **/

@Service
public class DocumentService {
    @Autowired
    private DocumentDao documentDao;

    public Document getById(int id){
        return documentDao.findById(id).orElse(null);
    }

    public Document saveDocument(Document document){
        return documentDao.save(document);
    }

    @RabbitListener(queues = {"q.create-document"})
    public void onListenCreateEmployee(List<DocumentCreationRequest> requests) {
        requests.forEach(request -> {
            Document newDoc = new Document();
            newDoc.setDocumentLink(request.getDocumentLink());
            newDoc.setDocumentFromType(request.getDocumentFromType());
            newDoc.setDocumentUploaddate(new Date());
            documentDao.save(newDoc);
        });

    }
}
