package ru.gb.Seminar06;
import static org.hamcrest.Matchers.is;
import  static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.Seminar06.controller.NoteController;
import ru.gb.Seminar06.model.Note;
import ru.gb.Seminar06.repository.NoteRepository;
import ru.gb.Seminar06.service.NoteService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - Интеграционный тест
 */

@SpringBootTest
@AutoConfigureMockMvc
public class NoteContrallerItTest {

    @Autowired
    /**
     * тестовый фреймворк на стороне сервера,
     * который позволяет проверять большинство функциональных возможностей приложения
     * Spring MVC с помощью облегченных и целевых тестов
     */
    MockMvc mockMvc;

    @Mock // аннотация заглушка. Заменяет реальный объект в условиях теста
    NoteService noteService;
    @Mock // аннотация заглушка. Заменяет реальный объект в условиях теста
    NoteRepository noteRepository;

    /**
     *  аннотация создает экземпляр класса и внедряет в него макет, созданный с помощью аннотации @Mock.
     */
    @InjectMocks  // аннотация создает экземпляр класса и внедряет в него макет, созданный с помощью аннотации @Mock.
    NoteController noteController;

    /**
     *  аннотация вставляет метод перед тестом
     */
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }


    /**
     * Метод тестирует вывод всех заметок
     */
    @Test
    public void noteFindAllIntegrationTest() throws Exception {
        // Блок предусловия
        List<Note> listNoteIt = new ArrayList<>();
        Note note = Note.builder().title("a text").content("new content").build();
        Note note2 = Note.builder().title("b text").content("old content").build();
        listNoteIt.add(note);
        listNoteIt.add(note2);

        // Блок действия
        Mockito.when(noteRepository.findAll()).thenReturn(listNoteIt);


        //Блок проверки
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title", is("b text")))
                .andDo(print());

        Mockito.verify(noteRepository).findAll();

    }
}
