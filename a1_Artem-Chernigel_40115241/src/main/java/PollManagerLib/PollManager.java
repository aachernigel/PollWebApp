package PollManagerLib;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

public class PollManager {
    private String name;
    private String question;
    private Choice[] choices;
    private PollStatus status;
    private Hashtable<Choice, Integer> choiceTable;
    private Hashtable<String, Pair<Choice, String>> participants;
    private LocalDateTime date;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss");

    public PollManager() {
        this.status = null;
    }

    public PollStatus getStatus() {
        return this.status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }

    public Choice[] getChoices() {
        return this.choices;
    }

    public String getName() {
        return this.name;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getTime() {
        date = LocalDateTime.now();
        return date.format(formatter);
    }

    private void setValues(String name, String question, Choice[] choices) throws PollException {
        if (name.equals("")) {
            throw new PollException("setValues", "Name of the Poll cannot be empty");
        } else if (question.equals("")) {
            throw new PollException("setValues", "Question of the Poll cannot be empty");
        } else if (choices == null) {
            throw new PollException("setValues", "Choices object is null");
        } else if (choices.length == 0 || choices.length == 1) {
            throw new PollException("setValues", "Choice length is less than 2");
        } else {
            for (int i = 0; i < choices.length; i++) {
                if (choices[i].getDescription().equals("")) {
                    throw new PollException("setValues", "One of the choices is empty");
                }
            }
            this.name = name;
            this.question = question;
            this.choices = new Choice[choices.length];
            for (int i = 0; i < choices.length; i++) {
                this.choices[i] = choices[i];
                this.choiceTable.put(this.choices[i], this.choices[i].getChoiceCounter());
            }
        }
    }

    public void CreatePoll(String name, String question, Choice[] choices) throws PollException {
        if (this.status == null || this.status == PollStatus.RELEASED) {
            this.status = PollStatus.CREATED;
            this.choiceTable = new Hashtable<>();
            try {
                setValues(name, question, choices);
            } catch (PollException pe) {
                System.err.println(pe);
            }
            System.out.println("Poll was successfully created");
        } else {
            throw new PollException("Create", "Status is not NULL or RELEASED");
        }
    }

    public void UpdatePoll(String name, String question, Choice[] choices) throws PollException {
        if (this.status == PollStatus.CREATED || this.status == PollStatus.RUNNING) {
            this.choiceTable = new Hashtable<>();
            try {
                setValues(name, question, choices);
            } catch (PollException pe) {
                System.err.println(pe);
            }
            ClearPoll();
            this.status = PollStatus.CREATED;
        } else {
            throw new PollException("Update", "Status is not CREATED or RUNNING");
        }
    }

    public void ClearPoll() throws PollException {
        if (this.status == PollStatus.RUNNING || this.status == PollStatus.RELEASED) {
            this.choiceTable.clear();
            if (this.participants != null)
                this.participants.clear();
            for (int i = 0; i < choices.length; i++) {
                choices[i].setChoiceCounter(0);
                this.choiceTable.put(this.choices[i], this.choices[i].getChoiceCounter());
            }
            if (this.status == PollStatus.RELEASED)
                this.status = PollStatus.CREATED;
        } else {
            throw new PollException("Clear", "Status is not RUNNING or RELEASED");
        }
    }

    public void ClosePoll() throws PollException {
        if (this.status == PollStatus.RELEASED) {
            try {
                ClearPoll();
                this.choiceTable = null;
                this.participants = null;
                this.choices = null;
            } catch (PollException pe) {
                System.err.println(pe);
            }
        } else {
            throw new PollException("Close", "Status is not RELEASED");
        }
    }

    public void RunPoll() throws PollException {
        if (this.status == PollStatus.CREATED) {
            this.status = PollStatus.RUNNING;
            System.out.println("Poll status is now RUNNING");
        } else {
            throw new PollException("Run", "Status is not CREATED");
        }
    }

    public void ReleasePoll() throws PollException {
        if (this.status == PollStatus.RUNNING) {
            this.status = PollStatus.RELEASED;
            System.out.println("Poll status is now RELEASED");
        } else {
            throw new PollException("Release", "Status is not RUNNING");
        }
    }

    public void UnreleasePoll() throws PollException {
        if (this.status == PollStatus.RELEASED) {
            this.status = PollStatus.RUNNING;
            System.out.println("Poll status is now UNRELEASED");
        } else {
            throw new PollException("Unrelease", "Status is not RELEASED");
        }
    }

    public void Vote(String participant, Choice choice) throws PollException {
        if (this.status == PollStatus.RUNNING) {
            System.out.println("Participant is: " + participant);
            if (this.participants == null) {
                this.participants = new Hashtable<>();
            }
            if (!this.participants.containsKey(participant)) {
                choice.setChoiceCounter(choice.getChoiceCounter() + 1);
                this.choiceTable.put(choice, choice.getChoiceCounter());
                date = LocalDateTime.now();
                this.participants.put(participant, new Pair<>(choice, date.format(formatter)));
                System.out.println("The participant successfully voted");
            } else {
                if (!choice.getDescription().equals(participants.get(participant).getFirst().getDescription())) {
                    // Update -1 choice counter in choice table
                    this.participants.get(participant).getFirst().setChoiceCounter(this.participants.get(participant).getFirst().getChoiceCounter() - 1);
                    this.choiceTable.put(this.participants.get(participant).getFirst(), this.participants.get(participant).getFirst().getChoiceCounter());
                    choice.setChoiceCounter(choice.getChoiceCounter() + 1);
                    this.choiceTable.put(choice, choice.getChoiceCounter());
                    // Update participant's choice in participants
                    date = LocalDateTime.now();
                    this.participants.put(participant, new Pair<>(choice, date.format(formatter)));
                    System.out.println("The participant successfully changed his mind");
                }
            }
            this.choiceTable.forEach((k, v) -> System.out.println(k.getDescription() + " - " + v));
        } else {
            throw new PollException("Vote", "Status is not RUNNING");
        }
    }

    public Hashtable GetPollResults() throws PollException {
        if (this.status == PollStatus.RELEASED)
            return this.choiceTable;
        else throw new PollException("GetPollResults", "Status is not RELEASED");
    }

    public void DownloadPollDetails(PrintWriter output, String filename) throws PollException {
        if (this.status == PollStatus.RELEASED) {
            try {
                output.println("Title: " + PollWrapper.manager.getName());
                output.println("Question: " + PollWrapper.manager.getQuestion());
                output.println("Choices: ");
                for (int i = 0; i < PollWrapper.manager.getChoices().length; i++)
                    output.println("\t" + PollWrapper.manager.getChoices()[i].getDescription());
                output.println("Votes: ");
                for (Pair p : PollWrapper.manager.participants.values())
                    output.println(((Choice) p.getFirst()).getDescription() + " " + p.getSecond());
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (output != null)
                    output.close();
            }
        } else {
            throw new PollException("DownloadDetails", "Status is not RELEASED");
        }
    }

}
