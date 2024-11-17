<template>
    <div v-if="isVisible" class="alert-overlay" @click="closeAlert">
      <div class="alert-box">
        <p>{{ message }}</p>
        <button @click="closeAlert">확인</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      message: {
        type: String,
        required: true,
      },
    },
    data() {
      return {
        isVisible: true,
      };
    },
    methods: {
      closeAlert() {
        this.isVisible = false;
        this.$emit("closed");
      },
    },
  };
  </script>
  
  <style>
  .alert-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
  }
  .alert-box {
    background: white;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  }
  .alert-box button {
    margin-top: 10px;
    padding: 5px 10px;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .alert-box button:hover {
    background: #0056b3;
  }
  </style>
  