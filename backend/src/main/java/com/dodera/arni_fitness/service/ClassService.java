package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.ClassRequest;
import com.dodera.arni_fitness.model.Class;
import com.dodera.arni_fitness.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void addClass(ClassRequest classRequest) {
        var classEntity = new Class();
        classEntity.setTitle(classRequest.title());
        classEntity.setDescription(classRequest.description());
        classRepository.save(classEntity);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public void updateClass(Long id, ClassRequest classRequest) {
        var classEntity = classRepository.findById(id).orElseThrow();
        classEntity.setTitle(classRequest.title());
        classEntity.setDescription(classRequest.description());
        classRepository.save(classEntity);
    }

    public Class getClass(Long id) {
        return classRepository.findById(id).orElseThrow();
    }

    public List<Class> getClasses() {
        return classRepository.findAll();
    }
}
