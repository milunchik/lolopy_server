package lolopy.server.dtos;

import java.util.Objects;

public record LoginForm(String email, String password) {
    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        LoginForm that = (LoginForm) obj;
        return Objects.equals(this.email, that.email) && Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "LoginForm[" + "email=" + email + "," + "password=" + password + ']';
    }
}
