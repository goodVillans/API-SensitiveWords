package com.example.startup.sensitiveWords.service;

import com.example.startup.sensitiveWords.repository.SensitiveWordsRepository;
import com.example.startup.sensitiveWords.model.SensitiveWords;
import jakarta.annotation.PostConstruct;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CensorSensitiveWords {
    @Autowired
    private static SensitiveWordsRepository sensitiveWordsRepository;

    private static Trie swTrie;

    public CensorSensitiveWords() {

    }

    @PostConstruct
    public static void buildTrie(){
        Collection<SensitiveWords> sensitiveWords = sensitiveWordsRepository.findAll();
        Trie.TrieBuilder triebuilder = Trie.builder().onlyWholeWords().ignoreCase();
        for (SensitiveWords sw: sensitiveWords) {
            triebuilder.addKeyword(sw.getSensitiveWord());
        }
        swTrie = triebuilder.build();
    }

    public static String findAndReplaceSensitiveWords(String message){
        Collection<Emit> emits = swTrie.parseText(message);
        StringBuilder stringBuilder = new StringBuilder(message);
        for (Emit e: emits) {
            String replace = "*".repeat(e.getKeyword().length());
            stringBuilder.replace(e.getStart(), e.getEnd(), replace);
        }
        return stringBuilder.toString();
    }


}
