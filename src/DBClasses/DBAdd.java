package DBClasses;

import Model.Exercise;
import Model.Food;
import Model.Goal;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public final class DBAdd extends DBAccess{

    private DBAdd(){

    }

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

    public static void addFood(Food f){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO FOODS VALUES ('"+f.getName()+"', "+f.getKcals()+", "
                    +f.getProtein()+", "+f.getCarbs()+", "+f.getFat()+", '"+f.getCategory()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

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

    public static void addCalories(String u, Date d, int c, String m){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO CALORIECOUNTS VALUES ('"+u+"', '"+d+"', '"+c+"', '"+m+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static void updateCalories(String u, int c){
        getConnection();
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        try {
            st.executeUpdate("UPDATE CALORIECOUNTS SET KCALS = KCALS + "
                    +c+" WHERE USERNAME = '"+u+"' AND DATE = '"+date+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    public static void addExercise(Exercise ex){
        getConnection();
        try {
            st.executeUpdate("INSERT INTO EXERCISE VALUES ('"+ex.getName()+"', '"
                    +ex.getType().name()+"', "+ex.getCalsBurnedPer5Mins()+")");
        } catch (SQLException e) {
            e.printStackTrace();
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
        addFood(new Food("White Chocolate (100g)",595,0,0,0,"Sweet"));*/
        //getFoods("Meat");
        System.out.println(getFoodCalories("Butter (100g)"));
        Date today = new Date(119,2,5);
        getMealCalories("td98", today, "Breakfast");
    }

}
