package com.project.Work360.ai.rag;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PromptLoader {

    @Autowired
    private ResourceLoader loader;

    public String load(String fileName) {
        try {
            Resource resource = loader.getResource("classpath:ia/prompts/" + fileName);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar prompt: " + fileName, e);
        }
    }
}