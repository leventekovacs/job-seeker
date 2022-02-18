package com.jobseeker.controller;

import com.jobseeker.model.Position;
import com.jobseeker.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    private PositionService positionService;

    @Autowired
    public void setJobService(PositionService jobService) {
        this.positionService = jobService;
    }

    @GetMapping("/{id}")
    public Position getPosition(@PathVariable Long id) {
        return positionService.getPosition(id);
    }

    @GetMapping("/search")
    public List<String> searchPosition(
            @RequestParam() String keyword,
            @RequestParam() String location) {
        return positionService.getFilteredUrls(keyword,location);
    }

    @PostMapping()
    public String registratePosition(@RequestBody Position position) {
        positionService.addPosition(position);
        return positionService.getURL(position);
    }

}
