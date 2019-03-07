package Model;

/**
 * Abstract parent class storing credentials and methods required by User
 * and any future Admin etc. classes
 */
public abstract class Profile {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;

    public void activateProfile(){
        active = true;
    }

    public void deactivateProfile(){
        active = false;
    }

    public boolean isActive(){
        return active;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Name: ").append(firstName).append(" ").append(lastName).append("\n");
        s.append("Email: ").append(email);

        return s.toString();
    }
}
