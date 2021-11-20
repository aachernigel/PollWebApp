package web;

import PollManagerLib.PollManager;
import PollManagerLib.PollStatus;
import PollManagerLib.PollWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBPollGateway {
    public static DBPollGateway dbPoll = new DBPollGateway();
    private PreparedStatement preparedStatement;

    DBPollGateway() {
        DBConnection.getConnection();
    }

    public void updateStatus(PollStatus status) {
        try {
            preparedStatement = DBConnection.connection.prepareStatement("UPDATE poll SET status = ? WHERE pollID = ?");
            preparedStatement.setString(1, PollWrapper.manager.getStatus().toString());
            preparedStatement.setString(2, PollWrapper.manager.getPollID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVote(String pollID, String sessionID, String pin, String choice, LocalDateTime dateTime) {
        try {
            preparedStatement = DBConnection.connection.prepareStatement(
                    "INSERT INTO vote (pollID, sessionID, pin, choice, dateTime) VALUES (?,?,?,?,?)"
            );
            preparedStatement.setString(1, pollID);
            preparedStatement.setString(2, sessionID);
            preparedStatement.setString(3, pin);
            preparedStatement.setString(4, choice);
            preparedStatement.setString(5, dateTime.format(PollManager.formatter));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVote(String pollID, String pin, String choice, LocalDateTime dateTime) {
        try {
            preparedStatement = DBConnection.connection.prepareStatement(
                    "UPDATE vote SET choice = ?, dateTime = ? WHERE pollID = ? AND pin = ?"
            );
            preparedStatement.setString(1, choice);
            preparedStatement.setString(2, dateTime.format(PollManager.formatter));
            preparedStatement.setString(3, pollID);
            preparedStatement.setString(4, pin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVotes(){
        try {
            preparedStatement = DBConnection.connection.prepareStatement(
                    "DELETE FROM vote WHERE pollID = ?"
            );
            preparedStatement.setString(1, PollWrapper.manager.getPollID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePoll(){
        try{
            preparedStatement = DBConnection.connection.prepareStatement(
                    "DELETE FROM poll WHERE pollID = ?"
            );
            preparedStatement.setString(1, PollWrapper.manager.getPollID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePoll(){
        try{
            preparedStatement = DBConnection.connection.prepareStatement(
                    "UPDATE poll SET name = ?, question = ?, status = ?, option1 = ?, option2 = ?, option3 = ?, " +
                            "option4 = ? , option5 = ?, option6 = ?, option7 = ?," +
                            " option8 = ?, option9 = ?, option10 = ? WHERE pollID = ?"
            );
            preparedStatement.setString(1, PollWrapper.manager.getName());
            preparedStatement.setString(2, PollWrapper.manager.getQuestion());
            preparedStatement.setString(3, PollWrapper.manager.getStatus().toString());
            int index = 0;
            for(int i = 4; i <= 13; i++){
                if(index < PollWrapper.manager.getChoices().length){
                    preparedStatement.setString(i, PollWrapper.manager.getChoices()[index].getDescription());
                    index++;
                } else{
                    preparedStatement.setString(i, null);
                }
            }
            preparedStatement.setString(14, PollWrapper.manager.getPollID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getListOfPolls(String creatorID){
        try{
            preparedStatement = DBConnection.connection.prepareStatement(
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
