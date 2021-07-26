package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.LessonLearnt;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.LessonLearntService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/lessonLearnt")
public class LessonLearntController {
    @Autowired
    private LessonLearntService lessonLearntService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<LessonLearnt> getAllLessonLearnt() {
        return lessonLearntService.getAllLessonLearnts();
    }

    @Secured("ROLE_USER")
    @Cacheable("lessonLearnt")
    @GetMapping("/{id}")
    public @ResponseBody
    LessonLearnt getLessonLearntById(@PathVariable Integer id) throws ResourceNotFoundException {
        return lessonLearntService.getLessonLearntById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    LessonLearnt addNewLessonLearnt(@RequestBody LessonLearnt lessonLearnt) {
        return lessonLearntService.addNewLessonLearnt(lessonLearnt);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteLessonLearntById(@PathVariable Integer id) {
        lessonLearntService.deleteLessonLearntById(id);
    }
}
