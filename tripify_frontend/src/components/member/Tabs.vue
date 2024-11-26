<template>
    <div class="tabs">
        <div class="tab" :class="{ active: currentTab === 'places' }" @click="setCurrentTab('places')">
            좋아하는 장소
        </div>
        <div class="tab" :class="{ active: currentTab === 'posts' }" @click="setCurrentTab('posts')">
            내 게시글
        </div>
    </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const currentTab = ref('places');
const emit = defineEmits(['update:currentTab']);

function setCurrentTab(tab) {
    console.log(currentTab.value);
    currentTab.value = tab;
    emit('update:currentTab', tab);
}
</script>


<style>
.tabs {
    display: flex;
}

.tab {
    padding: 10px 20px;
    cursor: pointer;
}

.tab .active {
    font-weight: bold;
    color: blue;
}
</style>
