<template>
    <ul class="board-list">
        <li v-for="post in posts" :key="post.id" @click="$emit('viewPost', post)">
            {{ post.title }}
        </li>
    </ul>
</template>
  
<script>
export default {
    props: ['posts'],
};
</script>
  
<style scoped>
.board-list {
    list-style-type: none;
    padding: 0;
}

.board-list li {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    cursor: pointer;
}

.board-list li:hover {
    background-color: #f0f0f0;
}
</style>
  