package api.models.business;

public interface Business {

    String name();

    String type();

    Integer deleteTime();

    Integer maxWorkers();

    Integer maxBalance();

    Integer price();

    Integer minSalary();

    Integer maxSalary();

    Integer bankruptcyChance();

    String achievements();

}
