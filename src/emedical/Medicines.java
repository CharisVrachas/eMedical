package emedical;

public class Medicines {
    private String nameMedicine;
    private String brandMedicine;
    private String stockMedicine;
    private int numberMedicine;

    public Medicines(String nameMedicine, String brandMedicine, String stockMedicine, int numberMedicine) {
        this.nameMedicine = nameMedicine;
        this.brandMedicine = brandMedicine;
        this.stockMedicine = stockMedicine;
        this.numberMedicine = numberMedicine;
    }

    
    // Getters
    public String getNameMedicine() {
        return nameMedicine;
    }

    public String getBrandMedicine() {
        return brandMedicine;
    }

    public String getStockMedicine() {
        return stockMedicine;
    }

    public int getNumberMedicine() {
        return numberMedicine;
    }

    
    //Setters
    public void setNameMedicine(String nameMedcine) {
        this.nameMedicine = nameMedcine;
    }

    public void setBrandMedicine(String brandMedicine) {
        this.brandMedicine = brandMedicine;
    }

    public void setStockMedicine(String stockMedicine) {
        this.stockMedicine = stockMedicine;
    }

    public void setNumberMedicine(int numberMedicine) {
        this.numberMedicine = numberMedicine;
    }
    
}
