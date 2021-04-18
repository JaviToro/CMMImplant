package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.LessonLearnt;
import com.palmatoro.cmmimplant.repository.LessonLearntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/lesson")
public class LessonLearntController {
    @Autowired
    private LessonLearntRepository lessonLearntRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<LessonLearnt> getAllLessons() {
        return lessonLearntRepository.findAll();
    }
}
