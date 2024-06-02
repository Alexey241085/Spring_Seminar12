package ru.gb.Seminar06.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NonNull
    private String title;

    @NonNull
    @Column(name = "content")
    private String content;

    @Column(name = "data_time")
    private LocalDateTime localDateTime = LocalDateTime.now().withNano(0);

}
