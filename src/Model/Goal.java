package Model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Goal {

    private String id; // can be username for solo goal or random group id for group goal
    private double targetWeightLoss;
    private Date targetDate;
    private boolean completed;
    private boolean active;
    private int startDays;
    private double startWeight;

    private double weightLossProgress;
    private double percentLost;

    static Date today = Calendar.getInstance().getTime();

    static DecimalFormat numFormat = new DecimalFormat("#.00");

    public Goal(double targetWeightLoss, Date targetDate, Double startWeight){

        //making sure the user cannot set the target date to today or the past
        if (targetDate.after(today)){
            this.targetDate = targetDate;
        }
        else {
            System.out.println("Goal not set, please select a target date in the future");
            return;
        }

        //if the target date is okay then we can set the other variables as normal
        this.targetWeightLoss = targetWeightLoss;
        this.startDays = getDaysRemaining();
        this.completed = false;
        this.startWeight = startWeight;
    }

    public String getID(){
        return id;
    }

    public void setID(String s){
        id = s;
    }

    public boolean isCompleted() {

        return completed;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean b){
        active = b;
    }

    public void setCompleted(boolean completed) {

        this.completed = completed;
    }

    public double getTargetWeightLoss(){

        return targetWeightLoss;
    }

    public void setTargetWeightLoss(int targetWeightLoss){

        this.targetWeightLoss = targetWeightLoss;
    }

    public java.sql.Date getTargetDate() {
        java.sql.Date date = new java.sql.Date
                (this.targetDate.getYear(),this.targetDate.getMonth(),this.targetDate.getDate());

        return date;
    }

    public int getDaysRemaining() {
        Date date = new Date();
        long difference = date.getTime() - this.targetDate.getTime();
        return -1 * (int)TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

    public int getStartDays() {
        return this.startDays;
    }

    public void setStartDays(int i){
        startDays = i;
    }

    public void checkGoal(){
        if (this.targetDate.equals(today) || this.targetDate.before(today)){
            active = false;
            //when goal is not active, prompt user to set a new one
        }
        else if (this.weightLossProgress >= targetWeightLoss){
            active = false;
            //prompt user for new goal
        }
        else {
            active = true;
        }
    }

    public double getWeightLossProgress(){
        return this.weightLossProgress;
    }

    public void setWeightLossProgress(double currentWeight, double startWeight){

        this.weightLossProgress = currentWeight - startWeight;

    }

    public double getPercentLost(){
        return this.percentLost;
    }

    public void setPercentLost(){
        this.percentLost = (this.weightLossProgress/this.targetWeightLoss)*100;
    }

    @Override
    public String toString(){

        Date targetDate = this.getTargetDate();
        String goalString = targetWeightLoss + "kg by " + targetDate.getDay() + "/" +
                targetDate.getMonth() + "/" + targetDate.getYear();

        return goalString;
    }

    public void setStartWeight(double i) {
        this.startWeight = i;
    }

    public double getStartWeight() {
        return this.startWeight;
    }

    public static void main(String args[]){

        //Date goalDate = new Date(2019, 8,3);
        //Goal myGoal = new Goal(-10, goalDate);
        //System.out.println(myGoal.toString());
        //myGoal.setWeightLossProgress(74.8,81.6);
        //myGoal.setPercentLost();
        //System.out.println("Weight loss progress: " + numFormat.format(myGoal.getWeightLossProgress()) + "kg" +
        //        "\nPercentage of goal completed: " + numFormat.format(myGoal.getPercentLost()) + "%");


    }

}

