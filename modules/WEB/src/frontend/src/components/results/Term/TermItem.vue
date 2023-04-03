<template>
  <div class="term" @click="moveToTerm">
    <div class="name">{{term.name}}</div>
    <span class="description">{{term.description}}</span>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import router from "@/router/routes";
  import {TermData} from "@/services/TermService";

  export default defineComponent({
    name: 'TermItem',
    props: {
      term: {
        type: Object as () => TermData,
        required: true
      }
    },
    methods: {
      async moveToTerm() {
        await router.push('/terms/' + this.term.tid);
      }
    }
  })
</script>

<style scoped>
  .term {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    height: auto;

    padding: 7px 0;
    margin: 0px 10px;
  }

  .name {
    font-weight: bold;
    font-size: 18px;
  }

  .description {
    color: #4d4d4d;
  }
</style>