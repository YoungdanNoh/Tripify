<template>
  <div class="most-viewed">
    <div class="container">
      <h2 class="do-hyeon-regular">지금 핫해요🔥</h2>
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
        { title: "여수 밤바다", image: "https://res.cloudinary.com/kyte/image/upload/w_1200,h_630,q_auto,e_sharpen:50,c_fill,g_auto/v1605842552/content/shutterstock/KR/yeosu_0", description: "자동으로 콧노래가 나오는 곳! 아름다운 야경을 즐겨보세요." },
        { title: "화천 조경철 천문대", image: "https://blog.kakaocdn.net/dn/eaIGkv/btss9hGWvZJ/rrBH3MQKtDSZidvyk5yMBK/img.webp", description: "별 보러 갈래? 쏟아질 것 같은 별들을 눈에 담아보세요." },
        { title: "용인 민속촌", image: "https://www.yongin.go.kr/resources/user/yitour/img/content/010103_3.JPG", description: "오늘은 조선이다! 한국의 정취에 흠뻑 빠져보세요." },
        { title: "광주 무등산 국립공원", image: "https://upload.wikimedia.org/wikipedia/commons/4/49/%EB%AC%B4%EB%93%B1%EC%82%B0_%EC%A3%BC%EC%83%81%EC%A0%88%EB%A6%AC%EB%8C%80.jpg", description: "깎아 만든 듯한 주상 절리가 아름다운 곳. 가을엔 등산 어때요?" },
      ],
    };
  },
};
</script>

<style scoped>


.most-viewed {
}

.card img {
  height: 100px;
  object-fit: cover;
}



</style>
