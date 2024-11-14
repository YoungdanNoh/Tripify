import { createRouter, createWebHistory } from "vue-router";
import SearchView from "../views/SearchView.vue"; // SearchView로 변경
import CommunityView from "../views/CommunityView.vue"; // SearchView로 변경

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/place", // 기본 경로를 '/place'로 설정
      name: "place", // 라우트 이름도 'place'로 변경
      component: SearchView,
    },
    {
      path: "/community",
      name: "community",
      component: CommunityView,
    },
  ],
});

export default router;
