package pl.canx.myapp3.api.dto

data class CommentDTO(
    var id: Int,
    var postId: Int,
    var userId: Int,
    var text: String
)