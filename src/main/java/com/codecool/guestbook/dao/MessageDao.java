package com.codecool.guestbook.dao;

import com.codecool.guestbook.models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao implements Dao<Message> {

    private Connection connection;

    @Override
    public List<Message> getAll() {
        List<Message> allMessages = new ArrayList<>();
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM records ORDER BY date;");
            createMessagesFromResultSet(resultSet, allMessages);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMessages;
    }

    private void createMessagesFromResultSet(ResultSet resultSet, List<Message> allMessages)
            throws SQLException {
        while(resultSet.next()) {
            allMessages.add(new Message(resultSet.getString("message"),
                    resultSet.getString("name"),
                    resultSet.getString("date")));
        }
    }

    @Override
    public boolean save(Message message) {
        try {
            connect();
            PreparedStatement preparedStatement = prepareStatementFromMessage(message);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private PreparedStatement prepareStatementFromMessage(Message message) throws SQLException {
        String queryTemplate = "INSERT INTO records (message, name, date)" +
                "VALUES (?, ?, CURRENT_TIMESTAMP);";
        PreparedStatement preparedStatement = connection.prepareStatement(queryTemplate);
        preparedStatement.setString(1, message.getMessage());
        preparedStatement.setString(2, message.getName());
        return preparedStatement;
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/guestbook",
                "grzegorz", "29458173");
        System.out.println("Opened database successfully");
    }

}
