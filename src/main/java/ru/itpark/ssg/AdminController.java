package ru.itpark.ssg;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String hello(@AuthenticationPrincipal User user){

        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();


        return "I am admin";
    }
}
