package com.codecool.restaurant;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByUsername_thenReturnUserApp(){
        //given
        User ion = new User("Ion", "Ioana",
                "ion", "admin@gmail.com",
                "", "", "parola");
        entityManager.persist(ion);
        entityManager.flush();
        //when
        Optional<User> found = userRepository.findByUserName(ion.getUserName());
        //then
        assertThat(found.get().getUserName()).isEqualTo(ion.getUserName());
    }
}
