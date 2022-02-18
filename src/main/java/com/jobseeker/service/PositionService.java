package com.jobseeker.service;

import com.jobseeker.model.Position;
import com.jobseeker.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    @Autowired
    public void setPositionRepository(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position getPosition(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(new Supplier<IllegalArgumentException>() {
                    @Override
                    public IllegalArgumentException get() {
                        return new IllegalArgumentException("Position not found.");
                    }
                });
    }

    public List<Position> getPositions(String keyword, String location) {
        return positionRepository.findAll().stream().filter(
            position ->
                (keyword.isEmpty() ? true
                    : position.getName().toLowerCase().contains(keyword.toLowerCase()))
             && (location.isEmpty() ? true
                    : position.getLocation().toLowerCase().equals(location.toLowerCase())))
                .collect(Collectors.toList());
    }

    public String getURL(Position position) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build()
                .toUriString() + "/position/" + position.getId();
    }

    public List<String> getFilteredUrls(String keyword, String location) {
        return getPositions(keyword,location).stream()
                .map(this::getURL)
                .collect(Collectors.toList());
    }

    public void addPosition(Position position) {
        if(isValidName(position.getName()) && isValidLocation(position.getLocation())) {
            positionRepository.save(position);
        }
    }

    protected boolean isValidName(String name) {
        if(name.length() > 50) throw new IllegalArgumentException("The given name is too long.");
        return true;
    }

    protected boolean isValidLocation(String location) {
        if(location.length() > 50) throw new IllegalArgumentException("The given location is too long.");
        return true;
    }

}
