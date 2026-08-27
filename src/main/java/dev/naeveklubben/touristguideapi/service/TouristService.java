package dev.naeveklubben.touristguideapi.service;

import dev.naeveklubben.touristguideapi.model.TouristAttraction;
import dev.naeveklubben.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getAttractions() {
        return repository.getAllAttractions();
    }

    public TouristAttraction findTouristAttractionByName(String name) {
        TouristAttraction touristAttraction = repository.findAttractionByName(name);
        return touristAttraction;
    }

}
