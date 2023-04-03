package ru.javarush.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javarush.entity.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryImplTest {

    SessionFactory sessionFactory;

    @BeforeEach
    public void init() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    @AfterEach
    public void sessionClose() {
        sessionFactory.close();
    }

    @Test
    void shouldReturnCountOfTasks() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        session.persist(task1);
        session.persist(task2);
        session.persist(task3);
        transaction.commit();

        List<Task> tasks =
                session.createQuery("FROM Task", Task.class).list();
        assertEquals(3, tasks.size());
        session.close();
    }

    @Test
    void shouldReturnTaskById() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Task task1 = new Task();
        Task task2 = new Task();
        task2.setDescription("TEST");
        Task task3 = new Task();

        session.persist(task1);
        session.persist(task2);
        session.persist(task3);
        transaction.commit();

        Task tasks =
                session.createQuery("FROM Task where id = 2", Task.class).getSingleResult();
        assertEquals("TEST", tasks.getDescription());
        session.close();
    }

}