package ru.mirea.ItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    private final ItemService itemS;

    public ItemController(ItemService itemS) {
        this.itemS = itemS;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public @ResponseBody
    String greeting() {
        return itemS.greet();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Item getById(@PathVariable int id) {
        return itemS.getById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItems() {
        return itemS.geItems();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteItem(@PathVariable int id) {
        itemS.deleteItem(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> putItem(@RequestBody Map<String, ?> pet) {
        if (pet.containsKey("name")
                && pet.containsKey("type")
                && pet.containsKey("count")
                && pet.containsKey("price")
                && pet.get("name") instanceof String
                && pet.get("type") instanceof String
                && pet.get("count") instanceof Integer) {
            double price;
            if (pet.get("price") instanceof Integer)
                price = (Integer) pet.get("price");
            else
                price = (Double) pet.get("price");
            itemS.putItem(
                    (String) pet.get("name"),
                    (String) pet.get("type"),
                    (Integer) pet.get("count"),
                    price);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }
}
