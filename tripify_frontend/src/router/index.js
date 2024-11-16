import { createRouter, createWebHistory } from 'vue-router';

// 메인 뷰
import SearchView from '../views/SearchView.vue';
import CommunityView from '../views/CommunityView.vue';
import HomeView from '../views/HomeView.vue';

// Member 관련 뷰
import MemberView from '@/views/MemberView.vue';
import LoginPage from '@/components/member/LoginPage.vue';
import SignUpPage from '@/components/member/SignUpPage.vue';
import PasswordRecoveryPage from '@/components/member/PasswordRecoveryPage.vue';
import UserProfilePage from '@/components/member/UserProfilePage.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', // 홈페이지
      name: 'home',
      component: HomeView,
    },
    {
      path: '/place', // 기본 경로를 '/place'로 설정
      name: 'place',
      component: SearchView,
    },
    {
      path: '/community',
      name: 'community',
      component: CommunityView,
    },
    {
      path: '/login', // 회원 관련 라우트
      name: 'login',
      component: MemberView,
      children: [
        {
          path: '', // 기본 경로 '/login'
          name: 'loginPage',
          component: LoginPage,
        },
        {
          path: 'signup', // '/login/signup'
          name: 'signUpPage',
          component: SignUpPage,
        },
        {
          path: 'password-recovery', // '/login/password-recovery'
          name: 'passwordRecoveryPage',
          component: PasswordRecoveryPage,
        },
        
      ],
    },
    {
      path: '/profile', // '/profile'
      name: 'userProfilePage',
      component: UserProfilePage,
    },
  ],
});

export default router;
