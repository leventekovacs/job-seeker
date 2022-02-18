package com.jobseeker.service;

import com.jobseeker.model.Client;
import com.jobseeker.repository.ClientRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService underTest;

    Client client;

    @BeforeEach
    void setUp() {
        client = new Client("Test Client", "test.client@test.com");
    }

    @AfterEach
    void tearDown() {
        client = null;
    }

    @Test
    void canAddClient() {
        //when
        underTest.addClient(client);
        //then
        ArgumentCaptor<Client> contactArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(contactArgumentCaptor.capture());
        assertThat(contactArgumentCaptor.getValue()).isEqualTo(client);
    }

    @Test
    @Description("the given name is 101 characters long")
    void willThrowWhenNameIsTooLong() {
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidName("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The given name is too long.");

        verify(clientRepository, never()).save(any());
    }

    @Test
    void willThrowWhenEmailDoesNotSuitSyntax() {
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidEmail("invalidemail"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The given email does not suit syntax.");


        verify(clientRepository, never()).save(any());
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        given(clientRepository.findByEmail(client.getEmail()))
                .willReturn(Optional.of(client));
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidEmail("test.client@test.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The given email is taken.");

        verify(clientRepository, never()).save(any());
    }

}