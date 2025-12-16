package com.example.afinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.afinal.ui.theme.MyAppTheme

// ----------------------
// 데이터 모델
// ----------------------
data class Post(
    val id: Int,
    val content: String,
    val timestamp: Long
)

// ----------------------
// 간단 상태 관리 (ViewModel 대체)
// ----------------------
class FeedState {
    private var nextId = 0

    var posts by mutableStateOf(listOf<Post>())
        private set

    fun addPost(text: String) {
        if (text.isBlank()) return
        val newPost = Post(
            id = nextId++,
            content = text,
            timestamp = System.currentTimeMillis()
        )
        posts = listOf(newPost) + posts
    }
}

// ----------------------
// 게시물 UI
// ----------------------
@Composable
fun PostItem(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = post.content, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(5.dp))
        Text(
            text = java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                java.util.Locale.getDefault()
            ).format(java.util.Date(post.timestamp)),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

// ----------------------
// 피드 화면
// ----------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(state: FeedState, onWriteClick: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onWriteClick) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(state.posts) { post ->
                PostItem(post)
                Divider()
            }
        }
    }
}

// ----------------------
// 글 작성 화면
// ----------------------
@Composable
fun WritePostScreen(state: FeedState, onDone: () -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("내용을 입력하세요") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                state.addPost(text)
                onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("게시하기")
        }
    }
}

// ----------------------
// MainActivity
// ----------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyAppTheme {

                val navController = rememberNavController()
                val feedState = remember { FeedState() }

                NavHost(
                    navController = navController,
                    startDestination = "feed"
                ) {
                    composable("feed") {
                        FeedScreen(feedState) {
                            navController.navigate("write")
                        }
                    }
                    composable("write") {
                        WritePostScreen(feedState) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}
