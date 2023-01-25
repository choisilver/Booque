package site.book.project.domain;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");
    
    private String role;
    
    UserRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return this.role;
    }
}
