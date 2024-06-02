package ru.gb.Seminar06;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.ResultActions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.gb.Seminar06.controller.NoteController;
import ru.gb.Seminar06.model.Note;
import ru.gb.Seminar06.repository.NoteRepository;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - Модульный тест
 */

// Аннотация @WebMvcTest используется для тестирования контроллеров Spring MVC
@WebMvcTest(NoteController.class)
public class NoteContrallerModulTest {
    @MockBean
    NoteRepository noteController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    /**
     * Метод тестирует вывод всех заметок
     */
    @Test
    public void noteFindAllTest() throws Exception {
        // Блок предусловия
        List<Note> listNote = new ArrayList<>();
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("any text");
        note1.setContent("any content");
        note1.setLocalDateTime(LocalDateTime.now());
        listNote.add(note1);

        // Блок действия
        Mockito.when(noteController.findAll()).thenReturn(listNote);

        //Блок проверки
        mockMvc.perform(MockMvcRequestBuilders
                .get("/notes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("any text")))
                .andDo(print());
    }

    /**
     * Метод тестирует создание заметки
     */
    @Test
    public void creatNotetest() throws Exception {
        // Блок предусловия
        Note note2 = Note.builder().title("text").content("content").build();

        // Блок действия
        Mockito.when(noteController.save(note2)).thenReturn(note2);

        //Блок проверки
        mockMvc.perform(MockMvcRequestBuilders
                .post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(note2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("text")))
                .andDo(print());
    }
}
