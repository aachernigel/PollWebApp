package PollManagerLib;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PollManager {
    private static int ID_LENGTH = 10;
    private static int PIN_LENGTH = 6;
    private String pollID;
    private String pin;
    private String name;
    private String question;
    private Choice[] choices;
    private PollStatus status;
    private Hashtable<Choice, Integer> choiceTable;
    private Hashtable<String, Pair<Choice, String>> participants;
    private LocalDateTime date;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public String getDate() {
        date = LocalDateTime.now();
        return date.format(formatter);
    }

    public String getPollID(){
        return this.pollID;
    }

    public void setPollID(String pollID){
        this.pollID = pollID;
    }

    public String getPin(){
        return this.pin;
    }

    public void setPin(String pin){
        this.pin = pin;
    }

    public Hashtable<String, Pair<Choice, String>> getParticipants(){ return this.participants; }

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

    private void generateID(){
        String alphabet = "ABCDEFGHJKMNPQRSTVWXYZ";
        int randomIndex;
        this.pollID = "";
        for(int i = 0; i < PollManager.ID_LENGTH; i++){
            randomIndex = (int) (Math.random() * alphabet.length());
            this.pollID += alphabet.charAt(randomIndex);
        }
    }

    public void generatePIN(){
        String pin = "";
        for(int i = 0; i < PollManager.PIN_LENGTH; i++)
            pin += (int) (Math.random() * PollManager.ID_LENGTH);
        this.pin = pin;
    }

    public void CreatePoll(String name, String question, Choice[] choices) throws PollException {
        if (this.status == null || this.status == PollStatus.RELEASED) {
            this.status = PollStatus.CREATED;
            this.generateID();
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
            DBPollGateway.dbPoll.updateStatus(PollWrapper.manager.getStatus());
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
            DBPollGateway.dbPoll.deleteVotes();
            DBPollGateway.dbPoll.updateStatus(PollWrapper.manager.getStatus());
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
                this.status = PollStatus.CLOSED;
                DBPollGateway.dbPoll.updateStatus(PollStatus.CLOSED);
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
            DBPollGateway.dbPoll.updateStatus(PollWrapper.manager.getStatus());
        } else {
            throw new PollException("Run", "Status is not CREATED");
        }
    }

    public void ReleasePoll() throws PollException {
        if (this.status == PollStatus.RUNNING) {
            this.status = PollStatus.RELEASED;
            System.out.println("Poll status is now RELEASED");
            DBPollGateway.dbPoll.updateStatus(PollWrapper.manager.getStatus());
        } else {
            throw new PollException("Release", "Status is not RUNNING");
        }
    }

    public void UnreleasePoll() throws PollException {
        if (this.status == PollStatus.RELEASED) {
            this.status = PollStatus.RUNNING;
            System.out.println("Poll status is now UNRELEASED");
            DBPollGateway.dbPoll.updateStatus(PollWrapper.manager.getStatus());
        } else {
            throw new PollException("Unrelease", "Status is not RELEASED");
        }
    }

    public void Vote(String participant, Choice choice, LocalDateTime... date) throws PollException {
        if (this.status == PollStatus.RUNNING) {
            System.out.println("Participant is: " + participant);
            if (this.participants == null) {
                this.participants = new Hashtable<>();
            }
            this.date = date.length > 0 ? date[0] : LocalDateTime.now();
            if (!this.participants.containsKey(participant)) {
                choice.setChoiceCounter(choice.getChoiceCounter() + 1);
                this.choiceTable.put(choice, choice.getChoiceCounter());
                this.participants.put(participant, new Pair<>(choice, this.date.format(formatter)));
                System.out.println("The participant successfully voted");
            } else {
                if (!choice.getDescription().equals(participants.get(participant).getFirst().getDescription())) {
                    // Update -1 choice counter in choice table
                    this.participants.get(participant).getFirst().setChoiceCounter(this.participants.get(participant).getFirst().getChoiceCounter() - 1);
                    this.choiceTable.put(this.participants.get(participant).getFirst(), this.participants.get(participant).getFirst().getChoiceCounter());
                    choice.setChoiceCounter(choice.getChoiceCounter() + 1);
                    this.choiceTable.put(choice, choice.getChoiceCounter());
                    // Update participant's choice in participants
                    this.participants.put(participant, new Pair<>(choice, this.date.format(formatter)));
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
                if(filename.contains(".txt")){
                    output.println("Title: " + PollWrapper.manager.getName());
                    output.println("Question: " + PollWrapper.manager.getQuestion());
                    output.println("Choices: ");
                    for (int i = 0; i < PollWrapper.manager.getChoices().length; i++)
                        output.println("\t" + PollWrapper.manager.getChoices()[i].getDescription());
                    output.println("Votes: ");
                    for (Pair p : PollWrapper.manager.participants.values())
                        output.println("\t" + ((Choice) p.getFirst()).getDescription() + " " + p.getSecond());
                } else if(filename.contains(".json")){
                    JSONObject jsonPoll = new JSONObject();
                    JSONObject jsonChoices = new JSONObject();
                    JSONObject jsonVotes = new JSONObject();
                    for (int i = 1; i < PollWrapper.manager.getChoices().length + 1; i++)
                        jsonChoices.put("option" + i, PollWrapper.manager.getChoices()[i - 1].getDescription());
                    for (Pair p : PollWrapper.manager.participants.values()){
                        System.out.println("\t" + ((Choice) p.getFirst()).getDescription() + " " + p.getSecond());
                        jsonVotes.put(p.getSecond().toString(), ((Choice) p.getFirst()).getDescription());
                    }
                    jsonPoll.put("Choices", jsonChoices);
                    jsonPoll.put("Title", PollWrapper.manager.getName());
                    jsonPoll.put("Question", PollWrapper.manager.getQuestion());
                    jsonPoll.put("Votes", jsonVotes);
                    output.println(jsonPoll.toString(4));
                } else if(filename.contains(".xml")){
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.newDocument();
                    Element rootElement = document.createElement("poll");
                    document.appendChild(rootElement);
                    rootElement.setAttribute("id", PollWrapper.manager.getPollID());
                    Element title = document.createElement("title");
                    title.setTextContent(PollWrapper.manager.getName());
                    rootElement.appendChild(title);
                    Element question = document.createElement("question");
                    question.setTextContent(PollWrapper.manager.getQuestion());
                    rootElement.appendChild(question);
                    Element choices = document.createElement("choices");
                    Element choice;
                    for (int i = 1; i < PollWrapper.manager.getChoices().length + 1; i++){
                        choice = document.createElement("choice");
                        choice.setAttribute("id", "option" + i);
                        choice.setTextContent(PollWrapper.manager.getChoices()[i - 1].getDescription());
                        choices.appendChild(choice);
                    }
                    rootElement.appendChild(choices);
                    Element votes = document.createElement("votes");
                    Element vote;
                    for (Pair p : PollWrapper.manager.participants.values()){
                        vote = document.createElement("vote");
                        vote.setAttribute("id", p.getSecond().toString());
                        vote.setTextContent(((Choice) p.getFirst()).getDescription());
                        votes.appendChild(vote);
                    }
                    rootElement.appendChild(votes);
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(output);
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                    transformer.transform(source, result);
                }
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
