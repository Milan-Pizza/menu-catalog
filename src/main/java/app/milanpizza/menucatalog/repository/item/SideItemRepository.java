package app.milanpizza.menucatalog.repository.item;

import app.milanpizza.menucatalog.domain.item.SideItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SideItemRepository extends MongoRepository<SideItem, String> {

    List<SideItem> findByMenuId(String menuId);

    List<SideItem> findByMenuIdAndIsAvailable(String menuId, boolean isAvailable);

    List<SideItem> findByCategory(String category);

    List<SideItem> findByIsVegetarian(boolean isVegetarian);

    List<SideItem> findByIsVegan(boolean isVegan);
}