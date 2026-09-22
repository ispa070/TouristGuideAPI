package dev.naeveklubben.touristguideapi.controller;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.City;
import dev.naeveklubben.touristguideapi.model.Tag;
import dev.naeveklubben.touristguideapi.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Tests only the web/controller layer for AttractionController
@WebMvcTest(AttractionController.class)
class AttractionControllerTest {

    //Simulates HTTP requests to the controller
    @Autowired
    private MockMvc mockMvc;

    //Replaces the real AttractionService with a Mockito mock
    @MockitoBean
    private AttractionService attractionService;

    //Tests that all attractions are added to the model
    //and displayed using the correct Thymeleaf view
    @Test
    void shouldShowAttractions() throws Exception {

        List<Attraction> attractions = List.of
                (new Attraction(
                                "Tivoli",
                                "Sjovt sted og flot til Halloween",
                                City.KØBENHAVN,
                                List.of(Tag.FORLYSTELSESPARK, Tag.BØRNEVENLIG)),
                        new Attraction(
                                "Aros",
                                "Kunstmuseum",
                                City.AARHUS,
                                List.of(Tag.MUSEUM)
                        ));

        //Defines what the mocked service should return
        when(attractionService.getAttractions())
                .thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions"))
                .andExpect(model().attribute("attractions", attractions));

        //Verifies that the controller called the service
        verify(attractionService).getAttractions();
    }

    //Tests that an attraction can be found using its name as a path variable
    @Test
    void shouldGetAttractionByName() throws Exception {

        Attraction attraction = new Attraction(
                "Tivoli",
                "Sjovt sted og flot til Halloween",
                City.KØBENHAVN,
                List.of(Tag.FORLYSTELSESPARK, Tag.BØRNEVENLIG));

        when(attractionService.getAttractionByName("Tivoli"))
                .thenReturn(attraction);

        mockMvc.perform(get("/attractions/Tivoli"))
                .andExpect(status().isOk());

        verify(attractionService).getAttractionByName("Tivoli");
    }

    //Tests that the controller returns 404 when an attraction does not exist
    @Test
    void shouldReturnNotFound() throws Exception {
        when(attractionService.getAttractionByName("Unknown"))
                .thenReturn(null);

        mockMvc.perform(get("/attractions/Unknown"))
                .andExpect(status().isNotFound());

        verify(attractionService).getAttractionByName("Unknown");
    }

    //Tests submitting the add-attraction form and redirecting after
    @Test
    void shouldAddAttraction() throws Exception {

        mockMvc.perform(post("/attractions/add")
                        .param("name", "Tivoli")
                        .param("description", "Sjovt sted og flot til Halloween")
                        .param("city", "KØBENHAVN")
                        .param("tags", "FORLYSTELSESPARK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        //any() accepts any object of the specified type
        verify(attractionService).addAttraction(any(Attraction.class));
    }

    //Tests updating an attraction using a path variable and form data
    @Test
    void shouldUpdateAttraction() throws Exception {

        mockMvc.perform(post("/attractions/Tivoli/edit")
                        .param("name", "Tivoli")
                        .param("description", "Opdateret Tivoli description her")
                        .param("city", "KØBENHAVN")
                        .param("tags", "BØRNEVENLIG"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        //eq() requires the argument to match this exact value
        verify(attractionService).updateAttraction(eq("Tivoli"), any(Attraction.class));
    }

    //Tests deleting an attraction using its name as a path variable
    @Test
    void shouldDeleteAttraction() throws Exception{

        mockMvc.perform(post("/attractions/Tivoli/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(attractionService).deleteAttraction("Tivoli");
    }
}
