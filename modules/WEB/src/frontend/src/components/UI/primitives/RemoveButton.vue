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
  name: 'RemoveButton',
  mixins: [ButtonMixin],
});
</script>

<style scoped>
.my-button {
  width: 100px;
  height: 40px;

  background-color: #b60808;
  color: #fff;
  border: none;
  border-radius: 4px;

  font-weight: bold;
  cursor: pointer;
}

button:hover {
  background-color: #b63838;
}

button:active {
  background-color: #b63838;
  transform: translateY(1px);
}
</style>