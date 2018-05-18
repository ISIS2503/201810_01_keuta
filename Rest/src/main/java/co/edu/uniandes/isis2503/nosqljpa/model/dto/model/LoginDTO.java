package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

public class LoginDTO {

    private String[] roles;

    public LoginDTO() {
    }

    public LoginDTO(String[] roles) {
        this.roles = roles;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
