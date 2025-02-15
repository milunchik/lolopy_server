package lolopy.server.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class User {

    @Id
    @SequenceGenerator(
        name = "user",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator ="user_sequence"
    )
    private int id;
    private String email;
    private String password;
    private String fullname;
    private String passport;
    private String phone_number;
    private String photo;

    public User(){}

    //for login
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }


    //for singup
    public User(String email, String password, String fullname){
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    //for receiving and changing data
    public User(String email, String password, String fullname, String passport, String phone_number, String photo){
        this.email = email;
        this.password = password;
        this.passport = passport;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.photo = photo;
    }

    
    public User(int id, String email, String password, String fullname, String passport, String phone_number, String photo){
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.passport = passport;
        this.phone_number = phone_number;
        this.photo = photo;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id; 
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email; 
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password; 
    }

    public String getFullName(){
        return fullname;
    }

    public void setFullName(String fullname){
        this.fullname = fullname; 
    }

    public String getPassport(){
        return passport;
    }

    public void setPassport(String passport){
        this.passport = passport;
    }

    public String getPhoneNumber(){
        return phone_number;
    }

    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }

    public String getPhoto(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }


    @Override
    public String toString(){
        return "User{"+"id="+id+", email= '"+ email +'\''+", password= "+password+", fullname= "+fullname+", passport= "+passport+", phone_number="+phone_number+", photo="+photo+'}';
    }

    

}
