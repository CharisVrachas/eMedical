package emedical;

public class Doctors {
    private String fullnameDoctor;
    private String emailDoctor;
    private String specializationDoctor;
    private String internDoctor;

    public Doctors(String fullnameDoctor, String emailDoctor, String specializationDoctor, String internDoctor) {
        this.fullnameDoctor = fullnameDoctor;
        this.emailDoctor = emailDoctor;
        this.specializationDoctor = specializationDoctor;
        this.internDoctor = internDoctor;
    }

    
    // Getters
    public String getFullnameDoctor() {
        return fullnameDoctor;
    }

    public String getEmailDoctor() {
        return emailDoctor;
    }

    public String getSpecializationDoctor() {
        return specializationDoctor;
    }

    public String getInternDoctor() {
        return internDoctor;
    }
    
    
    //Setters
    public void setFullnameDoctor(String fullnameDoctor) {
        this.fullnameDoctor = fullnameDoctor;
    }

    public void setEmailDoctor(String emailDoctor) {
        this.emailDoctor = emailDoctor;
    }

    public void setSpecializationDoctor(String specializationDoctor) {
        this.specializationDoctor = specializationDoctor;
    }

    public void setInternDoctor(String internDoctor) {
        this.internDoctor = internDoctor;
    }
    
}
