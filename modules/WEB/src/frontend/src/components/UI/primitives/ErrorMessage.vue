<template>
  <span class="error-message"><slot/></span>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  export default defineComponent({
    name: "ErrorMessage"
  })
</script>

<style scoped>
  .error-message {
    font-weight: bold;
    height: 40px;
    line-height: 40px;
    color: #b63838;
  }
</style>