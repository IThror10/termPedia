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
    name: 'AddButton',
    mixins: [ButtonMixin],
  });
</script>

<style scoped>
.my-button {
  width: 100px;
  height: 40px;

  color: #fff;
  background-color: #008000;
  border: none;
  border-radius: 4px;

  cursor: pointer;
  font-weight: bold;
}

button:hover {
  background-color: #00A020;
}

button:active {
  background-color: #00A020;
  transform: translateY(1px);
}
</style>