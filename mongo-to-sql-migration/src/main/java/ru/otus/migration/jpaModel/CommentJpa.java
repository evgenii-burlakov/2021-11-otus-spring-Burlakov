package ru.otus.migration.jpaModel;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "book")
@ToString(exclude = "book")
@Entity
@Table(name = "COMMENTS")
public class CommentJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private BookJpa book;
}
