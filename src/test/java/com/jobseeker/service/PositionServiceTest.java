package com.jobseeker.service;

import com.jobseeker.model.Position;
import com.jobseeker.repository.PositionRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {
    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionService underTest;

    Position position;

    @BeforeEach
    void setUP() {
        position = new Position("Java Developer","Budapest");
    }

    @AfterEach
    void tearDown() {
        position = null;
    }

    @Test
    void canGetPosition() {
        //when
        given(positionRepository.findById(position.getId())).
                willReturn(Optional.of(position));
        //then
        underTest.getPosition(position.getId());
        verify(positionRepository).findById(position.getId());
    }

    @Test
    void willThrowWhenPositionDoesNotExist() {
        //when
        underTest.addPosition(position);
        //then
        assertThatThrownBy(() -> underTest.getPosition(position.getId())).
                hasMessageContaining("Position not found.");
    }

    @Test
    void willReturnAllPositionWhenParametersAreEmpty() {
        //when
        underTest.getPositions("","");
        //then
        verify(positionRepository).findAll();
    }

    @Test
    void canAddPosition() {
        //when
        underTest.addPosition(position);
        //then
        ArgumentCaptor<Position> contactArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(positionRepository).save(contactArgumentCaptor.capture());
        assertThat(contactArgumentCaptor.getValue()).isEqualTo(position);
    }

    @Test
    @Description("the given name is 51 characters long")
    void willThrowWhenNameIsTooLong() {
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidName("Lorem ipsum dolor sit amet, consectetuer adipiscing"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The given name is too long.");

        verify(positionRepository, never()).save(any());
    }

    @Test
    @Description("the given name is 51 characters long")
    void willThrowWhenLocationIsTooLong() {
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidLocation("Lorem ipsum dolor sit amet, consectetuer adipiscing"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The given location is too long.");

        verify(positionRepository, never()).save(any());
    }

    @Test
    void canBeFiltered() {
        //given
        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position("Backend Developer", "Berlin"));
        positionList.add(new Position("DevOps Engineer", "Szeged"));
        positionList.add(new Position("Java Developer", "Budapest"));
        positionList.add(new Position("Full Stack Developer", "Debrecen"));
        positionList.add(new Position("DevOps Engineer", "Budapest"));
        positionList.add(new Position("Senior Java Developer", "Budapest"));
        positionList.add(new Position("Senior PHP Developer", "Budapest"));
        positionList.add(new Position("Senior PHP Developer", "Berlin"));
        given(positionRepository.findAll())
                .willReturn(positionList);
        //when
        //then
        assertThat(underTest.getPositions("","").size()).isEqualTo(8);
        assertThat(underTest.getPositions("java","").size()).isEqualTo(2);
        assertThat(underTest.getPositions("ops","").size()).isEqualTo(2);
        assertThat(underTest.getPositions("","budapest").size()).isEqualTo(4);
        assertThat(underTest.getPositions("","Berlin").size()).isEqualTo(2);
        assertThat(underTest.getPositions("Senior","Budapest").size()).isEqualTo(2);
        assertThat(underTest.getPositions("developer","budapest").size()).isEqualTo(3);
    }
}