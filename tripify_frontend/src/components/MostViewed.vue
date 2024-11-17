<template>
    <div class="most-viewed">
      <div class="container">
        <h2>가장 많이 조회된 장소</h2>
        <div class="row">
          <div class="col-md-3" v-for="view in mostViewed" :key="view.title">
            <div class="card">
              <img :src="view.image" class="card-img-top" :alt="view.title" />
              <div class="card-body">
                <h5 class="card-title">{{ view.title }}</h5>
                <p class="card-text">{{ view.description }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        mostViewed: [
          { title: "장소 1", image: "https://via.placeholder.com/400x200?text=장소1", description: "장소 1의 간략한 설명." },
          { title: "장소 2", image: "https://via.placeholder.com/400x200?text=장소2", description: "장소 2의 간략한 설명." },
          { title: "장소 3", image: "https://via.placeholder.com/400x200?text=장소3", description: "장소 3의 간략한 설명." },
          { title: "장소 4", image: "https://via.placeholder.com/400x200?text=장소4", description: "장소 4의 간략한 설명." },
        ],
      };
    },
  };
  </script>
  
  <style scoped>
  .most-viewed {
    padding: 50px 0;
  }
  </style>
  