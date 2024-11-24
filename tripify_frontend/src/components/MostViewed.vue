<template>
  <div class="most-viewed">
    <div class="container">
      <h2>가장 많이 조회된 장소</h2>
      <div class="row">
        <div class="col-md-6" v-for="view in mostViewed" :key="view.title"> <!-- 여기서 col-md-6을 사용하여 각 카드가 6열을 차지하도록 설정 -->
          <div class="card mb-4"> <!-- mb-4는 카드 간에 마진을 추가합니다 -->
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
  /* 필요에 따라 추가적인 스타일을 여기에 적용할 수 있습니다. */
</style>
