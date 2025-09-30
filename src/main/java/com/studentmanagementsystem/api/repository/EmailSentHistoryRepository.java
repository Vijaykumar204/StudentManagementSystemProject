package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.EmailSentHistoryModel;

@Repository
public interface EmailSentHistoryRepository extends JpaRepository<EmailSentHistoryModel, Long> {

}
