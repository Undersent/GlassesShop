package com.example.Glasses.persistence.predicate;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@WebAppConfiguration
@Transactional
public class UserPredicateTest {

    @Autowired
    private UserRepository userRepository;

    private User Bozena;
    private User Piotr;

    @Before
    public void setUp() throws Exception {
        Bozena = User.builder()
                .email("bozena@gmail.com")
                .enabled(true)
                .password("admin")
                .build();
        userRepository.save(Bozena);

        Piotr = User.builder()
                .email("piotr@gmail.com")
                .enabled(true)
                .password("qwerty")
                .build();
        userRepository.save(Piotr);
    }

    @Test
    public void givenEmail_whenGettingListOfUsers_thenCorrect(){
        final UserPredicateBuilder builder = new UserPredicateBuilder()
                .with("email", ":", "bozena@gmail.com");

        final Iterable<User> results = userRepository.findAll(builder.build());

        assertThat(results, containsInAnyOrder(Bozena));
    }

    @Test
    public void givenNotCompleteEmail_whenGettingListOfUsers_thenCorrect(){
        final UserPredicateBuilder builder = new UserPredicateBuilder()
                .with("email", ":", "@gmail.com");

        final Iterable<User> results = userRepository.findAll(builder.build());

        assertThat(results, containsInAnyOrder(Bozena, Piotr));
    }

    @Test
    public void givenWrongEmail_whenGettingListOfUsers_thenCorrect() {
        final UserPredicateBuilder builder = new UserPredicateBuilder().with("email", ":", "incorrect@gmail.com");

        final Iterable<User> results = userRepository.findAll(builder.build());
        assertThat(results, emptyIterable());
    }

}