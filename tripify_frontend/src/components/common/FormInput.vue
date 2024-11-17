<template>
    <div class="form-input">
        <label>{{ label }}</label>
        <input :type="type" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)" />
    </div>
</template>

<script>
export default {
    props: {
        label: String,
        type: {
            type: String,
            default: "text", // 기본 타입 설정
        },
        modelValue: String, // v-model 바인딩 값
    },
};
</script>
