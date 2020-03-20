package dao;


import com.andersenlab.App;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class,
        classes = App.class)
abstract public class AbstractDaoTest {
}
