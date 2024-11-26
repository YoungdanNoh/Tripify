<template>
    <div class="post-detail-container" @click.self="close">
        <div class="modal-content">
            <div class="header-line">
                <h1>{{ post.title }}</h1>
                <div>
                    <div v-if="isCurrentUserPost && !editMode" @click="deletePostOnServer" class="button">
                        <font-awesome-icon class="h-7 w-7 icons" icon="trash-can"></font-awesome-icon>
                    </div>
                    <div v-if="isCurrentUserPost && !editMode" class="button" @click="toggleEditMode">
                        <font-awesome-icon class="h-7 w-7 icons" icon="pen-to-square"></font-awesome-icon>
                    </div>
                    <font-awesome-icon class="h-7 w-7 icons close" icon="xmark" @click="close"></font-awesome-icon>
                </div>
            </div>
            <div class="meta-container">
                <p class="meta">작성일: {{ formatDate(post.createdAt) }}</p>
                <p class="meta">최근 수정일: {{ formatDate(post.updatedAt) }}</p>
            </div>
            <hr>
            <div v-if="!editMode">
                <p class="content">{{ post.content }}</p>
            </div>
            <textarea v-if="editMode" v-model="editContent" class="edit-content"></textarea>
            <div class="edit-menu">
                <div v-if="editMode" @click="cancelEdit">
                    <font-awesome-icon class="h-7 w-7 icons" icon="angles-left"></font-awesome-icon>
                </div>
                <div v-if="editMode" @click="saveEdit">
                    <font-awesome-icon class="h-7 w-7 icons" icon="check"></font-awesome-icon>
                </div>
            </div>
            <div class="footer-line">
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, computed } from 'vue';
import { useUserStore } from "@/stores/user";
import { updatePost, deletePost } from "@/api/posts";

const props = defineProps({
    post: Object
});

const emits = defineEmits(['close', 'updateList']);

const userStore = useUserStore();
const editMode = ref(false);
const editContent = ref('');

const isCurrentUserPost = computed(() => {
    return userStore.getUserId() === props.post.userId;
});

function formatDate(date) {
    const newDate = new Date(date);
    return newDate.getFullYear().toString().substr(-2) + '.' +
        (newDate.getMonth() + 1).toString().padStart(2, '0') + '.' +
        newDate.getDate().toString().padStart(2, '0');
}

function toggleEditMode() {
    editMode.value = true;
    editContent.value = props.post.content;
}

function cancelEdit() {
    editMode.value = false;
}

function saveEdit() {
    updatePost(props.post.postId, props.post.title, editContent.value).then(() => {
        props.post.content = editContent.value;
        editMode.value = false;
    });
}

async function deletePostOnServer() {
    try {
        await deletePost(props.post.postId);
        emits('updateList', props.post.postId);
        emits('close');
    } catch (error) {
        console.error('Failed to delete post:', error);
    }
}

function close() {
    emits("close");
}
</script>

<style scoped>
.post-detail-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.5);
    /* 반투명 배경 */
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.modal-content {
    background-color: white;
    border-radius: 8px;
    padding: 20px;
    width: 90%;
    max-width: 600px;
    /* 최대 너비 제한 */
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    /* 그림자 효과 */
    position: relative;
}

.header-line,
.footer-line {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.meta-container {
    display: flex;
    justify-content: space-between;
    font-size: 0.9em;
    color: #888;
    margin-top: 10px;
    /* 상단 간격 추가 */
}

h1 {
    font-size: 2em;
    /* 제목 크기 조정 */
}

.content {
    margin-top: 10px;
    font-size: 1.1em;
    /* 내용 폰트 크기 조정 */
    background-color: #f9f9f9;
    /* 내용 배경색 추가 */
    padding: 10px;
    /* 패딩 추가 */
    border-radius: 4px;
    /* 모서리 둥글게 처리 */
}

.edit-content {
    width: 100%;
    height: 150px;
    margin-top: 10px;
    /* 상단 간격 추가 */
    background-color: #f9f9f9;
    /* 편집 영역 배경색 추가 */
    padding: 10px;
    /* 패딩 추가 */
    border-radius: 4px;
    /* 모서리 둥글게 처리 */
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 16px;
    cursor: pointer;
}

.edit-menu {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
    /* 상단 간격 추가 */
}

.button {
    display: inline;
}

.icons {
    margin-right: 20px;
    /* 아이��� 간격 */

    cursor: pointer;
}

.close {
    color: red;
}
</style>
