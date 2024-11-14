<template>
    <li class="place-item">
        <img :src="place.image" alt="place image" />
        <div class="place-info">
            <h2>{{ place.name }}</h2>
            <p>{{ place.details }}</p>
            <p>{{ place.rating }} ⭐ - {{ place.host }}</p>
            <p>{{ place.guests }} guests | {{ place.bedroom }} bedroom | {{ place.bathroom }} bathroom</p>
        </div>
    </li>
</template>
  
<script>
export default {
    props: ['place'],
};
</script>
  
<style scoped>
.place-item {
    display: flex;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
}

.place-info {
    margin-left: 10px;
}
</style>
  