package com.TermPedia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}

//    @GetMapping("/owners/{ownerId}/pets/{petId}")
//    public Pet findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
//        // ...
//    }

//@Controller
//@RequestMapping("/owners/{ownerId}")
//public class OwnerController {
//
//    @GetMapping("/pets/{petId}")
//    public Pet findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
//        // ...
//    }
//}

//    @PostMapping(path = "/pets", consumes = "application/json")
//    public void addPet(@RequestBody Pet pet) {
//        // ...
//    }