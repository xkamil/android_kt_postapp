package pl.canx.myapp3.api.dto

data class PostDTO(
    var id: Int? = null,
    var title: String,
    var text: String,
    var comments: List<CommentDTO>? = null
)