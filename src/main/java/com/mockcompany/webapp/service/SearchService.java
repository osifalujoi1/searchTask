package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SearchService{

    private final ProductItemRepository productItemRepository;
    public SearchService(ProductItemRepository productItemRepository){
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query){
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        for (ProductItem item : allItems) {
            // TODO: Figure out if the item should be returned based on the query parameter!
            boolean matchesSearch = true;
            if (query.startsWith("\"") && query.endsWith("\"")){
                String exactQuery = query.substring(1, query.length()-1).toLowerCase();
                if (item.getName().toLowerCase().equals(exactQuery) || item.getDescription().toLowerCase().equals(exactQuery)){
                    itemList.add(item);
                }
            }
            else if (item.getName().toLowerCase().contains(query.toLowerCase()) || item.getDescription().toLowerCase().contains(query.toLowerCase())){
                itemList.add(item);
            }
        }
        return itemList;
    }

    public Collection<ProductItem> search(Pattern query){
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        for (ProductItem item : allItems) {
            if (query.matcher(item.getName()).matches() || query.matcher(item.getDescription()).matches()) {
                itemList.add(item);
            }
        }
        return itemList;
    }
}