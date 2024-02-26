package emedical;

public class Patients {
    private String fullName;
    private String brand;
    private String section;

    public Patients(String fullName, String brand, String section) {
        this.fullName = fullName;
        this.brand = brand;
        this.section = section;
    }
    
    
    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getBrand() {
        return brand;
    }

    public String getSection() {
        return section;
    }
    
    
    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSection(String section) {
        this.section = section;
    }
    
}
