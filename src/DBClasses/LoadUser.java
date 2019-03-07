package DBClasses;


import Model.Goal;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class used to load a specific user from database. Creates connection and populates required
 * fields from ResultSet - then returns User class when getUser() is called.
 */
public class LoadUser extends DBAccess {

    private User u;

    private String userName, password, firstName, lastName, email;
    private boolean active;

    private int height, bmi, age, sex;
    private double weight, activityLevel;
    private ArrayList<Goal> goals;

    /**
     * Creates new User object with corresponding details of String entered in parameter
     * @param un
     * @throws SQLException
     */
    public LoadUser(String un) throws SQLException {
        goals = new ArrayList<>();
        this.userName = un;
        populateData();

        u = new User(userName);
        u.setPassword(password);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setActive(active);

        u.setHeight(height);
        u.setWeight(weight);
        u.setBmi(bmi);
        u.setAge(age);
        u.setSex(sex);
        u.setActivityLevel(activityLevel);

        for (Goal g : goals) {
            u.addGoal(g);
        }

    }

    /**
     * Get loaded user
     * @return User
     */
    public User getUser() {
        return u;
    }

    /**
     * Queries database to populate all fields required to initialise User class
     * @throws SQLException
     */
    private void populateData() throws SQLException {

        getConnection();

        // Profile Data
        ResultSet rs = null;

        rs = st.executeQuery("SELECT * FROM PROFILE WHERE USERNAME ='" + this.userName + "'");

        rs.next();
        this.password = rs.getString(2);
        this.firstName = rs.getString(3);
        this.lastName = rs.getString(4);
        this.email = rs.getString(5);
        this.active = rs.getBoolean(6);

        // User Data
        rs = st.executeQuery("SELECT * FROM USER WHERE USERNAME ='" + this.userName + "'");
        rs.next();
        this.height = rs.getInt(2);
        this.weight = rs.getDouble(3);
        this.bmi = rs.getInt(4);
        this.age = rs.getInt(5);
        this.sex = rs.getInt(6);
        this.activityLevel = rs.getDouble(7);

        // Goals
        rs = st.executeQuery("SELECT * FROM GOALS WHERE ID ='" + this.userName + "'");
        while (rs.next()) {
            Date targetDate = rs.getDate(3);
            java.util.Date date = new java.util.Date(targetDate.getYear(),targetDate.getMonth(),targetDate.getDate());
            Goal g = new Goal(rs.getDouble(2), date, rs.getDouble(9));
            g.setID(rs.getString(1));
            g.setCompleted(rs.getBoolean((4)));
            g.setActive(rs.getBoolean(5));
            g.setStartDays(rs.getInt(6));
            //g.setWeightLossProgress();        This field requires current weight to calculate
            g.setPercentLost();
            goals.add(g);
        }

        closeConnection();
    }

    /**
     * Given a username and date, queries database for all calories consumed that date and
     * returns total amount.
     *
     * @param u
     * @param d
     * @return int calories
     * @throws SQLException
     */
    public static int getCaloriesByDate(String u, Date d) throws SQLException {
        ResultSet rs = null;
        int kcals = 0;
        getConnection();

        rs = st.executeQuery("SELECT KCALS FROM CALORIECOUNTS WHERE USERNAME = '"
                +u+"' AND DATE = '"+d+"'");
        while (rs.next()) {
            kcals += rs.getInt(1);
        }

        closeConnection();

        return kcals;
    }

    public static void main(String[] args) throws SQLException {

        User user = new LoadUser("imacpro").getUser();
        System.out.println(user.getUserName());
        System.out.println(user.toString());
        System.out.println(user.getGoals());

        //int rodneyCals = getCaloriesByDate("rodney", new Date(119, 1, 23));
        //System.out.println(rodneyCals);

        //User user = new LoadUser("ladyinred").getUser();
        //System.out.println(user.getGoals());


    }

}
