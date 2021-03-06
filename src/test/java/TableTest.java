package db61b;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TableTest {

    @Test
    public void testDuplicateColumns() {
        try {
        String[] columnTitles = new String[]{"SID", "SID", "Major"};
        Table table = new Table(columnTitles); }
        catch (DBException e){
            System.out.print(e);
        }

    }

    @Test
    public void testGetTitle() {
        String[] columnTitles = new String[]{"SID", "Name", "Major"};
        Table table = new Table(columnTitles);
        String actual = table.getTitle(1);
        String expected = "Name";
        assertEquals(expected, actual);
    }

    @Test
    public void testFindColumn() {
        String[] columnTitles = new String[]{"SID", "Name", "Major"};
        Table table = new Table(columnTitles);
        int actual = table.findColumn("SID");
        int expected = 0;
        assertEquals(expected, actual);

        int actual1 = table.findColumn("LastName");
        int expected1 = -1;
        assertEquals(expected1, actual1);
    }

    @Test
    public void testSize() {
        String[] columnTitles = new String[]{"SID", "Name", "Major"};
        Table table = new Table(columnTitles);
        int actual = table.size();
        int expected = 0;
        assertEquals(expected, actual);

        String[] values = new String[]{"1", "Alice", "MIMS"};
        table.add(values);
        int actual1 = table.size();
        int expected1 = 1;
        assertEquals(expected1, actual1);

        String[] values1 = new String[]{"2", "Jason", "EECS"};
        table.add(values1);
        int actual2 = table.size();
        int expected2 = 2;
        assertEquals(expected2, actual2);
    }

    @Test
    public void testGet() {
        String[] columnTitles = new String[]{"SID", "Name", "Major"};
        Table table = new Table(columnTitles);

        String[] values = new String[]{"1", "Alice", "MIMS"};
        table.add(values);
        String[] values1 = new String[]{"2", "Jason", "EECS"};
        table.add(values1);
        String actual = table.get(0, 0);
        String expected = "1";
        assertEquals(expected, actual);

    }

    @Test
    public void testAdd() {
        String[] columnTitles = new String[]{"SID", "Name", "Major"};
        Table table = new Table(columnTitles);
        String[] values = new String[]{"1", "Alice", "MIMS", "CS61B"};
        assertFalse(table.add(values));

    }

    @Test
    public void selectFromStudentTableWhereNameIsAlice(){
        Table students = createStudentTalbe();

        List<String> colList = new ArrayList<>();
        colList.add("Name");
        colList.add("Major");
        List<Condition> conditionList = new ArrayList<>();

        Condition con = new Condition(new Column("Name",students),new String
                ("="),new String("Alice"));
        conditionList.add(con);
        Table expectedResult = students.select(colList,conditionList);

        assertEquals("Alice",expectedResult.get(0,0));
    }

    @Test
    public void selectFromStudentAndEnrollTableWhoTakeCertainClass(){
        Table students = createStudentTalbe();
        Table enroll = createEnrollTable();

        List<String> colList = new ArrayList<>();
        colList.add("Name");
        colList.add("Grade");
        List<Condition> conditionList = new ArrayList<>();

        Condition con = new Condition(new Column("SID",students),new String
                ("="),new Column("SID",enroll));
        Condition con1 = new Condition(new Column("CCN",enroll ),new String
                ("="),new String("004"));
        conditionList.add(con);
        conditionList.add(con1);
        Table expectedResult = students.select(enroll,colList,conditionList);

        assertEquals("A",expectedResult.get(0,1));
        assertEquals("Alice",expectedResult.get(0,0));
    }

    @Test
    public void selectTableWihtoutCondition(){
        Table students = createStudentTalbe();
        Table enroll = createEnrollTable();
        List<String> colList = new ArrayList<>();
        colList.add("Name");
        colList.add("Grade");

        List<Condition> conditionList = new ArrayList<>();
        Table expectedResult = students.select(enroll,colList,conditionList);
        assertEquals(4,expectedResult.size());

    }

    private Table createStudentTalbe(){
        String[] columnTitles = new String[]{"SID", "Name", "Major"};
        Table stduents = new Table(columnTitles);
        String[] values = new String[]{"1", "Alice", "MIMS"};
        stduents.add(values);
        String[] values1 = new String[]{"2", "Jason", "EECS"};
        stduents.add(values1);
        String[] values2 = new String[]{"3", "Leo", "Business"};
        stduents.add(values2);
        String[] values3 = new String[]{"4", "Cindy", "Japanese"};
        stduents.add(values3);
        return stduents;
    }

    private Table createScheduleTable(){
        String[] columnTitles = new String[]{"CCN", "Course", "Time",
                "Semester"};
        Table stduents = new Table(columnTitles);
        String[] values = new String[]{"001", "61A", "Wed","F"};
        stduents.add(values);
        String[] values1 = new String[]{"002", "61B", "Thur", "F"};
        stduents.add(values1);
        String[] values2 = new String[]{"003", "61C", "Friday","S"};
        stduents.add(values2);
        String[] values3 = new String[]{"004", "aep", "Friday","S"};
        stduents.add(values3);
        return stduents;
    }

    private Table createEnrollTable(){
        String[] columnTitles = new String[]{"CCN", "SID", "Grade"};
        Table stduents = new Table(columnTitles);
        String[] values = new String[]{"001", "1", "A+"};
        stduents.add(values);
        String[] values1 = new String[]{"002", "2", "B"};
        stduents.add(values1);
        String[] values2 = new String[]{"003", "2", "C"};
        stduents.add(values2);
        String[] values3 = new String[]{"004", "1", "A"};
        stduents.add(values3);
        return stduents;
    }

    @Test
    public void testGetFrom() {
        List<String> columnTitles = new ArrayList<>();
        columnTitles.add("ID");
        columnTitles.add("Name");
        columnTitles.add("Major");
        Table table = new Table(columnTitles);
        String[] values = new String[]{"1", "Alice", "MIMS"};
        table.add(values);
        String[] values1 = new String[]{"2", "Jason", "EECS"};
        table.add(values1);

        List<String> columnTitles1 = new ArrayList<>();
        columnTitles1.add("SID");
        columnTitles1.add("Name");
        columnTitles1.add("Nationality");
        Table table1 = new Table(columnTitles);
        String[] values3 = new String[]{"2", "Jason", "USA"};
        table1.add(values3);

        String name = "ID";
        Column c = new Column(name, table);
        String a = c.getFrom(0);
        String b = "1";
        assertEquals(b, a);

        String name1 = "Name";
        Column c1 = new Column(name1, table, table1);
        String d = c1.getFrom(1, 0);
        String e = "Jason";
        assertEquals(e, d);
    }

    @Test
    public void testGetTableFromDB() {
        Table students = createStudentTalbe();
        Table enroll = createEnrollTable();

        Database db = new Database();
        db.put("studdents",students);
        db.put("enroll",enroll);

        assertEquals(null,db.get("sdcheule"));
    }



}
