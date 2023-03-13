<template>
  <div class="search-container">
    <div class="search-add">
      <div class="search-add-label" v-if="label.length > 0">
        <my-label>{{label}}</my-label>
      </div>
      <my-input
          class="search-input"
          type="text"
          :value="modelValue"
          :placeholder="placeholder"
          @input="updateInput"
          @focus="showDropdown"
          @blur="hideDropdown"
      />
      <div class="search-add-button" v-if="hasButton">
        <add-button @click="$emit('add')">Add</add-button>
      </div>
    </div>
    <div v-if="showResults" class="search_result">
      <slot/>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import InputMixin from "@/components/mixins/InputMixin";
import MyLabel from "@/components/UI/primitives/MyLabel.vue";

export default defineComponent({
  name: "DropdownInput",
  components: {MyLabel},
  emits: ['add'],
  mixins: [InputMixin],
  props: {
    hasButton: {
      type: Boolean,
      default: true
    },
    label: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      showResults: false
    }
  },
  methods: {
    showDropdown() {
      this.showResults = true;
    },

    hideDropdown() {
      setTimeout(
          () => this.showResults = false,
          150
      )
    },
  }
})
</script>

<style scoped>
  .search-container {
    display: flex;
    flex-direction: column;
    height: 60px;
    margin: 10px 0;
    z-index: 2;
  }

  .search-add {
    display: flex;
  }
</style>