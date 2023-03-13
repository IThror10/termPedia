<template>
  <div class="search-header">
    <my-input class="input_field"
              :type="type"
              :value="modelValue"
              :placeholder="placeholder"
              @input="updateInput"
              autocomplete="off"
    />
    <my-button @click="callSearch">Search</my-button>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import InputMixin from "@/components/mixins/InputMixin";

  export default defineComponent({
    name: 'SearchHeader',
    mixins: [InputMixin],
    methods: {
      callSearch() {
        this.$emit('doSearch');
      },
    }
  })
</script>

<style scoped>
.search-component {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 20px;
}

.search-component * {
  margin: 0 10px;
}
</style>