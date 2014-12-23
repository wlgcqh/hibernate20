package com.qiheng.hibernate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Test2 {
	public static SessionFactory sessionFactory;

	static {
		try {
			Configuration cfg = new Configuration().configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(cfg.getProperties()).buildServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactory.openSession();
		Transaction tx1 = null;
		Transaction tx2 = null;
		// 悲观锁
		try {
			tx1 = session1.beginTransaction();
			tx2 = session2.beginTransaction();

			Student student1 = (Student) session1.createQuery("from Student")
					.setLockMode("user1", LockMode.UPGRADE).uniqueResult();
			Student student2 = (Student) session1.createQuery("from Student")
					.setLockMode("user2", LockMode.UPGRADE).uniqueResult();
			System.out.println(student1);
			System.out.println(student2);
			
			System.out.println("-------------------");
			student1.setStudent_name("张三");
			tx1.commit();

		
			System.out.println("-------------------");

			student2.setStudent_name("lisi");
			tx2.commit();

			

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session1.close();
			session2.close();
		}

	}
}
