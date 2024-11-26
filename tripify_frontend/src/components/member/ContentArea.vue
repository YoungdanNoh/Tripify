<template>
    <div class="content-area">
        <Tabs @update:currentTab="updateCurrentTab" />
        <div class="content">
            <LikedPlaces v-if="currentTab === 'places'" />
            <MyPosts v-if="currentTab === 'posts'" />
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import Tabs from '@/components/member/Tabs.vue';
import LikedPlaces from '@/components/member/LikedPlaces.vue';
import MyPosts from '@/components/member/MyPosts.vue';

const currentTab = ref('places'); // 기본 탭 설정

function updateCurrentTab(tab) {
    currentTab.value = tab;
}
</script>

<style>
.content-area {
    flex: 1;
    padding: 20px;
}
</style>
