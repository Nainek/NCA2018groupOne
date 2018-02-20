package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Profile("!prod")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    @Rollback
    public void insertUserTest() {
        User expected = new RealUser();
        expected.setEmail("junitEmail@gmail.com");
        expected.setFirstName("junitFirstName");
        expected.setLastName("junitLastName");
        expected.setPassword("junitPass");
        expected.setPhoneNumber("0506078105");
        expected.setRegistrationDate(LocalDateTime.now());
        expected.setAddress(addressDao.findById(10L));
        expected.setManager(userDao.findById(6L));

        userDao.create(expected);
        User actual = userDao.findById(expected.getId());

        Assert.assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void insertUserWithNullsTest() {
        User expected = new RealUser();
        expected.setEmail("junitEmail@gmail.com");
        expected.setFirstName("junitFirstName");
        expected.setLastName("junitLastName");
        expected.setPassword("junitPass");
        expected.setRegistrationDate(LocalDateTime.now());

        userDao.create(expected);
        User actual = userDao.findByEmail("junitEmail@gmail.com");

        Assert.assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void updateUserTest() {
        User expected = userDao.findById(10L);
        expected.setEmail("junitUpdateEmail@gmail.com");
        expected.setFirstName("junitFirstName");
        expected.setLastName("junitLastName");
        expected.setPassword("junitPass");
        expected.setPhoneNumber("05034278105");
        expected.setAddress(addressDao.findById(10L));
        expected.setManager(userDao.findById(6L));
        expected.setRegistrationDate(LocalDateTime.now());
        userDao.update(expected);
        User actual = userDao.findByEmail("junitUpdateEmail@gmail.com");

        Assert.assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserTest() {
        User expected = new RealUser();
        expected.setEmail("junitEmail@gmail.com");
        expected.setFirstName("junitFirstName");
        expected.setLastName("junitLastName");
        expected.setPassword("junitPass");
        expected.setPhoneNumber("0506078105");
        expected.setRegistrationDate(LocalDateTime.now());

        User actual = userDao.create(expected);
        userDao.delete(actual);

        Assert.assertNull(userDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findUserByEmailTest() {
        String expected = "admin1@mail.com";
        User actual = userDao.findByEmail(expected);

        log.info("Fetched user by email: {}", actual.getEmail());
        Assert.assertEquals(expected, actual.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void findUserByIdTest() {
        Long expected = 11L;
        User actual = userDao.findById(expected);

        log.info("Fetched User by id: {}", actual);
        Assert.assertEquals(expected, actual.getId());
    }

}
