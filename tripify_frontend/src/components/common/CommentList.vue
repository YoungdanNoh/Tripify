<template>
    <div class="comment-list">
        <div v-if="commentStore.loading">댓글을 불러오는 중입니다...</div>
        <div v-else-if="commentStore.error">{{ commentStore.error }}</div>
        <div v-else-if="commentStore.comments.length === 0">아직 댓글이 없습니다.</div>
        <ul v-else>
            <li v-for="comment in commentStore.comments" :key="comment.commentId || comment.createdAt" class="comment-item">
                <div class="comment-header">
                    <div class="comment-info">
                        <div>
                            <strong>{{ comment.userName }}</strong>
                            <small>{{ formatDate(comment.createdAt) }}</small>
                        </div>
                        <button v-if="comment.userId === currentUserId" class="delete-button"
                            @click="handleDelete(comment.commentId)">
                            X
                        </button>
                    </div>
                </div>
                <p class="comment-content">{{ comment.content }}</p>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref } from "vue";
import { useUserStore } from "@/stores/user"; // 사용자 스토어 가져오기
import { useCommentStore } from "@/stores/comment"; // 댓글 스토어 가져오기

const props = defineProps({
    itemId: {
        type: Number,
        required: true
    },
    type: {
        type: String,
        required: true
    }
});

const userStore = useUserStore();
const commentStore = useCommentStore();

const currentUserId = userStore.user?.userId || null;

const loading = ref(false);
const error = ref("");

// 날짜 포맷팅 함수
function formatDate(dateString) {
    const date = new Date(dateString);
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
}

async function handleDelete(commentId) {
    if (confirm("이 댓글을 삭제하시겠습니까?")) {
        try {
            await commentStore.deleteComment(props.type, commentId);
            alert("댓글이 삭제되었습니다.");
        } catch (error) {
            alert(commentStore.error || "댓글 삭제에 실패했습니다.");
        }
    }
}

// 댓글 로드 함수
async function loadComments() {
    loading.value = true;
    try {
        await commentStore.fetchComments(props.type, props.itemId); // type과 itemId로 변경
    } catch (err) {
        error.value = commentStore.error || "댓글을 불러오는 데 실패했습니다.";
        console.error(err);
    } finally {
        loading.value = false;
    }
}

onMounted(() => {
    loadComments();
});

</script>
  
<style scoped>
.comment-list {
    max-height: 300px;
    /* 고정된 높이 */
    overflow-y: auto;
    /* 스크롤 활성화 */
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #f9f9f9;
}

.comment-item {
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.comment-item {
    list-style: none;
    /* 동그라미 모양 제거 */
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #ddd;
}

.comment-item strong {
    margin-right: 10px;
    /* 유저네임과 날짜 사이 간격 */
}

.comment-item small {
    color: gray;
    /* 날짜를 덜 강조 */
}

.comment-item p {
    margin-top: 5px;
    /* 댓글 내용과 유저 정보 간 간격 */
}

.comment-item {
    list-style: none;
    /* 동그라미 모양 제거 */
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #ddd;
}

.comment-item strong {
    margin-right: 10px;
    /* 유저네임과 날짜 사이 간격 */
}

.comment-item small {
    color: gray;
    /* 날짜를 덜 강조 */
}

.comment-item p {
    margin-top: 5px;
    /* 댓글 내용과 유저 정보 간 간격 */
}

ul {
    padding-inline-start: 0;
    /* 기본 왼쪽 패딩 제거 */
    margin: 0;
    /* 필요 시 기본 여백도 제거 */
    list-style: none;
    /* 필요 시 리스트 스타일도 제거 */
}

.comment-info {
    display: flex;
    /* 가로 배치 */
    justify-content: space-between;
    /* 왼쪽은 div, 오른쪽은 버튼 배치 */
    align-items: center;
    /* 세로 중앙 정렬 */
    margin-bottom: 5px;
    /* 댓글 내용과 간격 */
}

.comment-info div {
    display: flex;
    /* 작성자와 날짜를 가로로 배치 */
    gap: 10px;
    /* 작성자와 날짜 사이 간격 */
}

.delete-button {
    background: none;
    border: none;
    color: red;
    cursor: pointer;
    font-size: 14px;
    padding: 0;
    /* 버튼 여백 제거 */
}

.delete-button:hover {
    text-decoration: underline;
    /* 호버 시 강조 */
}
</style>