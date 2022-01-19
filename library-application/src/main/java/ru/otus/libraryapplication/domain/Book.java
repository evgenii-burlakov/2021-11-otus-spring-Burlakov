package ru.otus.libraryapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToOne(targetEntity = Author.class)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Author author;

    @OneToOne(targetEntity = Genre.class)
    @JoinColumn(name = "GENRE_ID", nullable = false)
    private Genre genre;
}
