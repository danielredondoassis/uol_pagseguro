package loginscreen.solution.example.com.loginscreen.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String name;
    private String phone;
    private String email;


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
