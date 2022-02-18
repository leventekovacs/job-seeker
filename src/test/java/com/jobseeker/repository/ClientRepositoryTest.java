package com.jobseeker.repository;

import com.jobseeker.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired private ClientRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canFindByEmail() {
        //given
        underTest.save(new Client("Test Client", "test.client@test.com"));
        //when
        boolean expected = underTest.findByEmail("test.client@test.com").isPresent();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findByEmailThatDoesNotExist() {
        //given
        underTest.save(new Client("Test Client", "test.client@test.com"));
        //when
        boolean expected = underTest.findByEmail("test@test.com").isPresent();
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void canFindByUUID() {
        //given
        Client client = new Client("Test Client", "test.client@test.com");
        underTest.save(client);
        //when
        boolean expected = underTest.existsByUuid(client.getUuid());
        //then
        assertThat(expected).isTrue();
    }

}