package app.milanpizza.menucatalog.repository;

import app.milanpizza.menucatalog.domain.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {

    List<Menu> findByRegionCode(String regionCode);

    List<Menu> findByIsActive(boolean isActive);

    @Query("{'validFrom': {$lte: ?0}, 'validTo': {$gte: ?0}}")
    List<Menu> findActiveMenusByDate(LocalDate date);

    @Query("{'regionCode': ?0, 'isActive': true}")
    List<Menu> findActiveMenusByRegion(String regionCode);

    boolean existsByRegionCodeAndName(String regionCode, String name);

    List<Menu> findByValidFromBetween(LocalDate startDate, LocalDate endDate);
}