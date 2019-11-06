/*
 * Copyright (C) 2019 chromsta
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author chromsta
 */
public class ChurchRecords {
    private final static Logger LOGGER =  
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

     /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        InputStream inputStream;
        Properties prop = new Properties();
        try {
            String propFileName = "configuration.properties";
            
            ChurchRecords main = new ChurchRecords();
            inputStream = main.getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
                inputStream.close();
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return;
	}
        try {
            FileReader fileReader = new FileReader();
            People people = fileReader.readWorkbook(prop);
        } catch (IOException ex) {
            Logger.getLogger(ChurchRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ChurchRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ChurchRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
