package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<User> myList = userService.findAll();
        for (User u : myList){
            System.out.println(u.getUserid() + " " + u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUserById() {
        assertEquals("test cinnamon", userService.findUserById(7).getUsername());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void findUserByIdNotFound(){
        assertEquals("test cinnamon", userService.findUserById(70).getUsername());
    }

    @Test
    public void findByNameContaining() {
        assertEquals(1, userService.findByNameContaining("cin").size());
    }

    @Test
    public void findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete() {
        userService.delete(14);
        assertEquals("test misskitty", userService.findUserById(14));
    }

    @Test
    public void findByName() {
        assertEquals("test cinnamon", userService.findByName("test cinnamon").getUsername());
    }

    @Test
    public void save() {
        String userName = "Test Username";
        User u2 = new User(userName,
                "2222222",
                "test@test.com");
        u2.getUseremails().add(new Useremail(u2, "anotheremail@email.com"));
        User addUser = userService.save(u2);
        assertNotNull(addUser);
        assertEquals(userName.toLowerCase(), addUser.getUsername());
    }

    @Test
    public void update() {
        String userName = "test username";
        User u2 = new User(userName,
                "2222222",
                "test@test.com");
        u2.setUserid(4);
        u2.getUseremails().add(new Useremail(u2, "anotheremail@email.com"));
        userService.update(u2, u2.getUserid());
        User addUser = userService.save(u2);
        assertNotNull(addUser);
        assertEquals(userName, addUser.getUsername());
    }

    @Test
    public void deleteAll() {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}
