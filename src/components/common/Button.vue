<template>
    <button @click="$emit('click')">{{ text }}</button>
</template>
  
<script>
export default {
    props: ['text'],
};
</script>
  