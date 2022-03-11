package ru.otus.libraryapplication.controller.comment;


//@RestController
//@RequiredArgsConstructor
public class CommentController {
//    private final CommentService commentService;
//
//    @GetMapping("/api/comments/{id}")
//    public CommentDto getCommentById(@PathVariable("id") Long id) {
//        return CommentDto.toDto(commentService.getById(id));
////    }
////
//    @GetMapping("/api/comments")
//    public List<CommentDto> getCommentByBookId(@RequestParam("bookId") Long bookId) {
//        return commentService.getAllByBookId(bookId).stream()
//                .map(CommentDto::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping("/api/comments")
//    public ResponseEntity<Comment> createComment(CommentDto comment) {
//        commentService.create(comment.getComment(), comment.getBook().getId());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PatchMapping("/api/comments/{id}")
//    public ResponseEntity<Comment> updateComment(CommentDto comment) {
//        commentService.update(comment.getId(), comment.getComment(), comment.getBook().getId());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/api/comments/{id}")
//    public ResponseEntity<Comment> deleteComment(@PathVariable("id") Long id) {
//        commentService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
}
