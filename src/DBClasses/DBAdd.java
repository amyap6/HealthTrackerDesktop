package DBClasses;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class holds static methods for adding to database
 */
public final class DBAdd extends DBAccess{

    private DBAdd(){

    }

    /**
     * Populates USER and PROFILE tables of database with given User details
     * @param u
     */
    public static void addUser(User u){

        getConnection();
        try {
            st.executeUpdate("INSERT INTO PROFILE VALUES ('"+u.getUserName()+"', '"+u.getPassword()+"', '"
                    +u.getFirstName()+"', '"+u.getLastName()+"', '"+u.getEmail()+"', 1)");
            st.executeUpdate("INSERT INTO USER VALUES ('"+u.getUserName()+"', "+u.getHeight()+", "
                    +u.getWeight()+", "+u.getBmi()+", "+u.getAge()+", "+u.getSex()+", "+u.getActivityLevel()+")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

    }

    /**
     * Populates FOOD table of database with given Food
     * @param f
     */
    public static void addFood(Food f){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO FOODS VALUES ('"+f.getName()+"', "+f.getKcals()+", "
                    +f.getProtein()+", "+f.getCarbs()+", "+f.getFat()+", '"+f.getCategory()+"', NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Allows adding of a custom Food
     * @param f
     */
    public static void addCustomFood(Food f){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO FOODS VALUES ('"+f.getName()+"', "+f.getKcals()+", "
                    +f.getProtein()+", "+f.getCarbs()+", "+f.getFat()+", '"+f.getCategory()+"', '"
                    +f.getUsername()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Returns list of custom foods for given User
     * @param category
     * @param username
     * @return foods
     */
    public static ArrayList<String> getFoods(String category, String username) {
        ArrayList<String> foods = new ArrayList<>();
        ResultSet rs = null;

        getConnection();
        if (category == "Custom") {
            try {
                rs = st.executeQuery("SELECT NAME FROM FOODS WHERE CATEGORY = '"+category+
                        "' AND USERNAME = '"+username+"'");
                while (rs.next()) {
                    foods.add(rs.getString(1));
                    System.out.println(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                rs = st.executeQuery("SELECT NAME FROM FOODS WHERE CATEGORY = '" + category + "'");
                while (rs.next()) {
                    foods.add(rs.getString(1));
                    System.out.println(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection();

        return foods;
    }

    /**
     * Returns calories value for given Food
     * @param food
     * @return calories
     */
    public static int getFoodCalories(String food) {
        int calories = 0;
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("SELECT KCALS FROM FOODS WHERE NAME = '"+food+"'");
            rs.next();
            calories = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return calories;
    }

    /**
     * Returns calories of specific meal identified by given User, Date, String
     * @param u
     * @param d
     * @param m
     * @return calories
     */
    public static int getMealCalories(String u, Date d, String m) {
        int calories = 0;
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("SELECT KCALS FROM CALORIECOUNTS WHERE USERNAME = '"+u+
                    "' AND DATE = '"+d+"' AND MEAL = '"+m+"'" );
            while (rs.next()) {
                calories += rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return calories;
    }

    /**
     * Adds new goal to database using given ID and Goal
     * @param id
     * @param g
     */
    public static void addGoal(String id, Goal g){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO GOALS VALUES ('"+id+"', " +g.getTargetWeightLoss()
                    +", '"+g.getTargetDate()+"', "+g.isCompleted()+", " +g.isActive()+", "+g.getStartDays()+
                    ", "+g.getWeightLossProgress()+", "+g.getPercentLost()+", "+g.getStartWeight()+")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Updates calories in database for given User and Date
     * @param u
     * @param d
     * @param c
     * @param m
     */
    public static void addCalories(String u, Date d, int c, String m){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO CALORIECOUNTS VALUES ('"+u+"', '"+d+"', '"+c+"', '"+m+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Adds new given Exercise to database
     * @param ex
     */
    public static void addExercise(Exercise ex){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO EXERCISE VALUES ('"+ex.getName()+"', '"
                    +ex.getType().name()+"', "+ex.getCalPerMin()+", NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Adds custom given exercise to database belonging to particular user
     * @param ex
     */

    /**
     * Return all exercises of given Exercise.Type belonging to given User
     * @param username
     * @param type
     * @return
     */
    public static ArrayList<String> getExercises(String username, Exercise.Type type) {
        ArrayList<String> exercises = new ArrayList<>();
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("(SELECT NAME FROM EXERCISE WHERE USERNAME = '"+username+
                    "' OR USERNAME IS NULL) INTERSECT (SELECT NAME FROM EXERCISE WHERE TYPE = '" +type+"')");
            while (rs.next()) {
                exercises.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return exercises;
    }

    /**
     * Returns calories burned over given duration for given weight
     * @param name
     * @param weight
     * @param duration
     * @return calories
     */
    public static int getExerciseCalories(String name, double weight, int duration) {
        double calories = -1*weight*duration;
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("SELECT CALPERMIN FROM EXERCISE WHERE NAME = '"+name+"'");
            rs.next();
            calories *= (rs.getDouble(1)*2.205);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return (int)calories;
    }

    /**
     * Adds given message to given Group group-chat by given User
     * @param user
     * @param group
     * @param message
     */
    public static void addMesage(String user, Group group, String message) {
        java.util.Date date = new java.util.Date();
        Timestamp now = new Timestamp(date.getTime());
        if (!group.getGroupName().equals("Longevity")) {
            getConnection();
            try {
                st.executeUpdate("INSERT INTO MESSAGES VALUES ('" + user + "','" + group.getGroupName() + "','"
                        + message + "','" + now + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }

    /**
     * Returns all messages for given Group
     * @param groupID
     * @return messages
     */
    public static ArrayList<Message> getMessages(String groupID) {
        ArrayList<Message> messages = new ArrayList<>();
        ResultSet rs = null;
        String user, name, message;

        if (!groupID.equals("Longevity")) {
            getConnection();
            try {
                rs = st.executeQuery("SELECT PROFILE.USERNAME, PROFILE.FIRSTNAME, PROFILE.SURNAME, MESSAGES.MESSAGE " +
                        "FROM MESSAGES JOIN PROFILE ON MESSAGES.USERNAME = PROFILE.USERNAME WHERE GROUPID = '" + groupID +
                        "' ORDER BY MESSAGES.DATE");
                while (rs.next()) {
                    user = rs.getString(1);
                    name = rs.getString(2) + " " + rs.getString(3);
                    message = rs.getString(4);
                    messages.add(new Message(user, name, message));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        } else {
            messages.add(new Message("Longevity", "Team Longevity", "Welcome to Groups!"));
            messages.add(new Message("Longevity", "Team Longevity", "Use the side panel to create " +
                    "new groups and add your friends."));
            messages.add(new Message("Longevity", "Team Longevity", "Here you can share a group " +
                    "goal and discuss your progress."));
            messages.add(new Message("Longevity", "Team Longevity", "Good Luck!"));
            messages.add(new Message("Longevity", "Team Longevity", "Team Longevity"));
        }

        return messages;
    }

    public static ArrayList<Group> getGroups(String user) {
        ArrayList<Group> groups = new ArrayList<>();
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("(SELECT GROUPMEMBERS.USERNAME, GROUPMEMBERS.GROUPID FROM GROUPMEMBERS " +
                            "WHERE GROUPMEMBERS.USERNAME = '"+user+"') INTERSECT " +
                    "(SELECT GROUPMEMBERS.USERNAME, GROUPMEMBERS.GROUPID FROM GROUPMEMBERS " +
                    "JOIN MESSAGES ON GROUPMEMBERS.GROUPID = MESSAGES.GROUPID " +
                    "WHERE GROUPMEMBERS.USERNAME = '"+user+"' ORDER BY MESSAGES.DATE DESC)");
            while (rs.next()) {
                groups.add(new Group(rs.getString(2), null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return groups;
    }

    public static Boolean addGroup(String user, String groupID) {
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("SELECT * FROM GROUPMEMBERS WHERE GROUPID = '"+groupID+"'");
            if (!rs.next()) {
                st.executeUpdate("INSERT INTO GROUPMEMBERS VALUES ('"+groupID+"', '"+user+"')");
                String welcome = "Welcome to your new group. Use the side bar to add new members!";
                addMesage("Longevity", new Group(groupID, null), welcome);
                return true;
            }
        } catch (SQLException e) {

        }
        closeConnection();
        return false;
    }

    public static Boolean addMember(String user, String groupID) {
        ResultSet rs = null;

        getConnection();
        try {
            rs = st.executeQuery("SELECT * FROM USER WHERE USERNAME = '"+user+"'");
            if (rs.next()) {
                st.executeUpdate("INSERT INTO GROUPMEMBERS VALUES ('"+groupID+"', '"+user+"')");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public static void updatePassword(String username, String password) {
        getConnection();
        try {
            st.executeUpdate("UPDATE PROFILE SET PASSWORD = '"+password+"' WHERE USERNAME = '"+username+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static void addCustomExercise(String name, Exercise.Type type, int dur, int cal, User user) {
        double calPerMin = (double)cal/user.getWeight()/dur/2.205;
        getConnection();
        try {
            st.executeUpdate("INSERT INTO EXERCISE VALUES ('"+name+"','"+type+"',"+calPerMin+",'"+user.getUserName()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static void addExerciseGoal(String user, int target) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH,+1);
        Date end = new Date(cal.getTime().getTime());
        getConnection();
        try {
            st.executeUpdate("INSERT INTO EXERCISEGOALS VALUES ('"+user+"',"+target+",'"+date+"','"+end+"')");
        } catch (SQLException e) {

        }
        closeConnection();
    }

    public static ArrayList<String> getExerciseGoal(String user) {
        ResultSet rs = null;
        ArrayList<String> goal = new ArrayList<>();
        getConnection();
        try {
            rs = st.executeQuery("SELECT * FROM EXERCISEGOALS WHERE USERNAME = '"+user+"'");
            rs.next();
            goal.add(rs.getString(1));
            goal.add(String.valueOf(rs.getInt(2)));
            goal.add(rs.getString(3));
            goal.add(rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return goal;
    }

    public static double getLostPercentage(User user) {
        ResultSet rs;
        int start = 0;
        int end = 0;
        getConnection();
        try {
            rs = st.executeQuery("SELECT TARGETWEIGHTLOSS, STARTWEIGHT FROM GOALS WHERE ID = '"+user.getUserName()+"'");
            rs.next();
            start = rs.getInt(2);
            end = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return (start-user.getWeight())/end;
    }

    public static void updateWeight(String user, int weight) {
        getConnection();
        try {
            st.executeUpdate("UPDATE USER SET WEIGHT = "+weight+" WHERE USERNAME = '"+user+"'");
        } catch (SQLException e) {

        }
        closeConnection();
    }


    public static void main(String[] args) {
        //System.out.println(new Date(119,2,3));

/*        addFood(new Food("Chicken (100g)",146,0,0,0,"Meat"));
        addFood(new Food("Beef (100g)",188,0,0,0,"Meat"));
        addFood(new Food("Pork (100g)",201,0,0,0,"Meat"));
        addFood(new Food("Turkey (100g)",147,0,0,0,"Meat"));
        addFood(new Food("Lamb (100g)",258,0,0,0,"Meat"));
        addFood(new Food("Apple (100g)",52,0,0,0,"Fruit"));
        addFood(new Food("Pear (100g)",57,0,0,0,"Fruit"));
        addFood(new Food("Plum (100g)",46,0,0,0,"Fruit"));
        addFood(new Food("Orange (100g)",47,0,0,0,"Fruit"));
        addFood(new Food("Grapes (100g)",67,0,0,0,"Fruit"));
        addFood(new Food("Banana (100g)",89,0,0,0,"Fruit"));
        addFood(new Food("Kiwi (100g)",61,0,0,0,"Fruit"));
        addFood(new Food("Pineapple (100g)",50,0,0,0,"Fruit"));
        addFood(new Food("Strawberries (100g)",33,0,0,0,"Fruit"));
        addFood(new Food("Carrot (100g)",41,0,0,0,"Vegetable"));
        addFood(new Food("Broccoli (100g)",34,0,0,0,"Vegetable"));
        addFood(new Food("Potato (100g)",77,0,0,0,"Vegetable"));
        addFood(new Food("Onion (100g)",40,0,0,0,"Vegetable"));
        addFood(new Food("Sprouts (100g)",43,0,0,0,"Vegetable"));
        addFood(new Food("Cabbage (100g)",25,0,0,0,"Vegetable"));
        addFood(new Food("Leak (100g)",7,0,0,0,"Vegetable"));
        addFood(new Food("Peas (100g)",81,0,0,0,"Vegetable"));
        addFood(new Food("Parsnip (100g)",75,0,0,0,"Vegetable"));
        addFood(new Food("Lettuce (100g)",15,0,0,0,"Vegetable"));
        addFood(new Food("Tomato (100g)",18,0,0,0,"Vegetable"));
        addFood(new Food("Cucumber (100g)",16,0,0,0,"Vegetable"));
        addFood(new Food("Pepper (100g)",20,0,0,0,"Vegetable"));
        addFood(new Food("Mushroom (100g)",22,0,0,0,"Vegetable"));
        addFood(new Food("Whole Milk (100ml)",68,0,0,0,"Dairy"));
        addFood(new Food("Semi-skimmed Milk (100ml)",47,0,0,0,"Dairy"));
        addFood(new Food("skimmed Milk (100ml)",35,0,0,0,"Dairy"));
        addFood(new Food("Plain Yoghurt (100ml)",61,0,0,0,"Dairy"));
        addFood(new Food("Single Cream (100ml)",193,0,0,0,"Dairy"));
        addFood(new Food("Double Cream (100ml)",446,0,0,0,"Dairy"));
        addFood(new Food("Butter (100g)",717,0,0,0,"Dairy"));
        addFood(new Food("Cheddar Cheese (100g)",416,0,0,0,"Dairy"));
        addFood(new Food("Mozzarella Cheese (100g)",300,0,0,0,"Dairy"));
        addFood(new Food("Parmesan Cheese (100g)",401,0,0,0,"Dairy"));
        addFood(new Food("1 Egg",155,0,0,0,"Dairy"));
        addFood(new Food("White Bread (100g)",265,0,0,0,"Grain"));
        addFood(new Food("White Bread (Slice)",60,0,0,0,"Grain"));
        addFood(new Food("Wholemeal Bread (100g)",247,0,0,0,"Grain"));
        addFood(new Food("Wholemeal Bread (Slice)",57,0,0,0,"Grain"));
        addFood(new Food("Long-Grain Rice (100g)",130,0,0,0,"Grain"));
        addFood(new Food("Pasta (100g)",352,0,0,0,"Grain"));
        addFood(new Food("Wholemeal Pasta (100g)",325,0,0,0,"Grain"));
        addFood(new Food("Milk Chocolate (100g)",530,0,0,0,"Sweet"));
        addFood(new Food("Dark Chocolate (100g)",607,0,0,0,"Sweet"));
        addFood(new Food("White Chocolate (100g)",595,0,0,0,"Sweet"));
        getFoods("Meat");
        System.out.println(getFoodCalories("Butter (100g)"));
        Date today = new Date(119,2,5);
        getMealCalories("td98", today, "Breakfast");*/
        /*addFood(new Food("Orange Juice (250ml)",113,0,0,0,"Drink"));
        addFood(new Food("Apple Juice (250ml)",115,0,0,0,"Drink"));
        addFood(new Food("Cola (250ml)",95,0,0,0,"Drink"));
        addFood(new Food("Lemonade (250ml)",98,0,0,0,"Drink"));*/

        /*addExercise(new Exercise("Walking", Exercise.Type.CARDIO, 0.032));
        addExercise(new Exercise("Running", Exercise.Type.CARDIO, 0.064));
        addExercise(new Exercise("Bicycling", Exercise.Type.CARDIO, 0.064));
        addExercise(new Exercise("Swimming", Exercise.Type.CARDIO, 0.048));
        addExercise(new Exercise("Football", Exercise.Type.CARDIO, 0.056));
        addExercise(new Exercise("Basketball", Exercise.Type.CARDIO, 0.064));
        addExercise(new Exercise("Hockey", Exercise.Type.CARDIO, 0.064));
        addExercise(new Exercise("Tennis", Exercise.Type.CARDIO, 0.056));
        addExercise(new Exercise("Badminton", Exercise.Type.CARDIO, 0.036));

        addExercise(new Exercise("Free Weights", Exercise.Type.STRENGTH, 0.048));
        addExercise(new Exercise("Push-ups", Exercise.Type.STRENGTH, 0.055));
        addExercise(new Exercise("Pull-ups", Exercise.Type.STRENGTH, 0.064));
        addExercise(new Exercise("Sit-ups", Exercise.Type.STRENGTH, 0.030));
        addExercise(new Exercise("Lunges", Exercise.Type.STRENGTH, 0.060));
        addExercise(new Exercise("Squats", Exercise.Type.STRENGTH, 0.038));
        addExercise(new Exercise("Crunches", Exercise.Type.STRENGTH, 0.030));
        addExercise(new Exercise("Planks", Exercise.Type.STRENGTH, 0.030));
        addExercise(new Exercise("Star Jumps", Exercise.Type.STRENGTH, 0.063));
        addExercise(new Exercise("Resistance Training", Exercise.Type.STRENGTH, 0.048));*/
    }
}
