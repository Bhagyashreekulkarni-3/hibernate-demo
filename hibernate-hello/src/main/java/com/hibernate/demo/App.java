package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.demo.entity.Student;
import com.hibernate.demo.utility.HibernateUtil;

public class App {
	public static void main(String[] args) {

		Student student = new Student("Bhagyashree", "Kulkarni", "bkulkarni@gmail.com");
		Student student1 = new Student("John", "Cena", "john@gmail.com");
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student objects
			session.save(student);
			session.save(student1);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<Student> students = session.createQuery("from Student", Student.class).list();
			students.forEach(s -> {
				System.out.println("Print student email id : " + s.getEmail());
			});
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
}