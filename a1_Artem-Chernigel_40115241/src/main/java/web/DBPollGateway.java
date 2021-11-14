package web;

import PollManagerLib.PollStatus;
import PollManagerLib.PollWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBPollGateway {
    public static DBPollGateway dbPoll = new DBPollGateway();
    private PreparedStatement preparedStatement;

    DBPollGateway() {
        DBConnection.getConnection();
    }

    public void updateStatus(PollStatus status) {
        try {
            preparedStatement = DBConnection.conn.prepareStatement("UPDATE poll SET status = ? WHERE pollID = ?");
            preparedStatement.setString(1, PollWrapper.manager.getStatus().toString());
            preparedStatement.setString(2, PollWrapper.manager.getPollID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVote(String pollID, String sessionID, String pin, String choice) {
        try {
            preparedStatement = DBConnection.conn.prepareStatement(
                    "INSERT INTO vote (pollID, sessionID, pin, choice) VALUES (?,?,?,?)"
            );
            preparedStatement.setString(1, pollID);
            preparedStatement.setString(2, sessionID);
            preparedStatement.setString(3, pin);
            preparedStatement.setString(4, choice);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVote(String pollID, String pin, String choice) {
        try {
            preparedStatement = DBConnection.conn.prepareStatement(
                    "UPDATE vote SET choice = ? WHERE pollID = ? AND pin = ?"
            );
            preparedStatement.setString(1, choice);
            preparedStatement.setString(2, pollID);
            preparedStatement.setString(3, pin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePoll(){
        try{
            preparedStatement = DBConnection.conn.prepareStatement(
                    "DELETE FROM poll WHERE pollID = ?"
            );
            preparedStatement.setString(1, PollWrapper.manager.getPollID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getListOfPolls(String creatorID){
        try{
            preparedStatement = DBConnection.conn.prepareStatement(
                    "SELECT * FROM poll WHERE creatorID = ?"
            );
            preparedStatement.setString(1, creatorID);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
