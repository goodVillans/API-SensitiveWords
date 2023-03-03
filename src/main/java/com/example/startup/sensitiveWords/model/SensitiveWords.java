package com.example.startup.sensitiveWords.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sensitive_words")
public class SensitiveWords {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "words")
    private String sensitiveWord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSensitiveWord() {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord) {
        this.sensitiveWord = sensitiveWord;
    }

    @Override
    public String toString() {
        return "SensitiveWords{" +
                "id=" + id +
                ", sensitiveWord='" + sensitiveWord + '\'' +
                '}';
    }
}
