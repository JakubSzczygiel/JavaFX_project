package sample;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class SqlHandler implements Writeable, Readable {

    static final String DATABASE_URL = "jdbc:sqlite:C:/Users/Jakub/IdeaProjects/Database/";
    static final String DATABASE_NAME = "ToDoListDatabase.db";
    static final String TABLE_NAME = "Tasks";

    @Override
    public Collection<Task> read() {
        Collection<Task> tasks = new ArrayList<>();
        try {
            Connection connection = createConnection(DATABASE_NAME);
            createTableInDatabase(connection, TABLE_NAME);
            tasks = readTasks(connection, TABLE_NAME);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private Connection createConnection(String databaseName) throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(DATABASE_URL + databaseName);
        return connection;
    }

    private Collection<Task> readTasks(Connection connection, String tableName) throws SQLException {
        Collection<Task> tasks = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", tableName));
        while (resultSet.next()) {
            String priority = resultSet.getString("PRIORITY");
            String description = resultSet.getString("DESCRIPTION");
            String status = resultSet.getString("STATUS");
            Date date = Date.valueOf(resultSet.getString("DATE"));
            int taskId = resultSet.getInt("ID");
            tasks.add(new Task(date,status, priority, description,taskId));
        }
        statement.close();
        System.out.println("Tasks read from DB: " + "\n" + tasks);
        return tasks;
    }


    @Override
    public void write(Collection<Task> tasks) {
        try {
            Connection connection = createConnection(DATABASE_NAME);
            createTableInDatabase(connection, TABLE_NAME);
            addListToDatabase(connection, tasks, TABLE_NAME);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int writeOneElement(Task task)
    {
        Collection<Task> oneTaskCollection=new ArrayList<>();
        oneTaskCollection.add(task);
        write(oneTaskCollection);
        int taskId=getLastWrittenTaskId();
        System.out.println("Following task was added to database: " + task);
        return taskId;
    }
    public void removeTask(Task task)
    {
        try {
            Connection connection = createConnection(DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("DELETE FROM Tasks WHERE id =%d",task.getTaskId()));
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void removeTable(String tableName, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(String.format("DROP TABLE IF EXISTS %s", tableName));
        statement.close();
    }

    private void createTableInDatabase(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s " +
                "(ID INTEGER PRIMARY KEY," +
                " DATE DATE, " +
                " STATUS VARCHAR(10), " +
                " PRIORITY VARCHAR(10), " +
                " DESCRIPTION VARCHAR)", tableName));
        statement.close();
    }


    private void addListToDatabase(Connection connection, Collection<Task> tasks, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        for (Task task : tasks
        ) {

            String date = "\"" + task.getDate().toString() + "\"";
            String priority = "\"" + task.getPriority() + "\"";
            String description = "\"" + task.getDescription() + "\"";
            String status = "\"" + task.getStatus() + "\"";
            statement.executeUpdate(String.format("INSERT INTO %s (DATE, STATUS, PRIORITY, DESCRIPTION) " +
                    "VALUES (%s, %s, %s, %s)", tableName, date, status, priority, description));
        }
        statement.close();
    }

    public int getLastWrittenTaskId() {
        int taskID=0;
        try {
            Connection connection = createConnection(DATABASE_NAME);
            Statement statement = connection.createStatement();
            ResultSet resultSet =statement.executeQuery(String.format("SELECT MAX (ID) as MaxId FROM %s", TABLE_NAME ));
            taskID=resultSet.getInt("MaxId");
            System.out.println(taskID);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskID;
    }

    public void changeTask(int taskId, String date, String status, String priority, String description) {
        try {
            Connection connection = createConnection(DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE %s SET DATE = '%s', STATUS= '%s', PRIORITY='%s', DESCRIPTION='%s' " +
                    "WHERE ID = %d", TABLE_NAME,date,status,priority,description,taskId));
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

