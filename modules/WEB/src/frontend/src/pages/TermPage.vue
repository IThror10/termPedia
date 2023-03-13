<template>
  <div id="termPage" v-if="term">
    <common-form>
      <div class="term_info">
        <h1>{{term.name}}</h1>
        <my-value>{{term.description}}</my-value>
      </div>
      <separate-line/>
      <div class="relations">
        <tag-term-component :term-id="term.tid"/>
        <lit-term-component :term-id="term.tid"/>
      </div>
    </common-form>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {TermData, TermService} from "@/services/TermService";
  import TagTermComponent from "@/components/TagTermComponent.vue";
  import LitTermComponent from "@/components/LitTermComponent.vue";
  export default defineComponent({
    name: "TermPage",
    components: {LitTermComponent, TagTermComponent},
    data() : {term: TermData | null } {
      return {
        term: null
      }
    },
    computed: {
      termS: () => new TermService()
    },
    async created() {
      this.term = await this.termS.getTermById(parseInt(this.$route.params.tid as string))
    }
  })
</script>

<style scoped>
  #termPage {
    width: 750px;
    height: auto;
    min-height: 400px;
    min-width: 950px;

    padding: 20px 0;
    margin: 0px auto 20px auto;
  }

  .term_info {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 70%;
  }

  .term_info h1 {
    font-size: 24px;
  }

  .relations {
    display: flex;
    width: 90%;
    justify-content: space-between;
    align-items: start;
  }
  .relations > div{
    width: 40%;
    margin: 0px  auto;
  }
</style>