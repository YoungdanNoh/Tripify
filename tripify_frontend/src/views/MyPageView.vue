<template>
    <div class="main-layout">
        <UserProfile class="user-profile" />
        <ContentArea class="content-area" />
    </div>
</template>
  
<script>
import UserProfile from '@/components/member/UserProfile.vue';
import ContentArea from '@/components/member/ContentArea.vue';

export default {
    components: {
        UserProfile,
        ContentArea
    }
}
</script>
  
<style>
.main-layout {
    display: flex;
}

.user-profile {
    width: 23%;
    background-color: #f5f5f5;
}
</style>
  