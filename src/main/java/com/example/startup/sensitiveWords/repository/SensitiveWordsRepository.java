package com.example.startup.sensitiveWords.repository;

import com.example.startup.sensitiveWords.model.SensitiveWords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveWordsRepository extends JpaRepository<SensitiveWords, Integer> {
}
