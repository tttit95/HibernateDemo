package demo.taitt.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
	private static SessionFactory factory; 
	public static void main(String[] args) {
		 try {
	         factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		 
		 test();
		 listUser();
	}
	public static void testBatch(){
		Session session = factory.openSession();
	    Transaction tx = null;
	    try {
	          tx = session.beginTransaction();
	          for(int i = 0; i< 100;i++){
	        	  String email = "Email " + i;
	        	  String password = "Password " + i;
	        	  User user = new User(i+100,email,password);
	        	  session.save(user);
//	        	  if(i % 10 == 0){
//	        		  session.flush();
//	        		  session.clear();
//	        	  }
	          }
	          
	          tx.commit();
	       } catch (HibernateException e) {
	          if (tx!=null) tx.rollback();
	          e.printStackTrace(); 
	       } finally {
	    	  session.clear();
	          session.close(); 
	       }
	}
	public static void test(){
		Session session = factory.openSession();
	    Transaction tx = null;
	    try {
	          tx = session.beginTransaction();
	          //User user = session.get(User.class,2);
	          User user = new User(200,"User5","User5");
	          //User user1 = session.get(User.class, 5);
	          //session.evict(user);
	          System.out.println(session.contains(user));
	          user.setEmail("updateed15");
	          session.merge(user);
	          
	          System.out.println(session.contains(user));
	          
	          tx.commit();
	       } catch (HibernateException e) {
	          if (tx!=null) tx.rollback();
	          e.printStackTrace(); 
	       } finally {
	    	  session.clear();
	          session.close(); 
	       }
	      
	}
	
	public static List<User> listUser(){
		List<User> listUser = new ArrayList<User>();
		Session session = factory.openSession();
	    Transaction tx = null;
	      try {
	          tx = session.beginTransaction();
	          listUser = session.createQuery("FROM User").list(); 
	          
	          for(int i = 0; i < listUser.size(); i++){
	        	  User user = listUser.get(i);
	        	  System.out.println("ID: " + user.getId() + " - Email: " + user.getEmail());
	          }
	          tx.commit();
	       } catch (HibernateException e) {
	          if (tx!=null) tx.rollback();
	          e.printStackTrace(); 
	       } finally {
	          session.close(); 
	       }
		return listUser;
	}

}

