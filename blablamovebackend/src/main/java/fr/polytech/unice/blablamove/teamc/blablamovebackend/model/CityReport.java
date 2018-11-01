package fr.polytech.unice.blablamove.teamc.blablamovebackend.model;

public class CityReport {
    private City city;
    private Integer transactionCount;

    public CityReport(City city, Integer transactionCount) {
        this.city = city;
        this.transactionCount = transactionCount;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }
}
