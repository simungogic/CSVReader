package com.csv.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {
	
	@SuppressWarnings("deprecation")
	public static void insert(User user) {
		Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
	    }
	    catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

	}
	
	public static List<User> searchAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
            List<User> users = session.createQuery("from User", User.class).list();
            return users;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
}	
