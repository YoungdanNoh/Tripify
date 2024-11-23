import { createRouter, createWebHistory } from "vue-router";

// 메인 뷰
import SearchView from "../views/SearchView.vue";
import CommunityView from "../views/CommunityView.vue";
import HomeView from "../views/HomeView.vue";
import SpotifyView from "../views/SpotifyView.vue";
import PlanHomeView from "@/views/PlanHomeView.vue";
import PlanDetailView from "@/views/PlanDetailView.vue";

// Member 관련 뷰
import MemberView from "@/views/MemberView.vue";
import LoginPage from "@/components/member/LoginPage.vue";
import SignUpPage from "@/components/member/SignUpPage.vue";
import PasswordRecoveryPage from "@/components/member/PasswordRecoveryPage.vue";
import UserProfilePage from "@/components/member/UserProfilePage.vue";

//Board 관련 뷰
import WritePost from "@/components/Board/WritePost.vue";
import BoardShow from "@/components/Board/BoardShow.vue";

//음악추천 관련 뷰
import MusicRecommendView from "@/views/MusicRecommendView.vue";

import { useUserStore } from "@/stores/user";
import { usePlaceStore } from "@/stores/place";
import PostDetail from "@/components/Board/PostDetail.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/", // 홈페이지
      name: "home",
      component: HomeView,
    },
    {
      path: "/place:keyword?", // 기본 경로를 '/place'로 설정
      name: "place",
      component: SearchView,
      beforeEnter: () => {
        const placeStore = usePlaceStore();
        placeStore.resetPlaces(); // places 초기화
      },
    },
    {
      path: "/plan", // 여행계획 페이지
      name: "plan",
      component: PlanHomeView,
      meta: { requiresAuth: true }, // 로그인 필요 설정
    },
    {
      path: "/spotify",
      name: "spotify",
      component: SpotifyView,
    },
    {
      path: "/musicRecommend",
      name: "musicRecommend",
      component: MusicRecommendView,
    },
    {
      path: "/PlanDetail",
      name: "PlanDetail",
      component: PlanDetailView,
    },
    {
      path: "/community",
      component: CommunityView,
      children: [
        {
          path: "",
          name: "BoardShow",
          component: BoardShow, // 기본 게시판
        },
        {
          path: "write",
          name: "WritePost",
          component: WritePost, // 글쓰기 페이지
          meta: { requiresAuth: true }, // 로그인 필요 설정
        },
        {
          path: "posts/:postId", // 동적 라우트
          name: "PostDetail",
          component: PostDetail, // 상세 페이지
          props: true, // 라우트 파라미터를 컴포넌트의 props로 전달
        },
        {
          path: "posts/:postId/edit",
          name: "EditPost",
          component: WritePost,
          props: (route) => ({ isEdit: true, postId: Number(route.params.postId) }),
        },
      ],
    },

    {
      path: "/login", // 회원 관련 라우트
      name: "login",
      component: MemberView,
      children: [
        {
          path: "", // 기본 경로 '/login'
          name: "loginPage",
          component: LoginPage,
        },
        {
          path: "signup", // '/login/signup'
          name: "signUpPage",
          component: SignUpPage,
        },
        {
          path: "password-recovery", // '/login/password-recovery'
          name: "passwordRecoveryPage",
          component: PasswordRecoveryPage,
        },
      ],
    },
    {
      path: "/profile", // '/profile'
      name: "userProfilePage",
      component: UserProfilePage,
    },
  ],
});
// 전역 가드
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();

  // 로그인 필요 페이지에 접근하려고 할 때
  if (to.meta.requiresAuth && !userStore.user) {
    alert("로그인이 필요합니다.");
    next("/login"); // 로그인 페이지로 리다이렉트
  } else {
    next(); // 정상 접근 허용
  }
});

export default router;
