<template>
  <div class="lit_list">
    <div class="details-value" v-if="array.length > 0">
      <div v-for="(value, index) in array" :key="index" class="element" @click="$emit('selected', index)">
        <div class="value">{{value}}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";

  export default defineComponent({
    name: 'StringList',
    emits: ['selected'],
    props: {
      array: {
        type: Array as () => string[],
        required: true
      }
    }
  })
</script>

<style scoped>
.lit_list {
  width: 100%;
}
.element {
  height: auto;
  border: 2px solid #ccc;
  background-color: white;
  margin-top: -2px;
}
.element:hover {
  background-color: #d8d8d8;
  cursor: pointer;
}
</style>