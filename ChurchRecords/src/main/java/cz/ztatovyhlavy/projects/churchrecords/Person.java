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


import java.util.Calendar;
import java.time.LocalDate;
import java.util.Comparator;


/**
 * Class storing information about one person
 * @author standaCh
 */
public class Person {
    String name;
    String surname;
    LocalDate dateOfBirth;
    LocalDate dateOfMarriage;
    LocalDate startOfMemberhsip;
    int age;
    int anniversary;

    /**
     * Class constructor
     * @param name
     * @param surname
     * @param dateOfBirth
     * @param dateOfMarriage
     * @param startOfMemberhsip
     */
    public Person(String name, String surname, LocalDate dateOfBirth, LocalDate dateOfMarriage, LocalDate startOfMemberhsip) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.dateOfMarriage = dateOfMarriage;
        this.startOfMemberhsip = startOfMemberhsip;
        this.anniversary = 0;
        this.age =  Calendar.getInstance().get(Calendar.YEAR) - 1900 - this.dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", surname=" + surname + ", dateOfBirth=" + dateOfBirth + ", dateOfMarriage=" + dateOfMarriage + '}';
    }
    
    /**
     * Get year of birth
     * @return Year when person was born
     */
    public int getYearOfBirth() {
        return dateOfBirth.getYear();
    }
    
    /**
     * Get month of birth
     * @return Month when person was born
     */
    public int getMonthOfBirth() {
        return dateOfBirth.getMonthValue();
    }
    
    /**
     * Get day in month of birth
     * @return Day when person was born
     */
    public int getDayOfBirth() {
        return dateOfBirth.getDayOfMonth();
    }
    
    /**
     * Get date when person has became member of the church
     * @return Date of membership start
     */
    public LocalDate getStartOfMembership() {
        return startOfMemberhsip;
    }
    
    /**
     * Get current age of the person
     * @return Current age
     */
    public int getCurrentAge() {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        if (currentMonth < this.getMonthOfBirth())
        {
            return age-1;
        } else if (currentMonth > this.getMonthOfBirth())
        {
            return age;
        } else
        {
            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < this.getDayOfBirth())
            {
                return age-1;
            } else
            {
                return age;
            }
        }
    }
    
    private static int compareDates(LocalDate date1, LocalDate date2)
    {
        int month1 = date1.getMonthValue(); 
        int month2 = date2.getMonthValue();

        if(month1 < month2)  
            return -1;
        else if(month1 == month2)
            return date1.getDayOfMonth() - date2.getDayOfMonth();
        else
            return 1;    
    }
    
    /**
     * Compare two people based on date of birth in the year, regardless the age
     */
    public static Comparator<Person> BirthdayComparator = new Comparator<Person>()
    {
        @Override
        public int compare(Person p1, Person p2)
        {
            return compareDates(p1.dateOfBirth, p2.dateOfBirth);
        }
    };

    /**
     * Compare two people based on the age
     */
    public static Comparator<Person> AgeComparator = new Comparator<Person>()
    {
        @Override
        public int compare(Person p1, Person p2)
        {
            return p1.dateOfBirth.compareTo(p2.dateOfBirth);
            /*
            if (p1.getCurrentAge() < p2.getCurrentAge()) {
                return -1;
            } else if (p1.getCurrentAge() > p2.getCurrentAge()) {
                return 1;
            } else {
                // return younger first
                if (p1.getYearOfBirth() < p2.getYearOfBirth())
                {
                    return 1;
                } else if (p1.getYearOfBirth() > p2.getYearOfBirth())
                {
                    return -1;
                }
                else
                {
                    if (p1.getMonthOfBirth() < p2.getMonthOfBirth())
                    {
                        return 1;
                    } else if (p1.getMonthOfBirth() > p2.getMonthOfBirth())
                    {
                        return -1;
                    } else {
                        return Integer.compare(p2.getDayOfBirth(), p1.getDayOfBirth());
                    }
                }
            }
            */
        }
    };

    /**
     * Compare two people based on wedding anniversary in the year, regardless of marriage duration
     */
    public static Comparator<Person> AnniversaryComparator = new Comparator<Person>()
    {
        @Override
        public int compare(Person p1, Person p2)
        {
            return compareDates(p1.dateOfMarriage, p2.dateOfMarriage);
        }
    };
}
