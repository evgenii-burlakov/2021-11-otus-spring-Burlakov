package ru.otus.migration.jpaModel;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
public class BookJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToOne(targetEntity = AuthorJpa.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private AuthorJpa author;

    @ManyToOne(targetEntity = GenreJpa.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "GENRE_ID", nullable = false)
    private GenreJpa genre;

    public BookJpa(Long id, String name, AuthorJpa author, GenreJpa genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
