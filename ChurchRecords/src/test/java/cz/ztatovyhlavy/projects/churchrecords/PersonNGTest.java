/*
 * Copyright (C) 2018 standaCh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.ztatovyhlavy.projects.churchrecords;

import java.time.LocalDate;
import java.time.Period;
import static org.testng.Assert.*;

/**
 *
 * @author standaCh
 */
public class PersonNGTest {
    
    static Person instance;
    
    public PersonNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
        instance = new Person("FirstName", "Surname", "2000-01-02", "2003-04-05", "2006-07-08");
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of toString method, of class Person.
     */
    @org.testng.annotations.Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Person{name=FirstName, surname=Surname, dateOfBirth=2000-01-02, dateOfMarriage=2003-04-05, startOfMembership=2006-07-08}";
        String result = instance.toString();
        assertEquals(result, expResult);
    }

    /**
     * Test of getYearOfBirth method, of class Person.
     */
    @org.testng.annotations.Test
    public void testGetYearOfBirth() {
        System.out.println("getYearOfBirth");
        int expResult = 2000;
        int result = instance.getYearOfBirth();
        assertEquals(result, expResult);
        
        Person secretPerson = new Person(null, null, null, "1900-01-01", "2000-01-01");
        expResult = 0;
        result = secretPerson.getYearOfBirth();
        assertEquals(result, expResult);        
    }

    /**
     * Test of getMonthOfBirth method, of class Person.
     */
    @org.testng.annotations.Test
    public void testGetMonthOfBirth() {
        System.out.println("getMonthOfBirth");
        int expResult = 1;
        int result = instance.getMonthOfBirth();
        assertEquals(result, expResult);
    }

    /**
     * Test of getDayOfBirth method, of class Person.
     */
    @org.testng.annotations.Test
    public void testGetDayOfBirth() {
        System.out.println("getDayOfBirth");
        int expResult = 2;
        int result = instance.getDayOfBirth();
        assertEquals(result, expResult);
    }

    /**
     * Test of getStartOfMembership method, of class Person.
     */
    @org.testng.annotations.Test
    public void testGetStartOfMembership() {
        System.out.println("getStartOfMembership");
        LocalDate expResult = LocalDate.parse("2006-07-08");
        LocalDate result = instance.getStartOfMembership();
        assertEquals(result, expResult);
    }

    /**
     * Test of getCurrentAge method, of class Person.
     */
    @org.testng.annotations.Test
    public void testGetCurrentAge() {
        System.out.println("getCurrentAge");
        int expResult = Period.between(LocalDate.parse("2000-01-02"), LocalDate.now()).getYears();
        int result = instance.getCurrentAge();
        assertEquals(result, expResult);
        
        Person birthdayTommorow = new Person(null, null, LocalDate.now().plusDays(1), null, null);
        expResult = 0;
        result = birthdayTommorow.getCurrentAge();
        assertEquals(result, expResult);
        
        Person firstBirthdayTommorow = new Person(null, null, LocalDate.now().plusDays(1).minusYears(1), null, null);
        expResult = 0;
        result = firstBirthdayTommorow.getCurrentAge();
        assertEquals(result, expResult);

        Person thirdBirthdayTommorow = new Person(null, null, LocalDate.now().plusDays(1).minusYears(3), null, null);
        expResult = 2;
        result = thirdBirthdayTommorow.getCurrentAge();
        assertEquals(result, expResult);
        
        Person birthdayYesterday = new Person(null, null, LocalDate.now().minusDays(1), null, null);
        expResult = 0;
        result = birthdayYesterday.getCurrentAge();
        assertEquals(result, expResult);

        Person firstBirthdayYesterday = new Person(null, null, LocalDate.now().minusDays(1).minusYears(1), null, null);
        expResult = 1;
        result = firstBirthdayYesterday.getCurrentAge();
        assertEquals(result, expResult);

        Person birthdayToday = new Person(null, null, LocalDate.now().minusYears(20), null, null);
        expResult = 20;
        result = birthdayToday.getCurrentAge();
        assertEquals(result, expResult);
    }
    
}
