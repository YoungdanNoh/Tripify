<template>
    <div class="community-view">
        <h2>커뮤니티 게시판</h2>
        <BoardList :posts="posts" @viewPost="viewPost" />
        <div v-if="selectedPost" class="post-details">
            <h3>{{ selectedPost.title }}</h3>
            <p>{{ selectedPost.content }}</p>
            <button @click="selectedPost = null">목록으로 돌아가기</button>
        </div>
    </div>
</template>
  
<script>
import BoardList from '../components/BoardList.vue';

export default {
    components: { BoardList },
    data() {
        return {
            posts: [
                { id: 1, title: '첫 번째 게시물', content: '이것은 첫 번째 게시물의 내용입니다.' },
                { id: 2, title: '두 번째 게시물', content: '이것은 두 번째 게시물의 내용입니다.' },
                { id: 3, title: '세 번째 게시물', content: '이것은 세 번째 게시물의 내용입니다.' },
            ],
            selectedPost: null,
        };
    },
    methods: {
        viewPost(post) {
            this.selectedPost = post;
        },
    },
};
</script>
  
<style scoped>
.community-view {
    padding: 20px;
    height: 100%;
}

.post-details {
    margin-top: 20px;
    padding: 10px;
    border: 1px solid #ddd;
}
</style>
  