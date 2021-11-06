package PollManagerLib;

public class Choice {
    private String description;
    private int choiceCounter;

    public Choice(String description){
        this.description = description;
        this.choiceCounter = 0;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getChoiceCounter(){
        return this.choiceCounter;
    }

    public void setChoiceCounter(int choiceCounter){
        this.choiceCounter = choiceCounter;
    }

    public void acceptVote(){
        this.choiceCounter++;
    }
}
