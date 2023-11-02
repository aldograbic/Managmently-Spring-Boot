package com.project.Managmently.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeaturesController {

    @GetMapping("/features")
    public String getFeaturesPage() {
        return "features";
    }
}
