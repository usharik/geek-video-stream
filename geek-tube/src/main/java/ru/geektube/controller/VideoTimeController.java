package ru.geektube.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class VideoTimeController {

    @PostMapping("/savetime")
    public void saveCurrentTime(@RequestParam("currentTime") String currentTime, HttpSession session) {
        session.setAttribute("currentTime", currentTime);
    }

    @GetMapping("/currenttime")
    public String getCurrentTime(HttpSession session) {
        return (String) session.getAttribute("currentTime");
    }

    @PostMapping("/savestate")
    public void sendEvent(@RequestParam("state") String state, HttpSession session) {
        session.setAttribute("state", state);
    }

    @GetMapping("/currentstate")
    public String getCurrentState(HttpSession session) {
        return (String) session.getAttribute("state");
    }
}
