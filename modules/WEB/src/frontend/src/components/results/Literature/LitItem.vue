<template>
  <div class="tag_info">
    <div class="value">
      <div class="data">
        <my-label>{{lit.name}} ({{lit.year}})</my-label>
        <my-value>Type: {{lit.type}}</my-value>
        <my-value>Authors: {{lit.authors}}</my-value>
      </div>
      <my-label>{{lit.rating}} 	&#11088; ({{lit.ratesAmount}} &#10032;)</my-label>
    </div>
    <div class="rating" v-if="isAuth">
      <my-rating @set-rating="rated" :initial-rating="rating"/>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import MyRating from "@/components/UI/primitives/MyRating.vue";
  import store from "@/store";
  import {LiteratureService, RatedLiteratureData} from "@/services/LiteratureService";

  export default defineComponent({
    name: "LitItem",
    components: {MyRating},
    data(): {rating: number, update: boolean} {
      return {
        rating: 0,
        update: false
      }
    },
    props: {
      lit: {
        type: Object as () => RatedLiteratureData,
        required: true
      },
      termId: {
        type: Number,
        required: true
      }
    },
    computed: {
      ls: () => new LiteratureService(),
      isAuth: () => store.state.auth.isAuth,
    },
    created() {
      this.updateRating();
    },
    watch: {
      lit() {
        this.updateRating();
      }
    },
    methods: {
      rated(rating: number) {
        this.ls.setRating(this.lit.lid, this.termId, rating).then(() => this.rating = rating);
      },
      async updateRating() {
        if (this.isAuth) {
          this.rating = await this.ls.getRating(this.lit.lid, this.termId);
        }
      }
    }
  })
</script>

<style scoped>
.value {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}
</style>