<template>
    <div class="popular-destinations">
      <div class="container">
        <h2>인기 관광지</h2>
        <div class="row">
          <div class="col-md-4" v-for="destination in destinations" :key="destination.title">
            <div class="card destination-card">
              <img :src="destination.image" class="card-img-top" :alt="destination.title" />
              <div class="card-body">
                <h5 class="card-title">{{ destination.title }}</h5>
                <p class="card-text">{{ destination.description }}</p>
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
        destinations: [
          {
            title: "서울",
            image: "https://love.seoul.go.kr/tmda/Pds/Board/seoul_news_write/201712_06_1.jpg",
            description: "전통과 현대가 공존하는 대한민국의 수도.",
          },
          {
            title: "부산",
            image: "https://www.visitbusan.net/uploadImgs/files/cntnts/20191229153530528",
            description: "아름다운 해변과 맛있는 음식으로 유명한 항구 도시.",
          },
          {
            title: "제주",
            image: "https://www.lottehotel.com/content/dam/lotte-hotel/lotte/jeju/overview/introduction/g-0807.jpg.thumb.768.768.jpg",
            description: "천혜의 자연 경관을 자랑하는 한국의 대표 휴양지.",
          },
        ],
      };
    },
  };
  </script>
  
  <style scoped>
  .popular-destinations {
    padding: 50px 0;
    background-color: #f8f9fa;
  }
  .destination-card img {
    height: 200px;
    object-fit: cover;
  }
  </style>
  