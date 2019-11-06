/*
 * Copyright (C) 2019 standaCh
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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Class reading workbook with church records
 * @author chromsta
 */
public class FileReader {
    private final static Logger LOGGER =  
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String NUMBER_OF_HEADER_ROWS = "pocet-radku-hlavicky";
    private static final String ROW_WITH_NAME = "cislo-sloupce-se-jmenem";
    private static final String ROW_WITH_SURNAME = "cislo-sloupce-s-prijmenim";
    private static final String ROW_WITH_BIRTHDAY = "cislo-sloupce-s-datem-narozeni";
    private static final String ROW_WITH_WEDDING_DATE = "cislo-sloupce-s-datem-svatby";
    private static final String ROW_WITH_MEMBERSHIP = "cislo-sloupce-s-datem-vstupu-do-sboru";

    /**
     * Function reads date from given cell
     * @param cell
     * @param fieldName
     * @param name
     * @param surname
     * @return List of persons
     */
    private LocalDate readDateFromCell(Cell cell, String fieldName, String name, String surname)
    {
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            case BLANK:
                return null;
            default:
                LOGGER.log(Level.SEVERE, "Error in {0} date for {1} {2}", new Object[]{fieldName, name, surname});
        }
        return null;
    }
    
    /**
     * Function reads given sheet
     * @param workbook
     * @param sheetName
     * @param skipMembership
     * @return List of persons
     */
    public List<Person> readSheet(Workbook workbook, String sheetName, Properties properties, boolean skipMembership)
    {
        Sheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> iterator = sheet.iterator();
        int rowNr = 1;
        List<Person> people = new ArrayList<>();
        while (iterator.hasNext()) {
            Row nextRow;
            nextRow = iterator.next();
            if (rowNr++ <= Integer.valueOf(properties.getProperty(NUMBER_OF_HEADER_ROWS))) continue;
            
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            
            int colNr = 1;
            String name = null;
            String surname = null;
            LocalDate birth = null;
            LocalDate marriage = null;
            LocalDate startOfMembership = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (colNr == Integer.valueOf(properties.getProperty(ROW_WITH_SURNAME)))
                {
                    surname = cell.getStringCellValue();
                }
                else if (colNr == Integer.valueOf(properties.getProperty(ROW_WITH_NAME)))
                {
                    name = cell.getStringCellValue();
                }
                else if (colNr == Integer.valueOf(properties.getProperty(ROW_WITH_BIRTHDAY)))
                {
                    birth = readDateFromCell(cell, "birthday", name, surname);
                }
                else if (colNr == Integer.valueOf(properties.getProperty(ROW_WITH_WEDDING_DATE)))
                {
                    marriage = readDateFromCell(cell, "marriage", name, surname);
                }
                else if (colNr == Integer.valueOf(properties.getProperty(ROW_WITH_MEMBERSHIP)))
                {
                    startOfMembership = readDateFromCell(cell, "start membership", name, surname);
                }
                colNr++;
                if ((skipMembership && colNr > Integer.valueOf(properties.getProperty(ROW_WITH_WEDDING_DATE)))
                        || colNr > Integer.valueOf(properties.getProperty(ROW_WITH_MEMBERSHIP)))
                {
                    break; 
                }
            }
            if (name != null && !name.isEmpty())
            {
                Person person = new Person(name, surname, birth, marriage, startOfMembership);
                people.add(person);
            }
            else
            {
                break;
            }
        }
        return people;
    }
    
    /**
     * Function reads workbook configured in given properties
     * @param properties
     * @return List of people belonging to the church
     * @throws IOException
     * @throws InvalidFormatException
     * @throws java.net.URISyntaxException
     */
    public People readWorkbook(Properties properties) throws IOException, InvalidFormatException, URISyntaxException
    {
        People people = null;
        String a = properties.getProperty("soubor");
        URL res = getClass().getClassLoader().getResource(a);
        File file = Paths.get(res.toURI()).toFile();
        String fileName = file.getAbsolutePath();
        try (Workbook workbook = WorkbookFactory.create(new File(fileName), properties.getProperty("heslo"))) {
            people = new People(readSheet(workbook, properties.getProperty("jmeno-listu-se-cleny"), properties, false), readSheet(workbook, properties.getProperty("jmeno-listu-s-detmi"), properties, true));
        }
        catch (FileNotFoundException e)
        {
            LOGGER.log(Level.SEVERE, "File {0} was not found.", fileName);
            throw e;
        }
        return people;
    }
    
}
