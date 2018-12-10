/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.Person;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author idoia
 */
public class GestionListaEnMemoria {

    private static Configuration config = new Configuration();
    private static SessionFactory sessionFactory = config.configure().buildSessionFactory();

    public static ObservableList<Person> observableORM() {
        ObservableList<Person> data = FXCollections.observableArrayList();
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            for (Iterator<Person> iPerson = session.createQuery("FROM Ikasleak").iterate(); iPerson.hasNext();) {
                Person person = (Person) iPerson.next();
                Hibernate.initialize(person);

                data.add(person);
            }

            if (session != null) {
                session.close();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return data;
    }

    public static void insertORM(String izena, String abizena, String korreoa) {
        Session session = null;
        session = sessionFactory.openSession();
        session.createQuery("INSERT INTO `ikasleak`(`izena`, `abizena`, `korreoa`) "
                + "VALUES ('" + izena + "', '" + abizena + "', '" + korreoa + "')");
        session.close();
    }

    public static void deleteORM(Person person) {
        Session session = null;
        session = sessionFactory.openSession();
        session.createQuery("DELETE FROM Ikasleak WHERE id =" + person.getId());
        session.close();
    }

    public static void updateORM(int i, String newValue, int id) {
        Session session = null;
        session = sessionFactory.openSession();
        switch (i) {
            case 1:
                session.createQuery("UPDATE `ikasleak` SET `izena`= '" + newValue + "' WHERE `id` = " + id);
                break;
            case 2:
                session.createQuery("UPDATE `ikasleak` SET `abizena`= '" + newValue + "'  WHERE `id` = " + id);
                break;
            case 3:
                session.createQuery("UPDATE `ikasleak` SET `korreoa`= '" + newValue + "'  WHERE `id` = " + id);
                break;
        }
        session.close();
    }
}
