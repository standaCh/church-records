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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author standaCh
 */
public class PeopleNGTest {

    static Person adultA = new Person("A", "B", "1990-01-02", "1923-04-05", "1967-08-09");
    static Person adultB = new Person("D", "E", "1980-12-11", null, null);
    static Person childA = new Person("F", "G", "2012-11-10", null, null);
    static Person childB = new Person("H", "CH", "2009-08-07", null, null);
    static People instance;
    
    public PeopleNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        List<Person> members = new ArrayList<>();
        members.add(adultA);
        members.add(adultB);
        List<Person> children = new ArrayList<>();
        children.add(childA);
        children.add(childB);
        instance = new People(members, children);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of getChildren method, of class People.
     */
    @Test
    public void testGetChildren() {
        System.out.println("getChildren");
        List expResult = new ArrayList<>(Arrays.asList(childA, childB));
        List result = instance.getChildren();
        assertEquals(result, expResult);
    }

    /**
     * Test of getMembers method, of class People.
     */
    @Test
    public void testGetMembers() {
        System.out.println("getMembers");
        List expResult = new ArrayList<>(Arrays.asList(adultA, adultB));
        List result = instance.getMembers();
        assertEquals(result, expResult);
    }
    
}
