<template>
  <div class="search-form">
    <div class="search-header" v-if="hasInput">
      <my-input class="input_field"
                :type="type"
                :value="modelValue"
                :placeholder="placeholder"
                @input="updateInput"
                autocomplete="off"
      />
      <my-button @click="search">Search</my-button>
    </div>

    <div class="search-body">
      <div class="search-pagination">
        <my-button @click="prev" :disabled="curPage < 2">Prev</my-button>
        <my-label>Page: {{curPage}}</my-label>
        <my-button @click="next" :disabled="!hasMore">Next</my-button>
      </div>
      <div class="search-result">
        <slot/>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import InputMixin from "@/components/mixins/InputMixin";

  export default defineComponent({
    name: "SearchComponent",
    emits: ['search'],
    mixins: [InputMixin],
    props: {
      hasMore: {
        type: Boolean,
        required: true
      },
      curPage: {
        type: Number,
        required: true
      },
      hasInput: {
        type: Boolean,
        default: true
      }
    },
    methods: {
      next() {
        if (this.hasMore) {
          this.$emit('search', this.curPage + 1);
        }
      },
      prev() {
        if (this.curPage > 1) {
          this.$emit('search', this.curPage - 1);
        }
      },
      search() {
        this.$emit('search', 1);
      }
    }
  })
</script>

<style scoped>
  .search-form > * {
    margin-top: 10px;
  }

  .search-body {
    display: flex;
    flex-direction: column;
  }

  .search-header {
    display: flex;
    flex-direction: row;
    height: auto;
  }

  .search-header > * {
    margin-left: 20px;
  }

  .search-header button {
    margin-right: 15px;
  }

  .search-pagination {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px 0px;
    height: auto;
  }

  .search-pagination > * {
    margin: 0px auto;
  }

  .search-pagination > div {
    display: flex;
    justify-content: center;
  }
</style>