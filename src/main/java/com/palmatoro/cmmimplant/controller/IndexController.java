package com.palmatoro.cmmimplant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    
    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("index_message", "CMMImplant es una herramienta para la gestión de CMMI en empresas, desarrollada por Pablo Palma y Javi Toro como trabajo de fin de grado del último curso de Ingeniería Informática de Software en la Universidad de Sevilla.");
        model.addAttribute("description_message","A modo de resumen, CMMImplant consiste en una metodología a seguir, fácil y sencilla, que permite la implantación del modelo CMMi-DEV 2.0 en cualquier organización que realice desarrollo software apoyándose en un sistema de información con la finalidad de poder recopilar las evidencias necesarias de cara a su certificación.");
        model.addAttribute("project_message", "El presente proyecto pretende dar solución a una de las problemáticas que más presente está en las organizaciones cuando trata de implementar las buenas prácticas del modelo CMMI en su día a día de trabajo, la de unificar las evidencias necesarias de los distintos proyectos que puedan estar dentro del alcance de la certificación para la evaluación de esta misma.");
        model.addAttribute("conclusion_message", "Por tanto, este proyecto ofrece, de una forma ágil, sencilla y operativa, mediante un sistema de información, facilitar que las organizaciones tengan un punto común para registrar todas sus evidencias sin interferir en los procedimientos y procesos internos corporativos y los específicos de cada proyecto.");
        return "index";
    }
}