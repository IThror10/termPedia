<template>
  <button
      class="my-button"
      :disabled="disabled"
  >
    <slot></slot>
  </button>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ButtonMixin from "@/components/mixins/buttonMixin";

  export default defineComponent({
    name: 'MyButton',
    mixins: [ButtonMixin],
  });
</script>

<style scoped>
  .my-button {
    width: 100px;
    height: 40px;
    line-height: 40px;

    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;

    font-weight: bold;
    cursor: pointer;
  }

  button:hover {
    background-color: #0069d9;
  }

  button:active {
    background-color: #0069d9;
    transform: translateY(1px);
  }

  button:disabled {
    background-color: #688296;
  }
</style>