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

import java.util.List;

/**
 * Class storing information about people of the church
 * @author standaCh
 */
public class People {
        List<Person> members;
        List<Person> children;
        
    /**
     * Class constructor
     * @param m List of members
     * @param ch List of children
     */
    public People(List<Person> m, List<Person> ch)
        {
            members = m;
            children = ch;
        }
        
    /**
     *
     * @return List of children of the church
     */
    public List<Person> getChildren()
        {
            return children;
        }
        
    /**
     *
     * @return List of members of the church
     */
    public List<Person> getMembers()
        {
            return members;
        }
    
}
