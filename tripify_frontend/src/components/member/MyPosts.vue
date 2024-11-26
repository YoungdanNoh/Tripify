<template>
    <div>
        <table>
            <thead>
                <tr>
                    <th style="width: 20%;">제목</th>
                    <th style="width: 65%;">내용</th>
                    <th style="width: 15%;">작성일</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="post in posts" :key="post.postId" @click="openModal(post)">
                    <td>{{ post.title }}</td>
                    <td>{{ post.content }}</td>
                    <td>{{ formatDate(post.createdAt) }}</td>
                </tr>
            </tbody>
        </table>
        <PostDetailModal v-if="showModal" :post="selectedPost" @close="handleClose" @updateList="handleUpdateList" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { fetchPostsByUserId } from '@/api/posts';
import { useUserStore } from "@/stores/user";
import PostDetailModal from '@/components/Board/PostDetailModal.vue'

const posts = ref([]);
const showModal = ref(false);
const selectedPost = ref(null);
const userStore = useUserStore();

const userId = userStore.getUserId(); // 예시로 사용된 userId, 실제로는 동적으로 받아야 함.

onMounted(async () => {
    const response = await fetchPostsByUserId(userId);
    posts.value = response.data;
});

function formatDate(date) {
    const newDate = new Date(date);
    return newDate.getFullYear().toString().substr(-2) + '.' +
        (newDate.getMonth() + 1).toString().padStart(2, '0') + '.' +
        newDate.getDate().toString().padStart(2, '0');
}

function handleClose() {
    showModal.value = false;
    selectedPost.value = null;
}

function handleUpdateList(deletedPostId) {
    posts.value = posts.value.filter(post => post.postId !== deletedPostId);
}

function openModal(post) {
    selectedPost.value = post;
    showModal.value = true;
}
</script>

<style scoped>
table {
    width: 100%;
    border-collapse: collapse;
}

th,
td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: left;
}

tr:hover {
    background-color: #f5f5f5;
}

thead th {
    background-color: #f0f0f0;
    /* 연한 회색 배경 */
    color: #000;
    /* 검은색 글자 */
}

td {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    /* 줄바꿈을 하지 않음 */
}

th {
    text-align: center;
}
</style>
