<template>
  <div class="labeled_list">
    <my-label class="label">{{ label }}:</my-label>
    <div class="details-value" v-if="values.length > 0">
      <ul>
        <li v-for="(value, index) in values" :key="index" class="removable">
          <my-value class="value">{{value}}</my-value>
          <remove-button @click="remove(value)">Remove</remove-button>
        </li>
        <li class="addable">
          <my-input class="input" v-model="inputValue"></my-input>
          <add-button @click="add">Add</add-button>
        </li>
      </ul>
    </div>
    <div class="user-details-value" v-else>
      <ul>
        <li>
          <my-input v-model="inputValue"></my-input>
          <add-button @click="add">Add</add-button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";

  export default defineComponent({
    name: 'LabelList',
    emits: ['add', 'remove'],
    props: {
      values: {
        type: Array,
        required: true
      },
      label: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        inputValue: ''
      }
    },
    methods: {
      remove(value: string) {
        this.$emit('remove', value)
      },
      add() {
        this.$emit('add', this.inputValue)
        this.inputValue = '';
      }
    }
  })
</script>

<style scoped>
  .labeled_list {
    display: flex;
    align-items: start;
    margin-bottom: 10px;
  }

  .label {
    margin-top: 10px;
  }

  .removable {
    display: flex;
    justify-content: space-between;
  }

  li {
    display: flex;
    flex-direction: row;
    margin-top: 10px;
  }

  /*li input {*/
  /*  width: 200px;*/
  /*}*/
</style>