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
import static org.mockito.Mockito.never;
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
                                "Vesterbrogade 3, 1630 København V",
                                List.of(City.KØBENHAVN),
                                List.of(Tag.FORLYSTELSESPARK, Tag.BØRNEVENLIG)),
                        new Attraction(
                                "Aros",
                                "Kunstmuseum",
                                "Aros Allé 2, 8000 Aarhus C",
                                List.of(City.AARHUS),
                                List.of(Tag.MUSEUM)
                        ));

        //Defines what the mocked service should return
        when(attractionService.findAllAttractions())
                .thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions"))
                .andExpect(model().attribute("attractions", attractions));

        //Verifies that the controller called the service
        verify(attractionService).findAllAttractions();
    }

    //Tests that an attraction can be found using its name as a path variable
    @Test
    void shouldGetAttractionByName() throws Exception {

        Attraction attraction = new Attraction(
                "Tivoli",
                "Sjovt sted og flot til Halloween",
                "Vesterbrogade 3, 1630 København V",
                List.of(City.KØBENHAVN),
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
                        .param("address", "Vesterbrogade 3, 1630 København V")
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

        when(attractionService.updateAttraction(eq("Tivoli"), any(Attraction.class)))
                .thenReturn(true);

        mockMvc.perform(post("/attractions/Tivoli/edit")
                        .param("name", "Tivoli")
                        .param("description", "Opdateret Tivoli description her")
                        .param("address", "Vesterbrogade 3, 1630 København V")
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

    //Tests that the add form is shown again with an error when the name is already taken
    @Test
    void shouldNotAddDuplicateAttraction() throws Exception {
        when(attractionService.attractionExists("Tivoli")).thenReturn(true);

        mockMvc.perform(post("/attractions/add")
                        .param("name", "Tivoli")
                        .param("address", "Vesterbrogade 3, 1630 København V")
                        .param("city", "KØBENHAVN"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-attraction"))
                .andExpect(model().attributeExists("error"));

        verify(attractionService, never()).addAttraction(any(Attraction.class));
    }

    //Tests that the edit form is filled with the attraction from the service
    @Test
    void shouldShowUpdateForm() throws Exception {
        Attraction attraction = new Attraction(
                "Tivoli",
                "Sjovt sted",
                "Vesterbrogade 3, 1630 København V",
                List.of(City.KØBENHAVN),
                List.of(Tag.FORLYSTELSESPARK));
        when(attractionService.getAttractionByName("Tivoli")).thenReturn(attraction);

        mockMvc.perform(get("/attractions/Tivoli/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-attraction"))
                .andExpect(model().attribute("attraction", attraction));
    }

    //Tests that editing an attraction that doesn't exist gives 404
    @Test
    void shouldReturnNotFoundWhenUpdatingUnknown() throws Exception {
        when(attractionService.updateAttraction(eq("Unknown"), any(Attraction.class)))
                .thenReturn(false);

        mockMvc.perform(post("/attractions/Unknown/edit")
                        .param("description", "x"))
                .andExpect(status().isNotFound());
    }

    //Tests that the delete confirmation page gives 404 for an unknown attraction
    @Test
    void shouldReturnNotFoundOnDeleteConfirmationForUnknown() throws Exception {
        when(attractionService.getAttractionByName("Unknown")).thenReturn(null);

        mockMvc.perform(get("/attractions/Unknown/delete"))
                .andExpect(status().isNotFound());
    }
}
