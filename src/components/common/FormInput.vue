<template>
    <div class="form-input">
        <label>{{ label }}</label>
        <input :type="type" :value="value" @input="$emit('update:value', $event.target.value)" />
    </div>
</template>
  
<script>
export default {
    props: ['label', 'type', 'value'],
};
</script>
  