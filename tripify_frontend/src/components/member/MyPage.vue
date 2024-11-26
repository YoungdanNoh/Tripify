<template>
    <div class="profile-page">
        <h2>프로필 정보</h2>
        <form @submit.prevent="handleUpdate">
            <!-- 프로필 이미지 -->
            <div class="form-group text-center">
                <img :src="formData.profileImage || defaultImage" alt="프로필 이미지" class="profile-img" />
            </div>

            <!-- 이미지 URL 입력 -->
            <div class="form-group">
                <label for="profileImage" class="form-label">프로필 이미지 URL</label>
                <input type="text" id="profileImage" placeholder="이미지 URL을 입력하세요" :disabled="!isEditing"
                    v-model="formData.profileImage" />
            </div>

            <!-- 이름 -->
            <div class="form-group">
                <label for="userName">이름</label>
                <input id="userName" type="text" v-model="formData.userName" :disabled="!isEditing" required />
            </div>

            <!-- 이메일 (비활성화) -->
            <div class="form-group">
                <label for="email">이메일</label>
                <input id="email" type="email" v-model="formData.email" disabled />
            </div>

            <!-- 비밀번호 (정보 수정 중에만 표시) -->
            <div class="form-group" v-if="isEditing">
                <label for="password">비밀번호</label>
                <input id="password" type="password" v-model="formData.password" placeholder="새 비밀번호를 입력하세요" />
            </div>
            <!-- 정보 수정 및 저장 버튼 -->
            <button type="button" v-if="!isEditing" @click="enableEditing">
                정보 수정
            </button>
            <button type="submit" v-if="isEditing">저장</button>
        </form>

        <button @click="handleDelete">회원 탈퇴</button>
        <AlertMessage v-if="showAlert" message="회원 탈퇴가 완료되었습니다." @closed="handleAlertClose" />
        <p v-if="message" :class="{ error: isError }">{{ message }}</p>

        <div class="tab-content">
            <button @click="fetchLikedPlaces">좋아하는 장소</button>
            <button @click="fetchPosts">게시글</button>

            <!-- 좋아하는 장소 목록 -->
            <div v-if="currentTab === 'places'">
                <ul>
                    <li v-for="place in likedPlaces" :key="place.id">
                        {{ place.title }}
                    </li>
                </ul>
            </div>

            <!-- 게시글 목록 -->
            <div v-if="currentTab === 'posts'">
                <ul>
                    <li v-for="post in posts" :key="post.id">
                        {{ post.title }}
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { reactive, ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { updateUser, deleteUser } from "@/api/user";
import { getLikedPlaces } from "@/api/place";
//import { fetchPosts as apiFetchPosts } from "@/api/posts";

const userStore = useUserStore();
const router = useRouter();
const currentTab = ref('');
const likedPlaces = ref([]);
const posts = ref([]);

const user = computed(() => userStore.user);

const formData = reactive({
    userName: user.value?.userName || "",
    email: user.value?.email || "",
    password: "",
    profileImage: user.value?.profileImage || null, // 기본 이미지 설정
});

// 상태 관리
const isEditing = ref(false);
const message = ref("");
const showAlert = ref(false);
const isError = ref(false);

const enableEditing = () => {
    isEditing.value = true;
};

// 정보 수정 처리
const handleUpdate = async () => {
    try {
        const { userName, email, password, profileImage } = formData;
        await updateUser(userName, email, password || null, profileImage || null); // API 호출
        userStore.user = { ...userStore.user, userName, profileImage }; // Pinia 상태 업데이트
        message.value = "프로필 정보가 성공적으로 수정되었습니다.";
        isEditing.value = false;
        isError.value = false;
    } catch (err) {
        message.value = "프로필 정보 수정에 실패했습니다.";
        isError.value = true;
        console.error(err);
    }
};

// 회원 탈퇴 처리
const handleDelete = async () => {
    try {
        const { email } = userStore.user;
        if (!email) {
            message.value = "회원 정보를 확인할 수 없습니다.";
            isError.value = true;
            return;
        }

        await deleteUser(email); // API 호출
        userStore.user = null; // Pinia 상태 초기화
        showAlert.value = true;

        setTimeout(() => {
            router.push("/"); // 3초 후 페이지 이동
        }, 3000);
    } catch (err) {
        message.value = "회원 탈퇴에 실패했습니다.";
        isError.value = true;
        console.error(err);
    }
};

// AlertMessage 닫기
const handleAlertClose = () => {
    showAlert.value = false;
};

// 좋아하는 장소 가져오기
const fetchLikedPlaces = async () => {
    console.log("user:", userStore.user);
    try {
        likedPlaces.value = await getLikedPlaces(userStore.user.userId);
        console.log("Liked places fetched:", likedPlaces.value);
    } catch (error) {
        console.error("Error fetching liked places:", error);
        likedPlaces.value = [];  // 오류가 발생했을 때 기본값 설정
    }
    currentTab.value = 'places';  // 탭을 'places'로 설정
};


onMounted(() => {
});

</script>

<style scoped>
.profile-page {
    max-width: 400px;
    margin: 40px auto;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    background: #fff;
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 10px;
    color: #333;
    font-size: 14px;
    font-weight: bold;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    transition: border-color 0.3s;
}

input:focus {
    border-color: #007bff;
    outline: none;
}

button {
    display: block;
    width: 100%;
    padding: 12px;
    margin-top: 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #0056b3;
}

p {
    margin-top: 10px;
    text-align: center;
}

.error {
    color: #d32f2f;
    font-weight: bold;
}

.profile-img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
    cursor: pointer;
    margin: 0 auto;
    display: block;
}
</style>
