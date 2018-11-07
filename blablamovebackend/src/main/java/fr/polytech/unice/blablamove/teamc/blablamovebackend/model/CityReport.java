package fr.polytech.unice.blablamove.teamc.blablamovebackend.model;

public class CityReport {
    private City city;
    private Long transactionCount;

    public CityReport(City city, Long transactionCount) {
        this.city = city;
        this.transactionCount = transactionCount;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
    }
}
