package org.mercury.DocumentService.dao;

import org.mercury.DocumentService.bean.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName DocumentDao
 * @Description TODO
 * @Author katefu
 * @Date 1/2/24 11:13 PM
 * @Version 1.0
 **/


public interface DocumentDao  extends JpaRepository<Document, Integer> {
    List<Document> findAllByTicketId(int id);
}
