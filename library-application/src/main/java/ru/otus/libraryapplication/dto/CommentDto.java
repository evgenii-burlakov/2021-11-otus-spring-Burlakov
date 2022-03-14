package ru.otus.libraryapplication.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.otus.libraryapplication.domain.Comment;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class CommentDto {
    private String id;

    private String comment;
    private String bookId;

    public CommentDto() {
    }

    public CommentDto(String id, String comment, String bookId) {
        this.id = id;
        this.comment = comment;
        this.bookId = bookId;
    }

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .bookId(comment.getBookId())
                .build();
    }

    public Comment toBean() {
        return new Comment(id, comment, bookId);
    }
}
