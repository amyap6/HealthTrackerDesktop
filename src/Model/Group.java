package Model;

import application.Launch;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Group {

    private ArrayList<User> members;
    private Goal groupGoal;
    private String groupName;

    public Group(ArrayList<User> members, Goal groupGoal, String groupName){
        this.members = members;
        this.groupGoal = groupGoal;
        this.groupName = groupName;
    }

    public Group(ArrayList<User> members, String groupName) {
        this.members = members;
        this.groupGoal = null;
        this.groupName = groupName;
    }

    public Group(String groupName, Goal groupGoal) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.members.add(Launch.getCurrentUser());
        this.groupGoal = groupGoal;
    }

    public ArrayList<User> getMembers() {

        return members;
    }

    public void setMembers(ArrayList<User> members) {

        this.members = members;
    }

    public Goal getGroupGoal() {

        return groupGoal;
    }

    public void setGroupGoal(Goal groupGoal) {

        this.groupGoal = groupGoal;
    }

    public String getGroupName() {

        return groupName;
    }

    public void setGroupName(String groupName) {

        this.groupName = groupName;
    }

    public void addUser(User user){
        this.members.add(user);
    }

    //delete the users membership from the group
    //return true if successful
    public boolean deleteMembership(User user){

        if (this.members.contains(user)){
            this.members.remove(user);
                return true;
            }

        return false;

    }

    //generates a random alphanumerical string of 10 characters to be used as verification within an email
    protected String randomString() {

        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            random.append(CHARS.charAt(index));
        }
        String randomString = random.toString();
        return randomString;

    }

    //generic method to send a mime email
    public void sendEmail(User sender, User recipient, String msg, String subject) throws MessagingException{

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( "2d962aa3c7a979", "27b172431ebc56");
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender.getEmail()));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipient.getEmail()));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        Transport.send(message);

    }

    //method to send group details to a user with a code to enable them to join the group
    public void sendGroupDetails(User sender, User recipient) throws MessagingException {

        String subject = this.getGroupName() + "has invited you to join their group!";

        String msg = "GROUP DETAILS \n \n Group name: " + this.getGroupName() +
                "\nMembers: " + this.getMembers() + "\n Group Goal: " + this.getGroupGoal() +
                "\n\n If you would like to join this group, paste the code below " +
                "into the prompt box when clicking 'Join Group'." + "\n\n" + randomString();

        sendEmail(sender, recipient, msg, subject);

    }

    //method to send group goal details to a user with a code to enable them to accept the goal
    public void sendNewGoalDetails(User sender, User recipient) throws MessagingException {

        String subject = this.getGroupName() + " has invited you to join their goal!";

        String msg = "Your group " + this.getGroupName() + " has created a new goal and would like " +
                "you to join.\n" + "Goal details: " + this.getGroupGoal() +
                "\n\nIf you would like to join this goal, paste the code below " +
                "into the prompt box when clicking 'Join Goal'.\n\n" + randomString();

        sendEmail(sender, recipient, msg, subject);

    }

    //method to send an email congratulating a user for completing a group goal
    public void sendGoalCompletedEmail(User sender, User recipient) throws MessagingException{

        String subject = "Group " + this.getGroupName() + " has completed it's goal!";

        String msg = "GOAL COMPLETED!\n\n" + "Congratulations! Your group has met their goal." +
                "Goal details: \n" + this.getGroupGoal();

        sendEmail(sender, recipient, msg, subject);

    }

    public static void main(String args[]) throws IOException {

        User amy = new User("amyPryor");
        amy.setFirstName("Amy");
        amy.setLastName("Pryor");
        amy.setEmail("amy.a.p@hotmail.com");

        User tom = new User("tomDavey");
        tom.setFirstName("Tom");
        tom.setLastName("Davey");

        User jamie = new User("jamieGreasley");
        jamie.setFirstName("Jamie");
        jamie.setLastName("Greasley");

        Date goalDate = new Date(2019, 8,3);
        Goal groupGoal = new Goal(-10, goalDate, 82.0);
        ArrayList<User> mem = new ArrayList<>();
        mem.add(amy);
        mem.add(jamie);
        mem.add(tom);

        Group group = new Group(mem, groupGoal, "Group 1");
        //group.joinGroup(jamie);
        group.deleteMembership(jamie);

        //can't send all these at the same time so one is commented out
        try {
            group.sendGroupDetails(amy, amy);
            group.sendNewGoalDetails(amy, amy);
            //group.sendGoalCompletedEmail(amy, amy);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
