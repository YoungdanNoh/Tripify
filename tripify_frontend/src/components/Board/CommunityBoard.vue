<template>
  <div>
    <h2>실시간 커뮤니티</h2>
    <table>
      <thead>
        <tr>
          <th>작성시간</th>
          <th>글제목</th>
          <th>작성자</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="post in posts.slice(0, 6)" :key="post.postId || post.tempKey" @click="() => viewPost(post.postId)" class="post-row">
          <td>{{ post.createdAt ? new Date(post.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true }) : '-' }}</td>
          <td>{{ post.title }}</td>
          <td>{{ post.userName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>



<script setup>
import { ref, computed, onMounted } from 'vue';
import { fetchPosts } from '@/api/posts';
import { useRouter } from 'vue-router';

const posts = ref([]);
const currentPage = ref(1);
const limit = ref(6);
const router = useRouter();

onMounted(async () => {
  const response = await fetchPosts(currentPage.value, limit.value);
  posts.value = response.data.posts;
  
  // 배열을 6개의 항목으로 채우기
  while (posts.value.length < 6) {
    posts.value.push({ 
      postId: null,
      userName: '-', 
      title: '-', 
      createdAt: null,
      tempKey: `temp-${posts.value.length}`
    });
  }
});

const filledPosts = computed(() => {
  return posts.value.map((post, index) => ({
    ...post,
    postId: post.postId || null,
    userName: post.userName || null,
    title: post.title || null,
    createdAt: post.createdAt || null
  }));
});

const viewPost = (postId) => {
  if (postId) {
    router.push({ name: 'PostDetail', params: { postId } });
  }
};
</script>

<style scoped>
table {
  width: 100%; 
  table-layout: fixed;
  border-collapse: collapse; /* 테이블의 경계선을 합쳐 행 간격을 없앱니다. */
  border-spacing: 0; /* 경계 사이의 공간을 0으로 설정 */
}

.post-row {
  cursor: pointer;
  height: 40px; /* 필요에 따라 각 행의 높이 조정 */
  padding: 5px 10px; /* 각 셀의 내부 여백 조정 */
}

td {
  padding: 8px; /* 각 셀의 내부 여백을 줄임 */
}
</style>
