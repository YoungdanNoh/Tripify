<template>
    <div class="write-post-container">
      <h1>{{ isEdit ? "글 수정하기" : "글 작성하기" }}</h1>
  
      <!-- 작성자 정보 -->
      <p class="author-info" v-if="!isEdit">
        작성자: <span>{{ user.userName }}</span>
      </p>
  
      <!-- 글 제목 -->
      <div class="form-group">
        <label for="title">제목</label>
        <input
          id="title"
          type="text"
          v-model="formData.title"
          placeholder="제목을 입력하세요"
          required
          :disabled="isEdit"
        />
      </div>
  
      <!-- 글 내용 -->
      <div class="form-group">
        <label for="content">내용</label>
        <textarea
          id="content"
          v-model="formData.content"
          placeholder="내용을 입력하세요"
          required
        ></textarea>
      </div>
  
      <!-- 버튼 -->
      <div class="actions">
        <button @click="submit" class="submit-button">
          {{ isEdit ? "수정 완료" : "작성 완료" }}
        </button>
        <button @click="cancel" class="cancel-button">취소</button>
      </div>
    </div>
  </template>
  
  <script>
import { ref, onMounted } from "vue";
import { fetchPost, createPost, updatePost } from "@/api/posts";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";

export default {
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    postId: {
      type: Number,
      default: null,
    },
  },
  setup(props) {
    const userStore = useUserStore();
    const router = useRouter();

    // formData를 ref로 설정
    const formData = ref({
      title: "",
      content: "",
    });

    // 게시글 작성/수정
    const submit = async () => {
      try {
        if (!formData.value.title || !formData.value.content) {
          alert("제목과 내용을 입력해주세요.");
          return;
        }

        if (props.isEdit) {
          // 수정 API 호출
          await updatePost(props.postId, formData.value.title, formData.value.content);
          alert("게시글이 수정되었습니다.");
        } else {
          // 작성 API 호출
          await createPost(userStore.user.userId, formData.value.title, formData.value.content);
          alert("게시글이 작성되었습니다.");
        }

        router.push("/community");
      } catch (err) {
        console.error(`게시글 ${props.isEdit ? "수정" : "작성"} 중 오류가 발생했습니다.`, err);
        alert(`게시글 ${props.isEdit ? "수정" : "작성"}에 실패했습니다.`);
      }
    };

    // 기존 게시글 데이터 가져오기 (수정 모드)
    const fetchExistingPost = async () => {
      if (props.isEdit && props.postId) {
        try {
          const response = await fetchPost(props.postId);
          formData.value.title = response.data.title; // 기존 제목
          formData.value.content = response.data.content; // 기존 내용
        } catch (err) {
          console.error("게시글 데이터를 가져오는 중 오류가 발생했습니다.", err);
          alert("게시글을 불러오는 데 실패했습니다.");
        }
      }
    };

    // 취소 버튼
    const cancel = () => {
      router.push("/community");
    };

    // 컴포넌트 마운트 시 데이터 로드
    onMounted(() => {
      fetchExistingPost();
    });

    return {
      user: userStore.user,
      formData,
      submit,
      cancel,
    };
  },
};
</script>

  
  <style scoped>
  .write-post-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fff;
  }
  h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  .author-info {
    font-size: 1.1em;
    margin-bottom: 20px;
  }
  .form-group {
    margin-bottom: 20px;
  }
  label {
    display: block;
    margin-bottom: 10px;
    font-weight: bold;
  }
  input,
  textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
  }
  textarea {
    height: 150px;
    resize: none;
  }
  .actions {
    display: flex;
    justify-content: space-between;
  }
  .submit-button {
    padding: 10px 20px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .submit-button:hover {
    background-color: #218838;
  }
  .cancel-button {
    padding: 10px 20px;
    background-color: #dc3545;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .cancel-button:hover {
    background-color: #c82333;
  }
  </style>
  