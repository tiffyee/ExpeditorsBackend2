package ttl.mie.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ttl.mie.TrackerTestDataConfig;
import ttl.mie.controller.TrackController;
import ttl.mie.controller.UriCreator;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrackControllerUnitTest {

    @InjectMocks
    private TrackController controller;

    @Mock
    private TrackService trackService;

    @Mock
    private UriCreator uriCreator;

    List<TrackDTO> allTracks = new TrackerTestDataConfig().tracks();

    @BeforeEach
    public void init() { }

    @Test
    public void testGetAllWithNoParams() throws Exception {

        Map<String, String> queryStrings = Map.of();
        Mockito.when(trackService.getAllTracks()).thenReturn(allTracks);

        ResponseEntity<?> response = controller.getAllTracks(queryStrings);
        assertEquals(200, response.getStatusCodeValue());
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<TrackDTO> tracks = (List)response.getBody();
        assertEquals(6, tracks.size());

        Mockito.verify(trackService).getAllTracks();
    }

    @Test
    public void testGetAllWithSomeParams() throws Exception {

        Map<String, String> queryStrings = Map.of("title", "April");
        Mockito.when(trackService.getTracksBy(queryStrings)).thenReturn(List.of(allTracks.get(1)));

        ResponseEntity<?> response = controller.getAllTracks(queryStrings);
        assertEquals(200, response.getStatusCodeValue());
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<TrackDTO> tracks = (List)response.getBody();
        assertEquals(1, tracks.size());

        Mockito.verify(trackService).getTracksBy(queryStrings);
        Mockito.verify(trackService, Mockito.never()).getAllTracks();
    }

    @Test
    public void testGetAllTracksWithBody() throws Exception {

        TrackDTO example = TrackDTO.builder().title("April").build();
        Mockito.when(trackService.getTracksBy(example)).thenReturn(List.of(allTracks.get(1)));

        ResponseEntity<?> response = controller.getAllTracksWithBody(example);
        assertEquals(200, response.getStatusCodeValue());

        Mockito.verify(trackService).getTracksBy(example);
    }

    @Test
    public void testGetOneGood() throws Exception {

        int id = 1;
        Mockito.when(trackService.getById(id)).thenReturn(Optional.ofNullable(allTracks.get(0)));

        ResponseEntity<?> response = controller.getTrack(id);
        assertEquals(200, response.getStatusCodeValue());
        @SuppressWarnings({"unchecked", "rawtypes"})
        TrackDTO tracks = (TrackDTO)response.getBody();

        Mockito.verify(trackService).getById(id);
    }

    @Test
    public void testGetOneBad() throws Exception {

        int id = 10000;
        Mockito.when(trackService.getById(id)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<?> response = controller.getTrack(id);
        assertEquals(400, response.getStatusCodeValue());
        @SuppressWarnings({"unchecked", "rawtypes"})
        TrackDTO tracks = (TrackDTO)response.getBody();

        Mockito.verify(trackService).getById(id);
    }

    @Test
    public void testAddTrack() throws Exception {

        int newId = 10;
        TrackDTO newTrack = TrackDTO.builder().id(newId).title("Blue Serenade").artist("The Serenaders")
                .album("Sing at the Moon").length(315).year("1965").build();

        Mockito.when(trackService.addTrack(newTrack)).thenReturn(newTrack);
        String newLoc = "http://localhost:8080/track/10";
        URI newResource = new URI(newLoc);
        Mockito.when(uriCreator.getUriFor(newId)).thenReturn(newResource);

        ResponseEntity<?> response = controller.addTrack(newTrack);
        assertEquals(201, response.getStatusCodeValue());
        String locHeader = response.getHeaders().get("Location").get(0);
        assertEquals(newLoc, locHeader);

        Mockito.verify(trackService).addTrack(newTrack);
        Mockito.verify(uriCreator).getUriFor(newId);
    }

    @Test
    public void testDeleteGood() throws Exception {

        int id = 1;
        Mockito.when(trackService.deleteTrack(id)).thenReturn(true);

        ResponseEntity<?> response = controller.deleteTrack(id);
        assertEquals(204, response.getStatusCodeValue());

        Mockito.verify(trackService).deleteTrack(id);
    }

    @Test
    public void testDeleteBad() throws Exception {
        int id = 1000;
        Mockito.when(trackService.deleteTrack(id)).thenReturn(false);

        ResponseEntity<?> response = controller.deleteTrack(id);
        assertEquals(400, response.getStatusCodeValue());

        Mockito.verify(trackService).deleteTrack(id);
    }

    @Test
    public void testUpdateGood() throws Exception {

        int id = 1;
        TrackDTO track = allTracks.get(0);
        Mockito.when(trackService.updateTrack(track)).thenReturn(true);

        ResponseEntity<?> response = controller.updateTrack(track);
        assertEquals(204, response.getStatusCodeValue());

        Mockito.verify(trackService).updateTrack(track);
    }

    @Test
    public void testUpdateBad() throws Exception {
        int id = 1000;
        TrackDTO track = TrackDTO.copyWithId(id, allTracks.get(0));

        Mockito.when(trackService.updateTrack(track)).thenReturn(false);

        ResponseEntity<?> response = controller.updateTrack(track);
        assertEquals(400, response.getStatusCodeValue());

        Mockito.verify(trackService).updateTrack(track);
    }
}
