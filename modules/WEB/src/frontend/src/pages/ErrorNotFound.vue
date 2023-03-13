<template>
  <div class="not_found">
    <div>
      <h1>404 Error: Not Found</h1>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'ErrorNotFound',
  });
</script>

<style scoped>
  .not_found {
    font-size: 40px;
    color: red;
  }
</style>